package com.app.examen_practico

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.app.examen_practico.presentation.ui.EmotionAnalysisScreen
import com.app.examen_practico.presentation.ui.theme.ExamenPracticoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExamenPracticoTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    EmotionAnalysisScreen()
                }
            }
        }
    }
}