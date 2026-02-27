package com.app.notesapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao{

    @Insert
    suspend fun addnote(noteEntity: NoteEntity)

    @Update
    suspend fun updatenote(noteEntity: NoteEntity)

    @Delete
    suspend fun deletenote(noteEntity: NoteEntity)

    @Query("SELECT * FROM notes")
    fun getAllNotes(): Flow<List<NoteEntity>>


    @Query("SELECT * FROM notes WHERE id=:id")
    suspend fun getNoteById(id: Int): NoteEntity


}