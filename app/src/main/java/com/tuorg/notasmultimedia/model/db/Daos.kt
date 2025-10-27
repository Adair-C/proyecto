package com.tuorg.notasmultimedia.model.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Transaction
    @Query("SELECT * FROM notes ORDER BY createdAt DESC")
    fun observeAll(): Flow<List<NoteWithRelations>>

    @Transaction
    @Query("SELECT * FROM notes WHERE type = :type ORDER BY CASE WHEN :type = 'TASK' THEN dueAt END ASC, createdAt DESC")
    fun observeByType(type: ItemType): Flow<List<NoteWithRelations>>

    @Transaction
    @Query("SELECT * FROM notes WHERE id = :id")
    fun observeById(id: String): Flow<NoteWithRelations?>

    @Query("SELECT * FROM notes WHERE title LIKE '%' || :q || '%' OR description LIKE '%' || :q || '%' ORDER BY createdAt DESC")
    fun search(q: String): Flow<List<NoteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(note: NoteEntity)

    @Update suspend fun update(note: NoteEntity)
    @Delete suspend fun delete(note: NoteEntity)
}

@Dao
interface AttachmentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<AttachmentEntity>)
    @Query("DELETE FROM attachments WHERE noteId = :noteId")
    suspend fun deleteByNote(noteId: String)
}

@Dao
interface ReminderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<ReminderEntity>)
    @Query("DELETE FROM reminders WHERE noteId = :noteId")
    suspend fun deleteByNote(noteId: String)
}
