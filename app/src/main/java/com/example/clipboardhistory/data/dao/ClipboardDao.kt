package com.example.clipboardhistory.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.clipboardhistory.data.entity.ClipboardEntry
import kotlinx.coroutines.flow.Flow

@Dao
interface ClipboardDao {
    
    @Query("SELECT * FROM clipboard_entries ORDER BY timestamp DESC")
    fun getAllEntries(): Flow<List<ClipboardEntry>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEntry(entry: ClipboardEntry)
    
    @Query("SELECT * FROM clipboard_entries ORDER BY timestamp DESC LIMIT 1")
    suspend fun getLastEntry(): ClipboardEntry?
    
    @Query("DELETE FROM clipboard_entries")
    suspend fun deleteAllEntries()
    
    @Query("SELECT COUNT(*) FROM clipboard_entries")
    suspend fun getEntriesCount(): Int
}