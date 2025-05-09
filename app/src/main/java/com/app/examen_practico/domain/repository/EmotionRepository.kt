package com.app.examen_practico.domain.repository

import com.app.examen_practico.domain.model.EmotionResult

interface EmotionRepository {
    suspend fun analyzeTextEmotion(text: String): Result<EmotionResult>
}