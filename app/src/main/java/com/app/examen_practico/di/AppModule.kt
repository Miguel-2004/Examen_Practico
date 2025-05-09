package com.app.examen_practico.di

import com.app.examen_practico.data.remote.EmotionAnalysisService
import com.app.examen_practico.data.repository.EmotionRepositoryImpl
import com.app.examen_practico.domain.repository.EmotionRepository
import com.app.examen_practico.domain.usecase.AnalyzeTextEmotionUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofitInstance(): Retrofit = Retrofit.Builder()
        .baseUrl("https://api.api-ninjas.com/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideEmotionAnalysisService(retrofit: Retrofit): EmotionAnalysisService =
        retrofit.create(EmotionAnalysisService::class.java)

    @Provides
    @Singleton
    fun provideEmotionRepository(service: EmotionAnalysisService): EmotionRepository =
        EmotionRepositoryImpl(service)

    @Provides
    @Singleton
    fun provideAnalyzeTextEmotionUseCase(repository: EmotionRepository): AnalyzeTextEmotionUseCase =
        AnalyzeTextEmotionUseCase(repository)
}