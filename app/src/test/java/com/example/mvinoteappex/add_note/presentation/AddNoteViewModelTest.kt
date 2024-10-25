package com.example.mvinoteappex.add_note.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.mvinoteappex.MainCoroutineRule
import com.example.mvinoteappex.addNote.domain.use_case.SearchImages
import com.example.mvinoteappex.addNote.domain.use_case.UpsertNote
import com.example.mvinoteappex.addNote.presentation.AddNoteViewModel
import com.example.mvinoteappex.core.data.repository.FakeImagesRepository
import com.example.mvinoteappex.core.data.repository.FakeNoteRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AddNoteViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var fakeNoteRepositoryImpl: FakeNoteRepository
    private lateinit var fakeImagesRepository: FakeImagesRepository

    private lateinit var addNoteViewModel: AddNoteViewModel

    @Before
    fun setUp() {
        fakeNoteRepositoryImpl = FakeNoteRepository()
        fakeImagesRepository = FakeImagesRepository()

        val upsertNote = UpsertNote(fakeNoteRepositoryImpl)
        val searchImages = SearchImages(fakeImagesRepository)
        addNoteViewModel = AddNoteViewModel(upsertNote, searchImages)
    }

    @Test
    fun `upsert note with empty title, return false`() = runTest {
        val isInserted = addNoteViewModel.upsertNote("", "description", "image")
        assertThat(isInserted).isFalse()
    }

    @Test
    fun `upsert note with empty description, return false`() = runTest {
        val isInserted = addNoteViewModel.upsertNote("title", "", "image")
        assertThat(isInserted).isFalse()
    }

    @Test
    fun `upsert valid note, return true`() = runTest {
        val isInserted = addNoteViewModel.upsertNote("title", "description", "image")
        assertThat(isInserted).isTrue()
    }

    @Test
    fun `search image with empty query, image list is empty`() = runTest {
        addNoteViewModel.searchImages("")
        mainCoroutineRule.dispatcher.scheduler.advanceUntilIdle()

        assertThat(addNoteViewModel.addNoteState.value.imageList.isEmpty()).isTrue()
    }

    @Test
    fun `search image with a valid query but with error, image list is empty`() = runTest {
        fakeImagesRepository.setShouldReturnError(true)
        addNoteViewModel.searchImages("query")
        mainCoroutineRule.dispatcher.scheduler.advanceUntilIdle()

        assertThat(addNoteViewModel.addNoteState.value.imageList.isEmpty()).isTrue()
    }

   @Test
    fun `search image with a valid query, image list is not empty`() = runTest {
        addNoteViewModel.searchImages("query")
        mainCoroutineRule.dispatcher.scheduler.advanceUntilIdle()

        assertThat(addNoteViewModel.addNoteState.value.imageList.isNotEmpty()).isTrue()
    }

}