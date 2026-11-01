package com.example.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "voice_clips")
data class VoiceClipEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val text: String,
    val voiceId: String,
    val voiceName: String,
    val voiceAccent: String,
    val filePath: String,
    val durationMs: Long,
    val timestamp: Long = System.currentTimeMillis(),
    val fileSizeBytes: Long = 0L,
    val emotion: String = "Neutral",
    val speed: Float = 1.0f,
    val pitch: Float = 1.0f,
    val isFavorite: Boolean = false
)
