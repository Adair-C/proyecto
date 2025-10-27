package com.tuorg.notasmultimedia.data

import androidx.room.withTransaction
import com.tuorg.notasmultimedia.model.db.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class NoteRepository(
    private val db: AppDatabase
) {
    fun all(): Flow<List<NoteWithRelations>> =
        db.noteDao().observeAll()

    fun byType(type: ItemType): Flow<List<NoteWithRelations>> =
        db.noteDao().observeByType(type)

    fun byId(id: String): Flow<NoteWithRelations?> =
        db.noteDao().observeById(id)

    fun search(q: String): Flow<List<NoteEntity>> =
        db.noteDao().search(q)

    suspend fun upsertGraph(
        note: NoteEntity,
        attachments: List<AttachmentEntity>,
        reminders: List<ReminderEntity>
    ) = withContext(Dispatchers.IO) {
        db.withTransaction {
            db.noteDao().upsert(note)
            db.attachmentDao().deleteByNote(note.id)
            db.reminderDao().deleteByNote(note.id)

            if (attachments.isNotEmpty()) {
                db.attachmentDao().insertAll(attachments)
            }
            if (reminders.isNotEmpty()) {
                db.reminderDao().insertAll(reminders)
            }
        }
    }

    suspend fun deleteNote(note: NoteEntity) = withContext(Dispatchers.IO) {
        db.noteDao().delete(note)
    }
}
