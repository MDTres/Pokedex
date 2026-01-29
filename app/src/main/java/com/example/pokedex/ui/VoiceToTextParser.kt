package com.example.pokedex.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import java.util.Locale

class VoiceToTextParser(private val context: Context){
    private val _isListening= mutableStateOf(false)
    val isListening : State<Boolean> = _isListening

    private val recognizer = SpeechRecognizer.createSpeechRecognizer(context)

    fun startListening(onResult: (String)->Unit){
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        }
        recognizer.setRecognitionListener(object : RecognitionListener{
            override fun onReadyForSpeech(params: Bundle?) {
                _isListening.value=true
            }

            override fun onResults(result : Bundle?) {
                val data= result?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                data?.get(0)?.let { text ->
                    onResult(text)
                }
                _isListening.value=false
            }

            override fun onError(error: Int) {
              _isListening.value=false
            }
            override fun onBeginningOfSpeech() {}
            override fun onBufferReceived(buffer: ByteArray?) {}
            override fun onEndOfSpeech() { _isListening.value = false }
            override fun onEvent(eventType: Int, params: Bundle?) {}
            override fun onPartialResults(partialResults: Bundle?) {}
            override fun onRmsChanged(rmsdB: Float) {}
        })
        recognizer.startListening(intent)
    }
}