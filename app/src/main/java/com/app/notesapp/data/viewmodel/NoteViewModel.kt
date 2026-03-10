package com.app.notesapp.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.app.notesapp.data.local.NoteDatabase
import com.app.notesapp.data.local.NoteEntity
import com.app.notesapp.data.repository.NoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: NoteRepository = run {
        val dao = NoteDatabase.getDatabase(application).noteDao()
        NoteRepository(dao)
    }

    // Original list from DB
    private val _allNotes = repository.allnotes.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        emptyList()
    )

    // Sorted list exposed to UI
    private val _notes = MutableStateFlow<List<NoteEntity>>(emptyList())
    val notes: StateFlow<List<NoteEntity>> get() = _notes

    private val _sortType = MutableStateFlow("NEWEST")

    init {
        // Collect notes from DB and sort initially
        viewModelScope.launch {
            _allNotes.collect { list -> applySort(list) }
        }

        // Re-apply sort on sort type change
        viewModelScope.launch {
            _sortType.collect { applySort(_allNotes.value) }
        }
    }

    private fun applySort(list: List<NoteEntity>) {
        _notes.value = when (_sortType.value) {
            "NEWEST" -> list.sortedByDescending { it.createdAt }
            "OLDEST" -> list.sortedBy { it.createdAt }
            else -> list
        }
    }

    fun insertNote(title: String, desc: String) {
        viewModelScope.launch {
            repository.insert(
                NoteEntity(
                    title = title,
                    description = desc,
                    createdAt = System.currentTimeMillis() // important for sorting
                )
            )
        }
    }

    fun updateNote(note: NoteEntity) {
        viewModelScope.launch { repository.update(note) }
    }

    fun deleteNote(note: NoteEntity) {
        viewModelScope.launch { repository.delete(note) }
    }

    fun sortNewest() { _sortType.value = "NEWEST" }
    fun sortOldest() { _sortType.value = "OLDEST" }
}