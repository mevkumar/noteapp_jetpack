package com.app.notesapp.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.notesapp.data.local.NoteEntity
import com.app.notesapp.data.repository.NoteRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import android.app.Application
import androidx.lifecycle.AndroidViewModel

import com.app.notesapp.data.local.NoteDatabase
import kotlinx.coroutines.flow.StateFlow


class NoteViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: NoteRepository

    val notes: StateFlow<List<NoteEntity>>

    init {

        val dao = NoteDatabase.getDatabase(application).noteDao()

        repository = NoteRepository(dao)

        notes = repository.allnotes.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            emptyList()
        )
    }

    fun insertNote(title: String, desc: String) {

        viewModelScope.launch {

            repository.insert(
                NoteEntity(
                    title = title,
                    description = desc
                )
            )
        }
    }
    fun updateNote(note: NoteEntity) {
        viewModelScope.launch {
            repository.update(note)
        }
    }
    fun deleteNote(note: NoteEntity) {

        viewModelScope.launch {

            repository.delete(note)

        }
    }
}