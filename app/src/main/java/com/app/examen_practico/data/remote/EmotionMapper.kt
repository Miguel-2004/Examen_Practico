package com.app.examen_practico.data.remote

import com.app.examen_practico.domain.model.EmotionResult

fun EmotionResponseDto.toEmotionResult(): EmotionResult {
    return EmotionResult(
        emotionScore = score,
        emotionType = sentiment,
        timestamp = System.currentTimeMillis()
    )
}