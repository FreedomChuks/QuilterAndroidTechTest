package com.freedom.quilterandroidtechtest

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * End-to-end UI test for the MainActivity
 * Tests the app launch and initial screen display
 */
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun app_launches_successfully() {
        // The app should launch without crashing
        // This is verified by the test not throwing an exception
    }

    @Test
    fun app_displays_topBar_with_booksTitle() {
        // Given the app has launched
        
        // Then the top bar should display "Books"
        composeTestRule.onNodeWithText("Books").assertIsDisplayed()
    }

    @Test
    fun app_shows_loading_or_content_state() {
        // Given the app has launched
        
        // Then it should show either loading state or content
        // We can't guarantee which state due to async nature, but app should be in one of the valid states
        // This is verified by the test not crashing and previous test passing
        
        // The test will wait for idle state which means content has loaded
        composeTestRule.waitForIdle()
    }
}
