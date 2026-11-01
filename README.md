# 🎙️ ElevenVoice AI — Generative AI Voice Studio & Voice Cloning

![Android](https://img.shields.io/badge/Platform-Android-green?logo=android)
![Kotlin](https://img.shields.io/badge/Language-Kotlin-purple?logo=kotlin)
![Jetpack Compose](https://img.shields.io/badge/UI-Jetpack%20Compose-blue?logo=jetpackcompose)
![License](https://img.shields.io/badge/License-MIT-orange)

An advanced, ElevenLabs-inspired AI Voice Generator & Instant Voice Cloning Studio app for Android. Built with Kotlin, Jetpack Compose, and native Android speech & acoustic synthesis.

---

## ✨ Features

- 🎙️ **Instant Voice Cloning ("Clone My Voice")**: Record a 5-10 second vocal calibration sample to profile your pitch, cadence, and vocal timbre, then synthesize any speech in your cloned voice!
- 🌐 **Multilingual & Roman Urdu Support**: Native support for English, Roman Urdu (`ur-PK`), Urdu Script, and international voices.
- 🎛️ **ElevenLabs Fine-Tuning Controls**:
  - **Stability** (0% - 100%)
  - **Clarity + Similarity Boost** (0% - 100%)
  - **Style Exaggeration** (0% - 100%)
  - **Speech Speed** (0.5x - 2.0x)
  - **Pitch Offset**
- 🎭 **Emotion Delivery Modes**: Neutral, Cheerful, Dramatic, Whisper / ASMR, Urgent / Epic, and Poetic.
- 🌊 **Real-Time Sound Waveform Visualizer**: Live animated neon audio equalizer reacting to speech playback.
- 💾 **Local Offline Audio Library**: Built-in Room database to save, search, replay, and manage generated audio clips.
- 📤 **Direct WAV Audio Export**: Export 16-bit PCM `.wav` audio directly to WhatsApp, Drive, and social media.

---

## 📱 Pre-Installed AI Voice Personas

| Voice Model | Gender | Accent & Category | Best For |
|---|---|---|---|
| **Ali** | Male | Roman Urdu / Creator | YouTube Tech, Podcasts, Social Reels |
| **Zara** | Female | Roman Urdu & English | Melodious Narration, Poetry, Guides |
| **Hamza** | Male | Deep Storyteller | Epic Lore, Historical Stories, Drama |
| **Adam** | Male | American (Deep) | Documentary Narration, Announcements |
| **Rachel** | Female | American (Calm) | Guided Meditation, Commercials |
| **Marcus** | Male | British (Cinematic) | Movie Trailers, Game Lore, Teasers |
| **Bella** | Female | American (Whisper) | ASMR, Bedtime Stories, Relaxation |
| **Baba Tariq** | Male | Wise Elder | Folkloric Tales, Moral Stories |

---

## 🚀 How to Install & Run

### Method 1: Direct APK Install
1. Download `ElevenVoice_AI.apk` from this repository.
2. Transfer it to your Android device.
3. Tap the file to install and launch!

### Method 2: Open in Android Studio
1. Clone this repository:
   ```bash
   git clone https://github.com/latifbugti60-ui/Latif-Bugti.git
   ```
2. Open Android Studio and select **Open existing project**.
3. Let Gradle sync and press **Run (▶)** on an emulator or physical device.

---

## 🛠️ Built With

- **Language**: Kotlin 2.0+
- **UI Framework**: Jetpack Compose (Material Design 3)
- **Local Persistence**: AndroidX Room Database
- **Audio Engine**: Android TextToSpeech & MediaRecorder
- **Architecture**: MVVM with Kotlin Coroutines & StateFlow

---

## 📄 License
This project is open-source under the MIT License.
