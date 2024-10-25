package com.example.mvinoteappex.core.data.repository

import com.example.mvinoteappex.core.data.local.NoteDatabase
import com.example.mvinoteappex.core.data.mapper.toNoteEntityForDeletion
import com.example.mvinoteappex.core.data.mapper.toNoteEntityForInsertion
import com.example.mvinoteappex.core.data.mapper.toNoteItem
import com.example.mvinoteappex.core.domain.model.NoteItem
import com.example.mvinoteappex.core.domain.repository.NoteRepository
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    noteDatabase: NoteDatabase
) : NoteRepository {

    private val noteDao = noteDatabase.getNoteDao()

    override suspend fun upsertNote(noteItem: NoteItem) {
        return noteDao.upsertNoteEntity(noteItem.toNoteEntityForInsertion())
    }

    override suspend fun deleteNote(noteItem: NoteItem) {
        return noteDao.deleteNoteEntity(noteItem.toNoteEntityForDeletion())
    }

    override suspend fun loadNotes(): List<NoteItem> {
        return noteDao.loadNotesEntity().map { it.toNoteItem() }
    }
}