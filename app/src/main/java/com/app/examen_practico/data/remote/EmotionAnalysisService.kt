package com.app.examen_practico.data.remote

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

data class EmotionResponseDto(
    val sentiment: String,
    val score: Double
)

interface EmotionAnalysisService {
    @GET("sentiment")
    suspend fun getTextEmotion(
        @Query("text") text: String,
        @Header("X-Api-Key") apiKey: String = "wLVPN1zV08lJYF7uXqgyPw==zVwp6TlVcAO1NLUf"
    ): EmotionResponseDto
}