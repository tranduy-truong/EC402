package com.tranduytruong.novatech

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.tranduytruong.novatech.ui.NovaTechApp
import com.tranduytruong.novatech.ui.theme.NovaTechTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NovaTechTheme {
                NovaTechApp()
            }
        }
    }
}
