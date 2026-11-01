package com.example.data.model

enum class VoiceEmotion(val label: String, val emoji: String, val pitchDelta: Float, val speedDelta: Float) {
    NEUTRAL("Neutral", "🎙️", 0.0f, 0.0f),
    CHEERFUL("Cheerful", "✨", 0.15f, 0.08f),
    DRAMATIC("Dramatic", "🎭", -0.15f, -0.12f),
    WHISPER("Whisper / ASMR", "🌙", 0.08f, -0.18f),
    URGENT("Urgent / Epic", "⚡", -0.05f, 0.18f),
    POETIC("Poetic (شاعری)", "📜", -0.10f, -0.10f)
}

data class VoiceSettings(
    val stability: Float = 0.75f,       // 0.0 to 1.0 (Higher = more consistent, lower = more expressive)
    val similarityBoost: Float = 0.85f, // 0.0 to 1.0 (Clarity and likeness)
    val styleExaggeration: Float = 0.20f, // 0.0 to 1.0 (Affects pitch dynamics)
    val speed: Float = 1.0f,            // 0.5 to 2.0
    val pitch: Float = 1.0f,            // 0.5 to 1.8
    val emotion: VoiceEmotion = VoiceEmotion.NEUTRAL
) {
    /**
     * Computes the effective speech rate for the TTS engine combining base rate, emotion, and speed slider.
     */
    fun computeEffectiveSpeed(baseVoiceSpeed: Float): Float {
        val total = (baseVoiceSpeed * speed) + emotion.speedDelta
        return total.coerceIn(0.4f, 2.5f)
    }

    /**
     * Computes the effective pitch for the TTS engine combining base pitch, emotion, and pitch slider.
     */
    fun computeEffectivePitch(baseVoicePitch: Float): Float {
        val total = (baseVoicePitch * pitch) + emotion.pitchDelta
        return total.coerceIn(0.4f, 2.2f)
    }
}
