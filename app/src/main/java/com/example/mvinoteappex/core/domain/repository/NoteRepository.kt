package com.example.mvinoteappex.core.domain.repository

import com.example.mvinoteappex.core.domain.model.NoteItem

interface NoteRepository {

    suspend fun upsertNote(noteItem: NoteItem)
    suspend fun deleteNote(noteItem: NoteItem)
    suspend fun loadNotes(): List<NoteItem>
}