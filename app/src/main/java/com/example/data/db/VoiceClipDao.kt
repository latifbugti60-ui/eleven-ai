package com.example.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface VoiceClipDao {
    @Query("SELECT * FROM voice_clips ORDER BY timestamp DESC")
    fun getAllClips(): Flow<List<VoiceClipEntity>>

    @Query("SELECT * FROM voice_clips WHERE isFavorite = 1 ORDER BY timestamp DESC")
    fun getFavoriteClips(): Flow<List<VoiceClipEntity>>

    @Query("SELECT * FROM voice_clips WHERE id = :id LIMIT 1")
    suspend fun getClipById(id: Long): VoiceClipEntity?

    @Query("SELECT * FROM voice_clips WHERE text LIKE '%' || :query || '%' OR title LIKE '%' || :query || '%' OR voiceName LIKE '%' || :query || '%' ORDER BY timestamp DESC")
    fun searchClips(query: String): Flow<List<VoiceClipEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertClip(clip: VoiceClipEntity): Long

    @Update
    suspend fun updateClip(clip: VoiceClipEntity)

    @Query("UPDATE voice_clips SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun setFavorite(id: Long, isFavorite: Boolean)

    @Delete
    suspend fun deleteClip(clip: VoiceClipEntity)

    @Query("DELETE FROM voice_clips WHERE id = :id")
    suspend fun deleteClipById(id: Long)
}
