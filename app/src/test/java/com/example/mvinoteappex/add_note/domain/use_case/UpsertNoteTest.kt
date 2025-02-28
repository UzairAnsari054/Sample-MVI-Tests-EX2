package com.example.mvinoteappex.add_note.domain.use_case

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.mvinoteappex.addNote.domain.use_case.UpsertNote
import com.example.mvinoteappex.core.data.repository.FakeNoteRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class UpsertNoteTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var fakeNoteRepository: FakeNoteRepository
    private lateinit var upsertNote: UpsertNote

    @Before
    fun setUp() {
        fakeNoteRepository = FakeNoteRepository()
        upsertNote = UpsertNote(fakeNoteRepository)
    }


    @Test
    fun `upsert note with empty title, return false`() = runTest {
        val isInserted = upsertNote("", "description", "image")
        assertThat(isInserted).isFalse()
    }

    @Test
    fun `upsert note with empty description, return false`() = runTest {
        val isInserted = upsertNote("title", "", "image")
        assertThat(isInserted).isFalse()
    }

    @Test
    fun `upsert valid note, return true`() = runTest {
        val isInserted = upsertNote("title", "description", "image")
        assertThat(isInserted).isTrue( )
    }
}