package com.app.notesapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase




//Yaha tum Room ko bata rahe ho:------
//entities → Kaunsi table database me banegi
//version → Database ka version
@Database(
    entities = [NoteEntity::class],
    version = 1
)



//    Yaha tum RoomDatabase ko extend kar rahe ho.
//    Room internally SQLite database create karega.
abstract class NoteDatabase : RoomDatabase() {


    //Ye Room ko batata hai ki DAO kaha hai.
    abstract fun noteDao(): NoteDao

    companion object {


       // Agar app me multiple threads database access kare, to same instance mile.
        @Volatile
        private var INSTANCE: NoteDatabase? = null

        //Ye function database instance return karta hai.
        fun getDatabase(context: Context): NoteDatabase {

//            Agar INSTANCE already exist hai
//            → wahi return karo
//            Nahi hai
//            → new database banao
            return INSTANCE ?: synchronized(this) {
                //Yaha actual database create hota hai.
                val instance = Room.databaseBuilder(
                    context.applicationContext,//Application context
                    NoteDatabase::class.java,//Database class
                    "note_database"//Database name
                ).build()
//Ab instance memory me save ho gaya.
                INSTANCE = instance
                instance
            }
        }
    }
}