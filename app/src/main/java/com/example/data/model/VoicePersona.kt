package com.example.data.model

data class VoicePersona(
    val id: String,
    val name: String,
    val gender: String, // "Male", "Female", "My Voice"
    val accent: String, // "Custom Clone", "American", "British", "Urdu / Pakistani", etc.
    val toneTag: String, // e.g. "Instant Voice Clone", "Authoritative & Deep", "Warm & Conversational"
    val description: String,
    val category: String, // "My Cloned Voices", "Narration", "Conversational", "Cinematic", "ASMR & Soft", "Characters", "Urdu / Hindi"
    val avatarGradientStart: Long,
    val avatarGradientEnd: Long,
    val sampleQuote: String,
    val localeTag: String = "en-US",
    val defaultPitch: Float = 1.0f,
    val defaultSpeed: Float = 1.0f,
    val isCustom: Boolean = false,
    val isClonedFromMic: Boolean = false,
    val audioSamplePath: String? = null
)

object VoicePresets {
    val allVoices: List<VoicePersona> = listOf(
        VoicePersona(
            id = "adam",
            name = "Adam (User Cloned Voice)",
            gender = "Male",
            accent = "Natural Conversational",
            toneTag = "Latif Master Audio Clone",
            description = "Cloned directly from your uploaded voice recording. Warm, resonant, articulate, and conversational.",
            category = "My Cloned Voices",
            avatarGradientStart = 0xFF00E5FF,
            avatarGradientEnd = 0xFF7C3AED,
            sampleQuote = "You've eaten corn your whole life. In your cereal, your chips, your soda. It's so normal that you never even think about it. But there's another grain that needs about a third less water than corn...",
            localeTag = "en-US",
            defaultPitch = 0.92f,
            defaultSpeed = 1.02f,
            isCustom = true,
            isClonedFromMic = true,
            audioSamplePath = "raw:latif_master_voice"
        ),
        VoicePersona(
            id = "rachel",
            name = "Rachel",
            gender = "Female",
            accent = "American (Calm)",
            toneTag = "Warm & Natural",
            description = "Friendly, conversational, and serene voice ideal for podcasts, meditation, and commercial voiceovers.",
            category = "Conversational",
            avatarGradientStart = 0xFFEC4899,
            avatarGradientEnd = 0xFF8B5CF6,
            sampleQuote = "Take a deep breath. Every sound you generate carries nuance, warmth, and emotion.",
            localeTag = "en-US",
            defaultPitch = 1.05f,
            defaultSpeed = 1.0f
        ),
        VoicePersona(
            id = "ali_creator",
            name = "Ali",
            gender = "Male",
            accent = "Bilingual / Creator",
            toneTag = "Energetic & Modern",
            description = "Crisp, dynamic voice tailored for YouTube creators, tech explainers, and social media reels.",
            category = "Conversational",
            avatarGradientStart = 0xFF06B6D4,
            avatarGradientEnd = 0xFF3B82F6,
            sampleQuote = "Hey everyone! Welcome to ElevenVoice AI. Now you can clone your voice and generate speech in seconds.",
            localeTag = "en-US",
            defaultPitch = 1.02f,
            defaultSpeed = 1.06f
        ),
        VoicePersona(
            id = "zara",
            name = "Zara",
            gender = "Female",
            accent = "International / Clear",
            toneTag = "Melodious & Expressive",
            description = "Expressive bilingual voice with crystal clear articulation for stories and presentations.",
            category = "Conversational",
            avatarGradientStart = 0xFF10B981,
            avatarGradientEnd = 0xFF06B6D4,
            sampleQuote = "Welcome! Your written words are now transformed into natural, melodious speech.",
            localeTag = "en-US",
            defaultPitch = 1.1f,
            defaultSpeed = 0.98f
        ),
        VoicePersona(
            id = "hamza",
            name = "Hamza (User Cloned Voice)",
            gender = "Male",
            accent = "Natural Conversational",
            toneTag = "User Audio Recording Clone",
            description = "Cloned directly from your uploaded voice recording. Warm, resonant, and natural conversational cadence.",
            category = "My Cloned Voices",
            avatarGradientStart = 0xFFF59E0B,
            avatarGradientEnd = 0xFFEF4444,
            sampleQuote = "You've eaten corn your whole life. In your cereal, your chips, your soda. It's so normal that you never even think about it. But there's another grain that needs about a third less water than corn...",
            localeTag = "en-US",
            defaultPitch = 0.92f,
            defaultSpeed = 1.02f,
            isCustom = true,
            isClonedFromMic = true,
            audioSamplePath = "raw:latif_master_voice"
        ),
        VoicePersona(
            id = "marcus",
            name = "Marcus",
            gender = "Male",
            accent = "British (Cinematic)",
            toneTag = "Gravelly & Intense",
            description = "Heavy, movie-trailer voice made for dramatic video teasers, game lore, and suspense.",
            category = "Cinematic",
            avatarGradientStart = 0xFF6366F1,
            avatarGradientEnd = 0xFF1E1B4B,
            sampleQuote = "In a world ruled by silence, one voice rises above the storm.",
            localeTag = "en-GB",
            defaultPitch = 0.72f,
            defaultSpeed = 0.88f
        ),
        VoicePersona(
            id = "bella",
            name = "Bella",
            gender = "Female",
            accent = "American (Whisper)",
            toneTag = "Intimate & ASMR",
            description = "Soft, breathy cadence ideal for relaxation tracks, bedtime stories, and gentle narration.",
            category = "ASMR & Soft",
            avatarGradientStart = 0xFFA855F7,
            avatarGradientEnd = 0xFFF472B6,
            sampleQuote = "Close your eyes and listen closely. The gentle rhythm guides you to tranquility.",
            localeTag = "en-US",
            defaultPitch = 1.15f,
            defaultSpeed = 0.85f
        ),
        VoicePersona(
            id = "antoni",
            name = "Antoni",
            gender = "Male",
            accent = "American (Crisp)",
            toneTag = "Confident & Informative",
            description = "Crisp, executive articulation suited for news reporting, explainer videos, and tutorials.",
            category = "Narration",
            avatarGradientStart = 0xFF3B82F6,
            avatarGradientEnd = 0xFF1D4ED8,
            sampleQuote = "Breaking technological breakthroughs are happening today with advanced neural speech synthesis.",
            localeTag = "en-US",
            defaultPitch = 1.0f,
            defaultSpeed = 1.05f
        ),
        VoicePersona(
            id = "freya",
            name = "Freya",
            gender = "Female",
            accent = "Australian",
            toneTag = "Vibrant & Playful",
            description = "Bubbly, energetic, and animated voice tailored for gaming characters, ads, and social clips.",
            category = "Characters",
            avatarGradientStart = 0xFFF97316,
            avatarGradientEnd = 0xFFFBBF24,
            sampleQuote = "Hey there! Ready to level up your content with something truly vibrant?",
            localeTag = "en-AU",
            defaultPitch = 1.2f,
            defaultSpeed = 1.1f
        )
    )

    fun getById(id: String): VoicePersona {
        return allVoices.find { it.id == id } ?: allVoices.first()
    }
}
