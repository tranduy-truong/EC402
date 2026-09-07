package com.tranduytruong.novatech

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.tranduytruong.novatech.ui.NovaTechApp
import com.tranduytruong.novatech.ui.theme.NovaTechTheme
import com.tranduytruong.novatech.ui.theme.ThemeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val themeViewModel: ThemeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            val themeMode by themeViewModel.themeMode.collectAsState()
            NovaTechTheme(themeMode = themeMode) {
                NovaTechApp(
                    themeMode = themeMode,
                    onThemeModeChange = themeViewModel::setThemeMode,
                )
            }
        }
    }
}
