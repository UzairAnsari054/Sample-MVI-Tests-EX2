package com.example.mvinoteappex.note_list.domain.use_case

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.mvinoteappex.core.data.repository.FakeNoteRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GetAllNotesTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var fakeNoteRepository: FakeNoteRepository
    private lateinit var getAllNote: GetAllNotes

    @Before
    fun setUp() {
        fakeNoteRepository = FakeNoteRepository()
        getAllNote = GetAllNotes(fakeNoteRepository)
    }

    @Test
    fun `get notes sort by title, sorted by title`() = runTest {
        fakeNoteRepository.shouldHaveFilledList(true)

        val notes = getAllNote.invoke(true)
        for (i in 0..notes.size - 2) {
            assertThat(notes[i].title).isLessThan(notes[i + 1].title)
        }
    }

    @Test
    fun `get notes sort by date added, sorted by date added`() = runTest {
        fakeNoteRepository.shouldHaveFilledList(true)

        val notes = getAllNote.invoke(false)
        for (i in 0..notes.size - 2) {
            assertThat(notes[i].dateAdded).isLessThan(notes[i + 1].dateAdded)
        }
    }

    @Test
    fun `get notes from empty list, empty list`() = runTest {
        fakeNoteRepository.shouldHaveFilledList(false)
        val notes = getAllNote.invoke(false)

        assertThat(notes.isEmpty()).isTrue()

    }
}