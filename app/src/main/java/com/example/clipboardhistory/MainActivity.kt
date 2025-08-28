package com.example.clipboardhistory

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.clipboardhistory.service.ClipboardService
import com.example.clipboardhistory.ui.ClipboardHistoryScreen
import com.example.clipboardhistory.ui.theme.ClipboardHistoryTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        // Start the clipboard monitoring service
        val serviceIntent = Intent(this, ClipboardService::class.java)
        startService(serviceIntent)
        
        setContent {
            ClipboardHistoryTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ClipboardHistoryScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}