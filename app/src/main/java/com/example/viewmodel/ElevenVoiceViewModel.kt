package com.example.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.audio.PlaybackState
import com.example.audio.VoiceEngineManager
import com.example.data.db.AppDatabase
import com.example.data.db.VoiceClipEntity
import com.example.data.model.ScriptPresets
import com.example.data.model.SampleScript
import com.example.data.model.VoiceEmotion
import com.example.data.model.VoicePersona
import com.example.data.model.VoicePresets
import com.example.data.model.VoiceSettings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File

enum class StudioTab(val title: String, val icon: String) {
    STUDIO("AI Studio", "Studio"),
    VOICES("Voice Lab", "Voices"),
    HISTORY("Library", "History"),
    SETTINGS("Engine", "Settings")
}

class ElevenVoiceViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getInstance(application)
    private val dao = db.voiceClipDao()
    val audioEngine = VoiceEngineManager(application)

    // Current navigation tab
    private val _currentTab = MutableStateFlow(StudioTab.STUDIO)
    val currentTab: StateFlow<StudioTab> = _currentTab.asStateFlow()

    // Voices
    private val _customVoices = MutableStateFlow<List<VoicePersona>>(emptyList())
    val allVoices: StateFlow<List<VoicePersona>> = combine(_customVoices) { custom ->
        VoicePresets.allVoices + custom[0]
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), VoicePresets.allVoices)

    private val _selectedVoice = MutableStateFlow<VoicePersona>(VoicePresets.allVoices.first())
    val selectedVoice: StateFlow<VoicePersona> = _selectedVoice.asStateFlow()

    // Script text input
    private val _scriptText = MutableStateFlow(
        "Welcome to ElevenVoice AI Studio. Clone your own voice or select from our realistic AI personas to generate studio-grade speech instantly."
    )
    val scriptText: StateFlow<String> = _scriptText.asStateFlow()

    // Character credits quota (simulating ElevenLabs Pro tier quota)
    private val _quotaRemaining = MutableStateFlow(70000)
    val quotaRemaining: StateFlow<Int> = _quotaRemaining.asStateFlow()

    // Voice fine-tuning settings
    private val _voiceSettings = MutableStateFlow(VoiceSettings())
    val voiceSettings: StateFlow<VoiceSettings> = _voiceSettings.asStateFlow()

    // Generation state with fast percentage counter
    private val _isGenerating = MutableStateFlow(false)
    val isGenerating: StateFlow<Boolean> = _isGenerating.asStateFlow()

    private val _generationProgress = MutableStateFlow(0)
    val generationProgress: StateFlow<Int> = _generationProgress.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val _lastGeneratedClip = MutableStateFlow<VoiceClipEntity?>(null)
    val lastGeneratedClip: StateFlow<VoiceClipEntity?> = _lastGeneratedClip.asStateFlow()

    // History and search
    private val _historySearchQuery = MutableStateFlow("")
    val historySearchQuery: StateFlow<String> = _historySearchQuery.asStateFlow()

    val historyClips: StateFlow<List<VoiceClipEntity>> = _historySearchQuery
        .combine(dao.getAllClips()) { query, clips ->
            if (query.isBlank()) {
                clips
            } else {
                clips.filter {
                    it.title.contains(query, ignoreCase = true) ||
                    it.text.contains(query, ignoreCase = true) ||
                    it.voiceName.contains(query, ignoreCase = true)
                }
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Forwarding audio engine states
    val playbackState: StateFlow<PlaybackState> = audioEngine.playbackState
    val isPreviewPlaying: StateFlow<String?> = audioEngine.isPreviewPlaying
    val liveAmplitudes: StateFlow<List<Float>> = audioEngine.liveAmplitudes

    fun setTab(tab: StudioTab) {
        _currentTab.value = tab
    }

    fun setScriptText(text: String) {
        if (text.length <= 80000) {
            _scriptText.value = text
        }
    }

    fun selectVoice(voice: VoicePersona) {
        _selectedVoice.value = voice
        audioEngine.stopPreview()
    }

    fun previewVoice(voice: VoicePersona) {
        audioEngine.previewVoice(voice)
    }

    fun stopPreview() {
        audioEngine.stopPreview()
    }

    fun updateStability(value: Float) {
        _voiceSettings.value = _voiceSettings.value.copy(stability = value.coerceIn(0f, 1f))
    }

    fun updateSimilarity(value: Float) {
        _voiceSettings.value = _voiceSettings.value.copy(similarityBoost = value.coerceIn(0f, 1f))
    }

    fun updateStyleExaggeration(value: Float) {
        _voiceSettings.value = _voiceSettings.value.copy(styleExaggeration = value.coerceIn(0f, 1f))
    }

    fun updateSpeed(value: Float) {
        _voiceSettings.value = _voiceSettings.value.copy(speed = value.coerceIn(0.5f, 2.0f))
    }

    fun updatePitch(value: Float) {
        _voiceSettings.value = _voiceSettings.value.copy(pitch = value.coerceIn(0.5f, 1.8f))
    }

    fun updateEmotion(emotion: VoiceEmotion) {
        _voiceSettings.value = _voiceSettings.value.copy(emotion = emotion)
    }

    fun applySampleScript(sample: SampleScript) {
        _scriptText.value = sample.text
        VoicePresets.allVoices.find { it.id == sample.recommendedVoiceId }?.let {
            _selectedVoice.value = it
        }
    }

    fun insertEmotionTag(tag: String) {
        val current = _scriptText.value
        val tagStr = "[$tag] "
        _scriptText.value = if (current.isBlank()) tagStr else "$current $tagStr"
    }

    fun resetSettingsToDefault() {
        _voiceSettings.value = VoiceSettings()
    }

    fun resetQuota() {
        _quotaRemaining.value = 70000
    }

    fun clearError() {
        _errorMessage.value = null
    }

    /**
     * Synthesize current script into an audio file, persist to Room DB, and start playback!
     */
    fun generateSpeech() {
        val text = _scriptText.value.trim()
        if (text.isBlank()) {
            _errorMessage.value = "Please enter some text or select a sample script to generate speech."
            return
        }

        val charsNeeded = text.length
        if (_quotaRemaining.value < charsNeeded) {
            _errorMessage.value = "Not enough character credits. Tap the quota badge to refresh."
            return
        }

        viewModelScope.launch {
            _isGenerating.value = true
            _generationProgress.value = 1
            _errorMessage.value = null

            // Fast progress ticker (e.g. 5%, 18%, 35%, 62%, 88%, 100%)
            val progressJob = launch {
                var p = 5
                while (p < 95) {
                    _generationProgress.value = p
                    delay(35)
                    p += (7..14).random()
                    if (p > 95) p = 95
                }
            }

            try {
                val voice = _selectedVoice.value
                val settings = _voiceSettings.value

                val audioFile = audioEngine.synthesizeSpeechToFile(text, voice, settings)
                progressJob.cancel()
                _generationProgress.value = 100
                delay(80)

                if (audioFile != null && audioFile.exists()) {
                    val fileLength = audioFile.length()
                    // Deduct quota
                    _quotaRemaining.value = (_quotaRemaining.value - charsNeeded).coerceAtLeast(0)

                    // Estimate duration in ms roughly if needed or read via MediaPlayer
                    val estimatedDurMs = ((text.length * 75L) / settings.speed).toLong().coerceAtLeast(1200L)

                    val detected = com.example.audio.EmotionSpeechParser.detectDominantEmotion(text)
                    val cleanText = com.example.audio.EmotionSpeechParser.stripAllEmotionTags(text)
                    val titleExcerpt = if (cleanText.length > 36) cleanText.take(36) + "..." else cleanText.ifBlank { text.take(36) }

                    val entity = VoiceClipEntity(
                        title = titleExcerpt,
                        text = text,
                        voiceId = voice.id,
                        voiceName = voice.name,
                        voiceAccent = voice.accent,
                        filePath = audioFile.absolutePath,
                        durationMs = estimatedDurMs,
                        timestamp = System.currentTimeMillis(),
                        fileSizeBytes = fileLength,
                        emotion = detected?.name ?: settings.emotion.label,
                        speed = settings.speed,
                        pitch = settings.pitch,
                        isFavorite = false
                    )

                    val newId = dao.insertClip(entity)
                    val saved = entity.copy(id = newId)
                    _lastGeneratedClip.value = saved

                    // Auto-start playback of newly synthesized audio
                    audioEngine.playAudioFile(audioFile.absolutePath)
                } else {
                    _errorMessage.value = "Speech synthesis failed. Please ensure your device has TTS installed."
                }
            } catch (e: Exception) {
                _errorMessage.value = "Generation error: ${e.message}"
            } finally {
                progressJob.cancel()
                _isGenerating.value = false
                _generationProgress.value = 0
            }
        }
    }

    fun playAudioFile(filePath: String) {
        audioEngine.playAudioFile(filePath)
    }

    fun pausePlayback() {
        audioEngine.pausePlayback()
    }

    fun resumePlayback() {
        audioEngine.resumePlayback()
    }

    fun seekTo(ms: Long) {
        audioEngine.seekTo(ms)
    }

    fun stopPlayback() {
        audioEngine.stopPlayback()
    }

    private val _downloadMessage = MutableStateFlow<String?>(null)
    val downloadMessage: StateFlow<String?> = _downloadMessage.asStateFlow()

    fun downloadClip(clip: VoiceClipEntity) {
        val dest = audioEngine.downloadAudio(getApplication(), clip.filePath, clip.title)
        if (dest != null) {
            _downloadMessage.value = "Saved to: $dest"
        } else {
            _downloadMessage.value = "Download failed."
        }
    }

    fun clearDownloadMessage() {
        _downloadMessage.value = null
    }

    fun shareClip(clip: VoiceClipEntity) {
        audioEngine.shareAudio(getApplication(), clip.filePath, clip.title)
    }

    fun toggleFavorite(clip: VoiceClipEntity) {
        viewModelScope.launch {
            val newFav = !clip.isFavorite
            dao.setFavorite(clip.id, newFav)
            if (_lastGeneratedClip.value?.id == clip.id) {
                _lastGeneratedClip.value = _lastGeneratedClip.value?.copy(isFavorite = newFav)
            }
        }
    }

    fun deleteClip(clip: VoiceClipEntity) {
        viewModelScope.launch {
            dao.deleteClip(clip)
            try {
                val f = File(clip.filePath)
                if (f.exists()) f.delete()
            } catch (e: Exception) {
                // Ignore file deletion errors
            }
            if (_lastGeneratedClip.value?.id == clip.id) {
                _lastGeneratedClip.value = null
            }
        }
    }

    fun cloneMyVoice(
        name: String,
        gender: String,
        tone: String,
        pitch: Float,
        speed: Float,
        audioSamplePath: String
    ) {
        val clonedPersona = VoicePersona(
            id = "my_voice_${System.currentTimeMillis()}",
            name = if (name.isBlank()) "My Voice" else name,
            gender = gender,
            accent = "Instant Voice Clone",
            toneTag = tone,
            description = "Cloned from personal voice sample with calibrated timbre and pitch contour.",
            category = "My Cloned Voices",
            avatarGradientStart = 0xFF06B6D4,
            avatarGradientEnd = 0xFF7C3AED,
            sampleQuote = "This is my cloned AI voice synthesized with ElevenVoice Studio.",
            localeTag = "en-US",
            defaultPitch = pitch,
            defaultSpeed = speed,
            isCustom = true,
            isClonedFromMic = true,
            audioSamplePath = audioSamplePath
        )
        _customVoices.value = listOf(clonedPersona) + _customVoices.value
        _selectedVoice.value = clonedPersona
    }

    fun addCustomVoice(
        name: String,
        accent: String,
        tone: String,
        description: String,
        gender: String,
        category: String,
        pitch: Float,
        speed: Float,
        sampleQuote: String,
        localeTag: String
    ) {
        val newVoice = VoicePersona(
            id = "custom_${System.currentTimeMillis()}",
            name = name,
            gender = gender,
            accent = accent,
            toneTag = tone,
            description = description,
            category = category,
            avatarGradientStart = 0xFF8B5CF6,
            avatarGradientEnd = 0xFFEC4899,
            sampleQuote = sampleQuote.ifBlank { "Hello, this is my custom cloned voice profile." },
            localeTag = localeTag,
            defaultPitch = pitch,
            defaultSpeed = speed,
            isCustom = true
        )
        _customVoices.value = _customVoices.value + newVoice
        _selectedVoice.value = newVoice
    }

    fun setSearchQuery(query: String) {
        _historySearchQuery.value = query
    }

    override fun onCleared() {
        super.onCleared()
        audioEngine.release()
    }
}
