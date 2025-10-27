package com.tuorg.notasmultimedia.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tuorg.notasmultimedia.di.Graph
import com.tuorg.notasmultimedia.model.db.*
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.*

class EditViewModel : ViewModel() {
    private val repo = Graph.notes

    // Estado de formulario (min 50% funcional: título, descr., tipo, dueAt, completed, recordatorio simple)
    var title: String = ""
    var description: String = ""
    var type: ItemType = ItemType.TASK
    var dueAt: LocalDateTime? = null
    var completed: Boolean = false

    private val noteId: String = UUID.randomUUID().toString()

    fun save() {
        viewModelScope.launch {
            val note = NoteEntity(
                id = noteId,
                title = title,
                description = description,
                type = type,
                createdAt = LocalDateTime.now(),
                dueAt = dueAt,
                completed = completed
            )
            val reminders = if (type == ItemType.TASK && dueAt != null)
                listOf(ReminderEntity(noteId = noteId, triggerAt = dueAt!!))
            else emptyList()

            repo.upsertGraph(note, attachments = emptyList(), reminders = reminders)
        }
    }
}
