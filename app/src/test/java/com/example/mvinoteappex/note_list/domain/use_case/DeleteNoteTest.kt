package com.example.mvinoteappex.note_list.domain.use_case

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.mvinoteappex.core.data.repository.FakeNoteRepository
import com.example.mvinoteappex.core.domain.model.NoteItem
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DeleteNoteTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var fakeNoteRepository: FakeNoteRepository
    private lateinit var deleteNote: DeleteNote

    @Before
    fun setUp() {
        fakeNoteRepository = FakeNoteRepository()
        deleteNote = DeleteNote(fakeNoteRepository)
    }


    @Test
    fun `delete note from list, note is deleted`() = runTest {
        fakeNoteRepository.shouldHaveFilledList(true)

        val note = NoteItem(
            "title2",
            "description2",
            "imageUrl2",
            2
        )

        deleteNote.invoke(note)
        assertThat(fakeNoteRepository.loadNotes().contains(note)).isFalse()

    }
}