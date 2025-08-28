package com.example.clipboardhistory.data.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.example.clipboardhistory.data.dao.ClipboardDao
import com.example.clipboardhistory.data.entity.ClipboardEntry

@Database(
    entities = [ClipboardEntry::class],
    version = 1,
    exportSchema = false
)
abstract class ClipboardDatabase : RoomDatabase() {
    
    abstract fun clipboardDao(): ClipboardDao
    
    companion object {
        @Volatile
        private var INSTANCE: ClipboardDatabase? = null
        
        fun getDatabase(context: Context): ClipboardDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ClipboardDatabase::class.java,
                    "clipboard_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}