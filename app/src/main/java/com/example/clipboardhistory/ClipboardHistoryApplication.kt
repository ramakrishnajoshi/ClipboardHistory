package com.example.clipboardhistory

import android.app.Application
import com.example.clipboardhistory.data.database.ClipboardDatabase
import com.example.clipboardhistory.data.repository.ClipboardRepository

class ClipboardHistoryApplication : Application() {
    
    private val database by lazy { ClipboardDatabase.getDatabase(this) }
    val repository by lazy { ClipboardRepository(database.clipboardDao()) }
}