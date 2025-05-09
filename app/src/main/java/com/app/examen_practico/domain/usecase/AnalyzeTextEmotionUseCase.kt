package com.app.examen_practico.domain.usecase

import com.app.examen_practico.domain.model.EmotionResult
import com.app.examen_practico.domain.repository.EmotionRepository

class AnalyzeTextEmotionUseCase(
    private val repository: EmotionRepository
) {
    suspend operator fun invoke(text: String): Result<EmotionResult> {
        if (text.isBlank()) {
            return Result.failure(IllegalArgumentException("El texto no puede estar vacío"))
        }
        return repository.analyzeTextEmotion(text)
    }
}