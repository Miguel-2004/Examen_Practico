package com.app.examen_practico.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.examen_practico.domain.model.EmotionResult
import com.app.examen_practico.presentation.ui.theme.NegativeEmotionColor
import com.app.examen_practico.presentation.ui.theme.NeutralEmotionColor
import com.app.examen_practico.presentation.ui.theme.PositiveEmotionColor
import com.app.examen_practico.presentation.viewmodel.EmotionAnalysisViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmotionAnalysisScreen(viewModel: EmotionAnalysisViewModel = hiltViewModel()) {
    val state = viewModel.uiState
    var textInput by remember { mutableStateOf("") }
    val dateFormat = remember { SimpleDateFormat("HH:mm:ss", Locale.getDefault()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            "Analizador de Emociones",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            "Herramienta para profesionales de la salud mental",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = textInput,
            onValueChange = { textInput = it },
            label = { Text("Ingresa texto para análisis") },
            placeholder = { Text("Escribe o pega el texto aquí...") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (textInput.isNotBlank()) {
                    viewModel.processText(textInput)
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Analizar emoción")
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Estado actual
        when {
            state.isProcessing -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
            state.errorMessage != null -> {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            "Error en el análisis",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onErrorContainer
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            state.errorMessage,
                            color = MaterialTheme.colorScheme.onErrorContainer
                        )
                    }
                }
            }
            state.currentEmotion != null -> {
                ResultCard(emotion = state.currentEmotion, isCurrentResult = true)
            }
        }

        if (state.analysisList.isNotEmpty()) {
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                "Historial de análisis",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(state.analysisList) { emotion ->
                    ResultCard(
                        emotion = emotion,
                        isCurrentResult = emotion == state.currentEmotion,
                        dateFormat = dateFormat
                    )
                }
            }
        }
    }
}

@Composable
fun ResultCard(
    emotion: EmotionResult,
    isCurrentResult: Boolean = false,
    dateFormat: SimpleDateFormat? = null
) {
    val backgroundColor = when (emotion.emotionType.lowercase()) {
        "positive" -> PositiveEmotionColor
        "neutral" -> NeutralEmotionColor
        "negative" -> NegativeEmotionColor
        else -> MaterialTheme.colorScheme.surfaceVariant
    }

    val contentColor = Color.White

    val elevation = if (isCurrentResult) 8.dp else 4.dp

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor,
            contentColor = contentColor
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = elevation)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            if (isCurrentResult) {
                Text(
                    "Resultado actual",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.labelLarge
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            val emotionText = when (emotion.emotionType.lowercase()) {
                "positive" -> "Positivo"
                "neutral" -> "Neutral"
                "negative" -> "Negativo"
                else -> emotion.emotionType
            }

            val emotionIntensity = when {
                emotion.emotionScore > 0.7 -> "Intensidad alta"
                emotion.emotionScore > 0.3 -> "Intensidad media"
                else -> "Intensidad baja"
            }

            Text("Emoción: $emotionText")
            Text("$emotionIntensity (${emotion.emotionScore})")

            if (dateFormat != null && !isCurrentResult) {
                Text(
                    "Analizado: ${dateFormat.format(Date(emotion.timestamp))}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}