package com.app.examen_practico.domain.model

data class EmotionResult(
    val emotionScore: Double,
    val emotionType: String,
    val timestamp: Long = System.currentTimeMillis()
)