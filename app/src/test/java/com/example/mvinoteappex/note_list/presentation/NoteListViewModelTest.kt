package com.example.mvinoteappex.note_list.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.mvinoteappex.MainCoroutineRule
import com.example.mvinoteappex.core.data.repository.FakeNoteRepository
import com.example.mvinoteappex.core.domain.model.NoteItem
import com.example.mvinoteappex.note_list.domain.use_case.DeleteNote
import com.example.mvinoteappex.note_list.domain.use_case.GetAllNotes
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class NoteListViewModelTest{
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var fakeNoteRepository: FakeNoteRepository
    private lateinit var getAllNote: GetAllNotes
    private lateinit var deleteNote: DeleteNote
    private lateinit var noteListViewModel: NoteListViewModel


    @Before
    fun setUp() {
        fakeNoteRepository = FakeNoteRepository()
        getAllNote = GetAllNotes(fakeNoteRepository)
        deleteNote = DeleteNote(fakeNoteRepository)
        noteListViewModel = NoteListViewModel(getAllNote, deleteNote)
    }

    @Test
    fun `get notes from an empty list, note list is empty`() = runTest {
        fakeNoteRepository.shouldHaveFilledList(false)

        noteListViewModel.loadNotes()
        mainCoroutineRule.dispatcher.scheduler.advanceUntilIdle() // wait fot notes to load completely

        assertThat(noteListViewModel.noteListState.value.isEmpty()).isTrue()
    }

    @Test
    fun `get notes from a filled list, note list is not empty`() = runTest {
        fakeNoteRepository.shouldHaveFilledList(true)

        noteListViewModel.loadNotes()
        mainCoroutineRule.dispatcher.scheduler.advanceUntilIdle() // wait fot notes to load completely

        assertThat(noteListViewModel.noteListState.value.isNotEmpty()).isTrue()
    }

    @Test
    fun `delete note from list, note is deleted`() = runTest {
        fakeNoteRepository.shouldHaveFilledList(true)

        noteListViewModel.loadNotes()
        mainCoroutineRule.dispatcher.scheduler.advanceUntilIdle() // wait fot notes to load completely

        val note = NoteItem("title2", "description2", "imageUrl2", 2)
        noteListViewModel.deleteNote(note)
        mainCoroutineRule.dispatcher.scheduler.advanceUntilIdle() // wait fot note to be deleted completely

        assertThat(noteListViewModel.noteListState.value.contains(note)).isFalse()
    }

    @Test
    fun `sort notes by date, notes are sorted by date`() {
        fakeNoteRepository.shouldHaveFilledList(true)

        var notes = noteListViewModel.noteListState.value
        for (i in 0..notes.size - 2) {
            assertThat(notes[i].dateAdded).isLessThan(notes[i + 1].dateAdded)
        }

        noteListViewModel.changeOrder()
        mainCoroutineRule.dispatcher.scheduler.advanceUntilIdle() // wait for order to change

        notes = noteListViewModel.noteListState.value
        for (i in 0..notes.size - 2) {
            assertThat(notes[i].title).isLessThan(notes[i + 1].title)
        }

        noteListViewModel.changeOrder()
        mainCoroutineRule.dispatcher.scheduler.advanceUntilIdle() // wait for order to change

        notes = noteListViewModel.noteListState.value
        for (i in 0..notes.size - 2) {
            assertThat(notes[i].dateAdded).isLessThan(notes[i + 1].dateAdded)
        }
    }

    @Test
    fun `sort notes by title, notes are sorted by title`() {
        fakeNoteRepository.shouldHaveFilledList(true)

        noteListViewModel.changeOrder()
        mainCoroutineRule.dispatcher.scheduler.advanceUntilIdle() // wait for order to change

        var notes = noteListViewModel.noteListState.value
        for (i in 0..notes.size - 2) {
            assertThat(notes[i].title).isLessThan(notes[i + 1].title)
        }
    }

}