package com.example.mvinoteappex.add_note.presentation

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.example.mvinoteappex.core.di.AppModule
import com.example.mvinoteappex.core.presentation.MainActivity
import com.example.mvinoteappex.core.presentation.utils.TestTags
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
class AddNoteScreenTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun addNoteScreenEndToEndTest() {
        composeRule.onNodeWithTag(TestTags.ADD_NOTE_FAB)
            .performClick()

        composeRule.onNodeWithTag(TestTags.TITLE_TEXT_FIELD)
            .performTextInput("title")

        composeRule.onNodeWithTag(TestTags.DESCRIPTION_TEXT_FIELD)
            .performTextInput("description")

        composeRule.onNodeWithTag(TestTags.NOTE_IMAGE)
            .performClick()

        composeRule.onNodeWithTag(TestTags.SEARCH_IMAGE_DIALOG)
            .assertIsDisplayed()

        composeRule.onNodeWithTag(TestTags.SEARCH_IMAGE_TEXT_FIELD)
            .performTextInput("query")

        runBlocking {
            delay(600)
        }

        composeRule.onNodeWithTag(TestTags.PICKED_IMAGE + "Image2")
            .performClick()

        composeRule.onNodeWithTag(TestTags.NOTE_IMAGE)
             .assertIsDisplayed()
    }
}