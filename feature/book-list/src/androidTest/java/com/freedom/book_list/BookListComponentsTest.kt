package com.freedom.book_list

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.freedom.book_list.component.BookItem
import com.freedom.book_list.component.EmptyState
import com.freedom.book_list.component.ErrorState
import com.freedom.book_list.component.LoadingState
import com.freedom.book_list.model.BookItemUi
import com.freedom.designsystem.theme.QuilterAndroidTechTestTheme
import org.junit.Rule
import org.junit.Test

/**
 * UI tests for individual Book List components
 */
class BookListComponentsTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun loadingState_displaysLoadingIndicatorAndText() {
        // Given
        composeTestRule.setContent {
            QuilterAndroidTechTestTheme {
                LoadingState()
            }
        }

        // Then
        composeTestRule.onNodeWithText("Loading...").assertIsDisplayed()
    }

    @Test
    fun errorState_displaysErrorMessageAndRetryButton() {
        // Given
        val errorMessage = "Network error occurred"
        composeTestRule.setContent {
            QuilterAndroidTechTestTheme {
                ErrorState(
                    message = errorMessage,
                    onRetry = {}
                )
            }
        }

        // Then
        composeTestRule.onNodeWithText("Something went wrong").assertIsDisplayed()
        composeTestRule.onNodeWithText(errorMessage).assertIsDisplayed()
        composeTestRule.onNodeWithText("Retry").assertIsDisplayed()
    }

    @Test
    fun errorState_retryButtonIsClickable() {
        // Given
        var retryClicked = false
        composeTestRule.setContent {
            QuilterAndroidTechTestTheme {
                ErrorState(
                    message = "Error",
                    onRetry = { retryClicked = true }
                )
            }
        }

        // When
        composeTestRule.onNodeWithText("Retry").performClick()

        // Then
        assert(retryClicked)
    }

    @Test
    fun emptyState_displaysEmptyMessageAndHelpText() {
        // Given
        composeTestRule.setContent {
            QuilterAndroidTechTestTheme {
                EmptyState()
            }
        }

        // Then
        composeTestRule.onNodeWithText("No books found").assertIsDisplayed()
        composeTestRule.onNodeWithText("Try pulling down to refresh").assertIsDisplayed()
    }

    @Test
    fun bookItem_displaysBookInformation() {
        // Given
        val book = BookItemUi(
            title = "Test Book Title",
            authorsName = "Test Author",
            firstPublishYear = 2023,
            coverUrl = null
        )

        composeTestRule.setContent {
            QuilterAndroidTechTestTheme {
                BookItem(
                    bookItemUi = book,
                    onBookClick = {}
                )
            }
        }

        // Then
        composeTestRule.onNodeWithText("Test Book Title").assertIsDisplayed()
        composeTestRule.onNodeWithText("Test Author").assertIsDisplayed()
        composeTestRule.onNodeWithText("2023").assertIsDisplayed()
    }

    @Test
    fun bookItem_handlesNullValues() {
        // Given
        val book = BookItemUi(
            title = null,
            authorsName = null,
            firstPublishYear = null,
            coverUrl = null
        )

        composeTestRule.setContent {
            QuilterAndroidTechTestTheme {
                BookItem(
                    bookItemUi = book,
                    onBookClick = {}
                )
            }
        }

        // Then - should display default values
        composeTestRule.onNodeWithText("-").assertIsDisplayed()
    }

    @Test
    fun bookItem_isClickable() {
        // Given
        var clickedBook: BookItemUi? = null
        val book = BookItemUi(
            title = "Clickable Book",
            authorsName = "Author",
            firstPublishYear = 2023,
            coverUrl = null
        )

        composeTestRule.setContent {
            QuilterAndroidTechTestTheme {
                BookItem(
                    bookItemUi = book,
                    onBookClick = { clickedBook = it }
                )
            }
        }

        // When
        composeTestRule.onNodeWithText("Clickable Book").performClick()

        // Then
        assert(clickedBook == book)
    }
}
