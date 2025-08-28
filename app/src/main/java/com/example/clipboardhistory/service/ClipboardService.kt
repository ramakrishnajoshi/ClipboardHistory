package com.example.clipboardhistory.service

import android.app.Service
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.example.clipboardhistory.data.repository.ClipboardRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class ClipboardService : Service() {
    
    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private lateinit var clipboardManager: ClipboardManager
    private lateinit var repository: ClipboardRepository
    
    private val clipboardListener = ClipboardManager.OnPrimaryClipChangedListener {
        val clip = clipboardManager.primaryClip
        if (clip != null && clip.itemCount > 0) {
            val text = clip.getItemAt(0).text?.toString()
            if (!text.isNullOrBlank()) {
                Log.d("ClipboardService", "Clipboard changed: $text")
                serviceScope.launch {
                    repository.insertEntry(text)
                }
            }
        }
    }
    
    override fun onCreate() {
        super.onCreate()
        clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        
        // Get repository from application
        val application = application as ClipboardHistoryApplication
        repository = application.repository
        
        Log.d("ClipboardService", "Service created")
    }
    
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        clipboardManager.addPrimaryClipChangedListener(clipboardListener)
        Log.d("ClipboardService", "Clipboard monitoring started")
        return START_STICKY
    }
    
    override fun onDestroy() {
        super.onDestroy()
        clipboardManager.removePrimaryClipChangedListener(clipboardListener)
        Log.d("ClipboardService", "Clipboard monitoring stopped")
    }
    
    override fun onBind(intent: Intent?): IBinder? = null
}