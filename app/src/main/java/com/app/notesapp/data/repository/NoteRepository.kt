package com.app.notesapp.data.repository

import com.app.notesapp.data.local.NoteDao
import com.app.notesapp.data.local.NoteEntity

class NoteRepository(private val dao: NoteDao){

    val allnotes= dao.getAllNotes()

    suspend fun insert(noteEntity: NoteEntity){
        dao.addnote(noteEntity)

    }

    suspend fun update(noteEntity: NoteEntity){
        dao.updatenote(noteEntity)
    }

    suspend fun delete(noteEntity: NoteEntity){
        dao.deletenote(noteEntity)
    }

    suspend fun getNoteById(id: Int){
        dao.getNoteById(id)
    }
}