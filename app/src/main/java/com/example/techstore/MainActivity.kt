package com.example.techstore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.techstore.ui.TechStoreApp
import com.example.techstore.ui.theme.TechStoreTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TechStoreTheme {
                TechStoreApp()
            }
        }
    }
}
