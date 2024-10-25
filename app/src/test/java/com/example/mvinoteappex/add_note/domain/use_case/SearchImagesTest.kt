package com.example.mvinoteappex.add_note.domain.use_case

import androidx.arch.core.executor.testing.InstantTaskExecutorRule

import com.example.mvinoteappex.addNote.domain.use_case.SearchImages
import com.example.mvinoteappex.addNote.presentation.utils.Resource
import com.example.mvinoteappex.core.data.repository.FakeImagesRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SearchImagesTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var fakeImagesRepository: FakeImagesRepository
    private lateinit var searchImages: SearchImages

    @Before
    fun setUp() {
        fakeImagesRepository = FakeImagesRepository()
        searchImages = SearchImages(fakeImagesRepository)
    }

    @Test
    fun `search images with empty query, return error`() = runTest {
        searchImages.invoke("").collect { result ->
            when (result) {
                is Resource.Error -> {
                    assertThat(result.data?.images == null).isTrue()
                }

                is Resource.Success -> Unit
                is Resource.Loading -> Unit

            }
        }
    }

    @Test
    fun `search images with a valid query but with network error, return error`() = runTest {
        fakeImagesRepository.setShouldReturnError(true)

        searchImages.invoke("").collect { result ->
            when (result) {
                is Resource.Error -> {
                    assertThat(result.data?.images == null).isTrue()
                }

                is Resource.Success -> Unit
                is Resource.Loading -> Unit

            }
        }
    }

    @Test
    fun `search images with a valid query, return success`() = runTest {
        searchImages.invoke("").collect { result ->
            when (result) {
                is Resource.Error -> Unit
                is Resource.Success -> {
                    assertThat(result.data?.images != null).isTrue()
                }

                is Resource.Loading -> Unit

            }
        }
    }

    @Test
    fun `search images with a valid query, list is not empty`() = runTest {
        searchImages.invoke("").collect { result ->
            when (result) {
                is Resource.Error -> Unit
                is Resource.Success -> {
                    assertThat(result.data?.images?.size!! > 0).isTrue()
                }

                is Resource.Loading -> Unit

            }
        }
    }
}