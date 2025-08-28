package com.example.clipboardhistory.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clipboardhistory.data.entity.ClipboardEntry
import com.example.clipboardhistory.data.repository.ClipboardRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ClipboardViewModel : ViewModel() {
    
    private val _clipboardEntries = MutableStateFlow<List<ClipboardEntry>>(emptyList())
    val clipboardEntries: StateFlow<List<ClipboardEntry>> = _clipboardEntries.asStateFlow()
    
    private lateinit var repository: ClipboardRepository
    
    fun initialize(repository: ClipboardRepository) {
        this.repository = repository
        loadClipboardEntries()
    }
    
    private fun loadClipboardEntries() {
        viewModelScope.launch {
            repository.getAllEntries().collect { entries ->
                _clipboardEntries.value = entries
            }
        }
    }
}