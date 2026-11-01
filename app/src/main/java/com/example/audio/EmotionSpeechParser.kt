package com.example.audio

import java.util.Locale

data class EmotionProfile(
    val name: String,
    val pitchMultiplier: Float, // Multiplied with voice base pitch
    val speedMultiplier: Float, // Multiplied with voice base speed
    val pauseBeforeMs: Long = 100L,
    val pauseAfterMs: Long = 150L,
    val category: String = "Emotion"
)

data class SpeechSegment(
    val spokenText: String,
    val emotion: EmotionProfile
)

object EmotionRegistry {

    private val profiles = mutableMapOf<String, EmotionProfile>()

    init {
        // Register all 76 emotions and expressive reaction tags specified by the user

        // Suspense, Mystery & Dark
        register("Mystery", pitch = 0.88f, speed = 0.90f, pauseBefore = 300L, pauseAfter = 250L)
        register("Suspense", pitch = 0.86f, speed = 0.88f, pauseBefore = 350L, pauseAfter = 300L)
        register("Unease", pitch = 0.94f, speed = 0.92f, pauseBefore = 200L, pauseAfter = 200L)
        register("Tension", pitch = 0.96f, speed = 1.05f, pauseBefore = 150L, pauseAfter = 200L)
        register("Atmospheric", pitch = 0.90f, speed = 0.88f, pauseBefore = 300L, pauseAfter = 250L)
        register("Gloomy", pitch = 0.82f, speed = 0.85f, pauseBefore = 250L, pauseAfter = 300L)

        // Sadness & Grief
        register("Sadness", pitch = 0.85f, speed = 0.82f, pauseBefore = 250L, pauseAfter = 350L)
        register("Deep Sadness", pitch = 0.78f, speed = 0.78f, pauseBefore = 400L, pauseAfter = 450L)
        register("Crying", pitch = 0.95f, speed = 0.76f, pauseBefore = 350L, pauseAfter = 400L)
        register("Whining", pitch = 1.25f, speed = 0.95f, pauseBefore = 150L, pauseAfter = 200L)
        register("Moaning", pitch = 0.80f, speed = 0.75f, pauseBefore = 300L, pauseAfter = 350L)
        register("Grumbling", pitch = 0.78f, speed = 0.88f, pauseBefore = 200L, pauseAfter = 200L)
        register("Guilt", pitch = 0.85f, speed = 0.84f, pauseBefore = 300L, pauseAfter = 300L)
        register("Shame", pitch = 0.82f, speed = 0.82f, pauseBefore = 350L, pauseAfter = 350L)

        // Shock, Fear & Panic
        register("Shock", pitch = 1.25f, speed = 1.15f, pauseBefore = 100L, pauseAfter = 300L)
        register("Fear", pitch = 1.18f, speed = 1.12f, pauseBefore = 150L, pauseAfter = 250L)
        register("Scared", pitch = 1.20f, speed = 1.15f, pauseBefore = 150L, pauseAfter = 250L)
        register("Terrified", pitch = 1.30f, speed = 1.22f, pauseBefore = 100L, pauseAfter = 300L)
        register("Panic", pitch = 1.32f, speed = 1.28f, pauseBefore = 100L, pauseAfter = 200L)
        register("Alarm", pitch = 1.22f, speed = 1.20f, pauseBefore = 100L, pauseAfter = 250L)

        // Anger & Intensity
        register("Anger", pitch = 1.12f, speed = 1.16f, pauseBefore = 100L, pauseAfter = 250L)
        register("Furious", pitch = 1.18f, speed = 1.25f, pauseBefore = 100L, pauseAfter = 300L)
        register("Shouting", pitch = 1.22f, speed = 1.22f, pauseBefore = 100L, pauseAfter = 250L)
        register("Action", pitch = 1.05f, speed = 1.20f, pauseBefore = 100L, pauseAfter = 150L)
        register("Fast-paced", pitch = 1.04f, speed = 1.30f, pauseBefore = 50L, pauseAfter = 100L)
        register("Disgust", pitch = 0.88f, speed = 0.88f, pauseBefore = 200L, pauseAfter = 300L)
        register("Jealousy", pitch = 0.95f, speed = 1.05f, pauseBefore = 150L, pauseAfter = 200L)

        // Gravity, Authority & Serious
        register("Gravity", pitch = 0.80f, speed = 0.85f, pauseBefore = 350L, pauseAfter = 350L)
        register("Serious", pitch = 0.85f, speed = 0.90f, pauseBefore = 250L, pauseAfter = 250L)
        register("Authority", pitch = 0.82f, speed = 0.92f, pauseBefore = 200L, pauseAfter = 250L)
        register("Warning", pitch = 0.90f, speed = 0.95f, pauseBefore = 250L, pauseAfter = 300L)
        register("Narrative", pitch = 0.92f, speed = 0.96f, pauseBefore = 200L, pauseAfter = 200L)
        register("History", pitch = 0.88f, speed = 0.92f, pauseBefore = 250L, pauseAfter = 250L)
        register("Descriptive", pitch = 0.95f, speed = 0.98f, pauseBefore = 150L, pauseAfter = 150L)
        register("Information", pitch = 1.00f, speed = 1.02f, pauseBefore = 100L, pauseAfter = 150L)
        register("Sophisticated", pitch = 0.92f, speed = 0.95f, pauseBefore = 200L, pauseAfter = 250L)

        // Calm, Soft & Quiet
        register("Calm", pitch = 0.92f, speed = 0.90f, pauseBefore = 250L, pauseAfter = 250L)
        register("Soft", pitch = 0.94f, speed = 0.88f, pauseBefore = 250L, pauseAfter = 250L)
        register("Quiet", pitch = 0.90f, speed = 0.86f, pauseBefore = 300L, pauseAfter = 300L)
        register("Whisper", pitch = 0.88f, speed = 0.82f, pauseBefore = 300L, pauseAfter = 350L)
        register("Whispers", pitch = 0.88f, speed = 0.82f, pauseBefore = 300L, pauseAfter = 350L)
        register("Whispering", pitch = 0.88f, speed = 0.82f, pauseBefore = 300L, pauseAfter = 350L)
        register("Silence", pitch = 1.00f, speed = 1.00f, pauseBefore = 800L, pauseAfter = 600L)
        register("Nostalgia", pitch = 0.90f, speed = 0.88f, pauseBefore = 300L, pauseAfter = 300L)
        register("Spiritual", pitch = 0.88f, speed = 0.85f, pauseBefore = 350L, pauseAfter = 350L)

        // Thinking, Doubt & Confusion
        register("Thinking", pitch = 0.94f, speed = 0.86f, pauseBefore = 350L, pauseAfter = 300L)
        register("Thoughtful", pitch = 0.90f, speed = 0.88f, pauseBefore = 300L, pauseAfter = 250L)
        register("Confusion", pitch = 1.08f, speed = 0.94f, pauseBefore = 200L, pauseAfter = 250L)
        register("Confused", pitch = 1.08f, speed = 0.94f, pauseBefore = 200L, pauseAfter = 250L)
        register("Doubt", pitch = 0.92f, speed = 0.88f, pauseBefore = 250L, pauseAfter = 250L)
        register("Hesitant", pitch = 0.96f, speed = 0.82f, pauseBefore = 350L, pauseAfter = 300L)
        register("Mumbling", pitch = 0.82f, speed = 0.88f, pauseBefore = 200L, pauseAfter = 200L)
        register("Stuttering", pitch = 1.05f, speed = 0.90f, pauseBefore = 150L, pauseAfter = 200L)
        register("Boredom", pitch = 0.80f, speed = 0.82f, pauseBefore = 300L, pauseAfter = 300L)

        // Joy, Humor & Positive
        register("Excited", pitch = 1.20f, speed = 1.18f, pauseBefore = 100L, pauseAfter = 200L)
        register("Inspirational", pitch = 1.06f, speed = 0.98f, pauseBefore = 250L, pauseAfter = 250L)
        register("Hope", pitch = 1.05f, speed = 0.95f, pauseBefore = 200L, pauseAfter = 200L)
        register("Awe", pitch = 1.10f, speed = 0.86f, pauseBefore = 350L, pauseAfter = 350L)
        register("Discovery", pitch = 1.14f, speed = 1.08f, pauseBefore = 150L, pauseAfter = 200L)
        register("Surprise", pitch = 1.22f, speed = 1.14f, pauseBefore = 100L, pauseAfter = 250L)
        register("Pride", pitch = 1.02f, speed = 0.96f, pauseBefore = 200L, pauseAfter = 200L)
        register("Relief", pitch = 0.92f, speed = 0.88f, pauseBefore = 300L, pauseAfter = 250L)
        register("Sarcastic", pitch = 1.08f, speed = 0.92f, pauseBefore = 200L, pauseAfter = 250L)
        register("Laughing", pitch = 1.15f, speed = 1.12f, pauseBefore = 150L, pauseAfter = 200L)
        register("Giggle", pitch = 1.28f, speed = 1.15f, pauseBefore = 100L, pauseAfter = 200L)
        register("Chuckle", pitch = 0.95f, speed = 1.02f, pauseBefore = 150L, pauseAfter = 200L)
        register("Snicker", pitch = 1.08f, speed = 1.05f, pauseBefore = 150L, pauseAfter = 200L)

        // Physical / Vocal Reactions & Gestures
        register("Sigh", pitch = 0.82f, speed = 0.78f, pauseBefore = 400L, pauseAfter = 400L)
        register("Gasp", pitch = 1.30f, speed = 1.15f, pauseBefore = 250L, pauseAfter = 300L)
        register("Clears throat", pitch = 0.88f, speed = 0.95f, pauseBefore = 450L, pauseAfter = 350L)
        register("Ahem", pitch = 1.05f, speed = 0.95f, pauseBefore = 350L, pauseAfter = 300L)
        register("Groan", pitch = 0.75f, speed = 0.75f, pauseBefore = 350L, pauseAfter = 350L)
        register("Yawn", pitch = 0.76f, speed = 0.70f, pauseBefore = 500L, pauseAfter = 400L)
        register("Pant", pitch = 1.15f, speed = 1.22f, pauseBefore = 200L, pauseAfter = 250L)
        register("Heavy breathing", pitch = 0.85f, speed = 0.80f, pauseBefore = 400L, pauseAfter = 400L)
        register("Exhausted", pitch = 0.78f, speed = 0.76f, pauseBefore = 400L, pauseAfter = 400L)
    }

    private fun register(name: String, pitch: Float, speed: Float, pauseBefore: Long, pauseAfter: Long) {
        val profile = EmotionProfile(name, pitch, speed, pauseBefore, pauseAfter)
        profiles[name.lowercase(Locale.ROOT)] = profile
    }

    fun getProfile(name: String): EmotionProfile? {
        val clean = name.trim().removeSurrounding("[", "]").trim().lowercase(Locale.ROOT)
        return profiles[clean]
    }

    fun getAllEmotionNames(): List<String> {
        return listOf(
            "Mystery", "Sadness", "Shock", "Unease", "Gravity", "Confusion",
            "Suspense", "Serious", "Anger", "Sarcastic", "Discovery", "Tension",
            "Deep Sadness", "Whispers", "Inspirational", "Soft", "Calm", "Nostalgia",
            "Atmospheric", "Awe", "Quiet", "Silence", "Fear", "Thinking",
            "Doubt", "Fast-paced", "Alarm", "Gloomy", "Whisper", "Thoughtful",
            "Narrative", "History", "Descriptive", "Information", "Sophisticated", "Spiritual",
            "Warning", "Authority", "Action", "Disgust", "Hope", "Sigh",
            "Gasp", "Clears throat", "Ahem", "Groan", "Snicker", "Chuckle",
            "Giggle", "Yawn", "Pant", "Heavy breathing", "Whispering", "Excited",
            "Crying", "Furious", "Confused", "Scared", "Terrified", "Laughing",
            "Exhausted", "Hesitant", "Mumbling", "Stuttering", "Panic", "Relief",
            "Guilt", "Jealousy", "Pride", "Shame", "Boredom", "Surprise",
            "Shouting", "Whining", "Moaning", "Grumbling"
        )
    }
}

object EmotionSpeechParser {

    private val emotionTagRegex = Regex("\\[([a-zA-Z\\s-]+)\\]")

    /**
     * Strips all [Emotion] tags completely so the TTS engine NEVER speaks out the tag words!
     */
    fun stripAllEmotionTags(text: String): String {
        return text.replace(emotionTagRegex, "")
            .replace(Regex("\\s{2,}"), " ")
            .trim()
    }

    /**
     * Detect the dominant emotion profile from the text.
     */
    fun detectDominantEmotion(text: String): EmotionProfile? {
        val match = emotionTagRegex.find(text) ?: return null
        val tagWord = match.groupValues[1]
        return EmotionRegistry.getProfile(tagWord)
    }

    /**
     * Parse text into ordered speech segments, matching each sentence/clause with its bracketed emotion.
     * Guaranteed: Spoken text has zero bracketed emotion words!
     */
    fun parseScriptSegments(fullText: String, defaultProfile: EmotionProfile): List<SpeechSegment> {
        val segments = mutableListOf<SpeechSegment>()
        val matches = emotionTagRegex.findAll(fullText).toList()

        if (matches.isEmpty()) {
            val clean = fullText.trim()
            if (clean.isNotBlank()) {
                segments.add(SpeechSegment(clean, defaultProfile))
            }
            return segments
        }

        var currentProfile = defaultProfile
        var lastIdx = 0

        for (match in matches) {
            val tagStart = match.range.first
            val tagEnd = match.range.last + 1

            // Text between previous tag and current tag
            if (tagStart > lastIdx) {
                val subText = fullText.substring(lastIdx, tagStart).trim()
                if (subText.isNotBlank()) {
                    segments.add(SpeechSegment(subText, currentProfile))
                }
            }

            // Update active profile for following text
            val tagName = match.groupValues[1]
            currentProfile = EmotionRegistry.getProfile(tagName) ?: currentProfile
            lastIdx = tagEnd
        }

        // Remaining text after last tag
        if (lastIdx < fullText.length) {
            val remaining = fullText.substring(lastIdx).trim()
            if (remaining.isNotBlank()) {
                segments.add(SpeechSegment(remaining, currentProfile))
            }
        }

        return segments
    }
}
