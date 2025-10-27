package com.tuorg.notasmultimedia.di

import android.content.Context
import androidx.room.Room
import com.tuorg.notasmultimedia.data.NoteRepository
import com.tuorg.notasmultimedia.model.db.AppDatabase

object Graph {
    lateinit var db: AppDatabase
    lateinit var notes: NoteRepository

    fun init(context: Context) {
        db = Room.databaseBuilder(context, AppDatabase::class.java, "notes.db").build()
        notes = NoteRepository(db)
    }
}
