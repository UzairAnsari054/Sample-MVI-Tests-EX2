package com.example.mvinoteappex.note_list.domain.use_case

import com.example.mvinoteappex.core.domain.model.NoteItem
import com.example.mvinoteappex.core.domain.repository.NoteRepository
import javax.inject.Inject

class DeleteNote @Inject constructor(
    private val noteRepository: NoteRepository
) {
    suspend operator fun invoke(noteItem: NoteItem) {
        noteRepository.deleteNote(noteItem)
    }
}