package com.app.examen_practico.data.repository

import com.app.examen_practico.data.remote.EmotionAnalysisService
import com.app.examen_practico.data.remote.toEmotionResult
import com.app.examen_practico.domain.model.EmotionResult
import com.app.examen_practico.domain.repository.EmotionRepository

class EmotionRepositoryImpl(
    private val service: EmotionAnalysisService
) : EmotionRepository {
    override suspend fun analyzeTextEmotion(text: String): Result<EmotionResult> {
        return try {
            val response = service.getTextEmotion(text)
            Result.success(response.toEmotionResult())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}