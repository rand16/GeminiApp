package com.example.geminiapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.mlkit.genai.prompt.Generation
import com.google.mlkit.genai.prompt.GenerateContentResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class OnDeviceAiViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<UiState>(UiState.Initial)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    // ML Kit Prompt API Client
    private val model = Generation.getClient()

    fun sendPrompt(prompt: String) {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            try {
                val response = model.generateContent(prompt)
                // Access text via candidate if direct response.text fails
                val resultText = response.candidates.firstOrNull()?.text ?: "No text generated"
                _uiState.value = UiState.Success(resultText)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.localizedMessage ?: "Unknown error")
            }
        }
    }
}
