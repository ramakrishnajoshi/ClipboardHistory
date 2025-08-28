package com.example.clipboardhistory.data.repository

import com.example.clipboardhistory.data.dao.ClipboardDao
import com.example.clipboardhistory.data.entity.ClipboardEntry
import kotlinx.coroutines.flow.Flow

class ClipboardRepository(private val clipboardDao: ClipboardDao) {
    
    fun getAllEntries(): Flow<List<ClipboardEntry>> = clipboardDao.getAllEntries()
    
    suspend fun insertEntry(text: String) {
        // Check if this is a duplicate of the last entry
        val lastEntry = clipboardDao.getLastEntry()
        if (lastEntry?.text != text) {
            val entry = ClipboardEntry(text = text)
            clipboardDao.insertEntry(entry)
        }
    }
    
    suspend fun deleteAllEntries() {
        clipboardDao.deleteAllEntries()
    }
    
    suspend fun getEntriesCount(): Int {
        return clipboardDao.getEntriesCount()
    }
}