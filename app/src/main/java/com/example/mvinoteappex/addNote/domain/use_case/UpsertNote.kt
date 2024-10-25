package com.example.mvinoteappex.addNote.domain.use_case

import com.example.mvinoteappex.core.domain.model.NoteItem
import com.example.mvinoteappex.core.domain.repository.NoteRepository
import javax.inject.Inject

class UpsertNote @Inject constructor(
    private val noteRepository: NoteRepository
) {
    suspend operator fun invoke(
        title: String,
        description: String,
        imageUrl: String,
    ): Boolean {

        if (title.isEmpty() || description.isEmpty()) {
            return false
        }

        val note = NoteItem(
            title = title,
            description = description,
            dateAdded = System.currentTimeMillis(),
            imageUrl = imageUrl
        )
        noteRepository.upsertNote(note)
        return true
    }
}