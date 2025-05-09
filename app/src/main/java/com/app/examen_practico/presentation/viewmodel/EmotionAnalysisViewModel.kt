package com.app.examen_practico.presentation.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.examen_practico.domain.model.EmotionResult
import com.app.examen_practico.domain.usecase.AnalyzeTextEmotionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmotionAnalysisViewModel @Inject constructor(
    private val analyzeTextEmotion: AnalyzeTextEmotionUseCase
) : ViewModel() {

    var uiState by mutableStateOf(EmotionAnalysisState())
        private set

    fun processText(text: String) {
        if (text.isBlank()) return

        viewModelScope.launch {
            uiState = uiState.copy(isProcessing = true)
            val result = analyzeTextEmotion(text)

            uiState = if (result.isSuccess) {
                val emotion = result.getOrNull()
                uiState.copy(
                    isProcessing = false,
                    currentEmotion = emotion,
                    errorMessage = null,
                    analysisList = listOfNotNull(emotion) + uiState.analysisList
                )
            } else {
                uiState.copy(
                    isProcessing = false,
                    errorMessage = result.exceptionOrNull()?.message ?: "Error desconocido"
                )
            }
        }
    }
}

data class EmotionAnalysisState(
    val isProcessing: Boolean = false,
    val currentEmotion: EmotionResult? = null,
    val analysisList: List<EmotionResult> = emptyList(),
    val errorMessage: String? = null
)