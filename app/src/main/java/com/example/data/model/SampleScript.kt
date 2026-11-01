package com.example.data.model

data class SampleScript(
    val title: String,
    val category: String,
    val text: String,
    val recommendedVoiceId: String
)

object ScriptPresets {
    val samples: List<SampleScript> = listOf(
        SampleScript(
            title = "Multi-Emotion Thriller",
            category = "Storytelling",
            text = "[Mystery] The ancient library was completely silent. [Suspense] Deep between the bookshelves, a faint ticking began. [Unease] Something was moving in the dark. [Shock] Suddenly a shadow leapt forward! [Whisper] Hold your breath and do not look back.",
            recommendedVoiceId = "adam"
        ),
        SampleScript(
            title = "Roman Urdu Dramatic Story",
            category = "Roman Urdu",
            text = "[Mystery] Raat ke andhere mein ajeeb khamoshi thi. [Suspense] Achanak darwazay ke peeche se kisi ke chalne ki aawaz aayi. [Shock] Darwaza zor se band ho gaya! [Whisper] Chup raho, koi sun na le. [Relief] Shukar hai, wo sirf hawa ka jhonka tha.",
            recommendedVoiceId = "adam"
        ),
        SampleScript(
            title = "Emotional Poetry & Sigh",
            category = "Emotion",
            text = "[Sadness] Zindagi ki raahon mein kitne musafir milte hain aur bichhad jate hain. [Sigh] Jo guzar gaya wo laut kar nahi aata. [Deep Sadness] Aur dil ke konay mein sirf ek dard reh jata hai.",
            recommendedVoiceId = "adam"
        ),
        SampleScript(
            title = "Corn & Drought (Master Voice)",
            category = "Narrative",
            text = "You've eaten corn your whole life. In your cereal, your chips, your soda. It's so normal that you never even think about it. But there's another grain that needs about a third less water than corn. It survives droughts that kill corn where it stands.",
            recommendedVoiceId = "adam"
        ),
        SampleScript(
            title = "Fast-Paced Action Teaser",
            category = "Cinematic",
            text = "[Action] The engines are roaring at full throttle! [Alarm] Hostile drones detected on the radar! [Panic] We lost the secondary thruster! [Determination] Hold on tight, we are breaking through the barrier!",
            recommendedVoiceId = "marcus"
        ),
        SampleScript(
            title = "Calm Guided Sanctuary",
            category = "Conversational",
            text = "[Calm] Welcome to your quiet sanctuary. [Soft] Inhale deeply and let go of any tension. [Whisper] In this stillness, peace surrounds you completely.",
            recommendedVoiceId = "rachel"
        )
    )
}
