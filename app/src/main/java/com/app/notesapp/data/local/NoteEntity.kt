package com.app.notesapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("notes")
data class NoteEntity(
    @PrimaryKey(true)
    val id : Int=0,
    val title: String,
    val description: String

)