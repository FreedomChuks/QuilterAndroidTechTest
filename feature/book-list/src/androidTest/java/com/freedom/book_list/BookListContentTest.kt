package com.freedom.book_list

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.freedom.book_list.model.BookItemUi
import com.freedom.book_list.model.BooksUi
import com.freedom.designsystem.theme.QuilterAndroidTechTestTheme
import org.junit.Rule
import org.junit.Test

/**
 * UI tests for BookListContent and related composables
 */
class BookListContentTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun bookListContent_withEmptyList_displaysEmptyState() {
        // Given
        val emptyBooks = emptyList<BookItemUi>()

        composeTestRule.setContent {
            QuilterAndroidTechTestTheme {
                BookListContent(
                    books = emptyBooks,
                    onBookClick = {}
                )
            }
        }

        // Then
        composeTestRule.onNodeWithText("No books found").assertIsDisplayed()
    }

    @Test
    fun bookListContent_withBooks_displaysAllBooks() {
        // Given
        val books = listOf(
            BookItemUi(
                title = "Book One",
                authorsName = "Author One",
                firstPublishYear = 2020,
                coverUrl = null
            ),
            BookItemUi(
                title = "Book Two",
                authorsName = "Author Two",
                firstPublishYear = 2021,
                coverUrl = null
            ),
            BookItemUi(
                title = "Book Three",
                authorsName = "Author Three",
                firstPublishYear = 2022,
                coverUrl = null
            )
        )

        composeTestRule.setContent {
            QuilterAndroidTechTestTheme {
                BookListContent(
                    books = books,
                    onBookClick = {}
                )
            }
        }

        // Then - all books should be displayed
        composeTestRule.onNodeWithText("Book One").assertIsDisplayed()
        composeTestRule.onNodeWithText("Book Two").assertIsDisplayed()
        composeTestRule.onNodeWithText("Book Three").assertIsDisplayed()
        
        composeTestRule.onNodeWithText("Author One").assertIsDisplayed()
        composeTestRule.onNodeWithText("Author Two").assertIsDisplayed()
        composeTestRule.onNodeWithText("Author Three").assertIsDisplayed()
    }

    @Test
    fun bookListContent_bookClick_invokesCallback() {
        // Given
        var clickedBook: BookItemUi? = null
        val book = BookItemUi(
            title = "Test Book",
            authorsName = "Test Author",
            firstPublishYear = 2023,
            coverUrl = null
        )

        composeTestRule.setContent {
            QuilterAndroidTechTestTheme {
                BookListContent(
                    books = listOf(book),
                    onBookClick = { clickedBook = it }
                )
            }
        }

        // When
        composeTestRule.onNodeWithText("Test Book").performClick()

        // Then
        assert(clickedBook == book)
    }

    @Test
    fun bookListContent_withMultipleBooks_displaysCorrectCount() {
        // Given
        val books = List(5) { index ->
            BookItemUi(
                title = "Book $index",
                authorsName = "Author $index",
                firstPublishYear = 2020 + index,
                coverUrl = null
            )
        }

        composeTestRule.setContent {
            QuilterAndroidTechTestTheme {
                BookListContent(
                    books = books,
                    onBookClick = {}
                )
            }
        }

        // Then - verify we can see multiple books
        books.forEach { book ->
            composeTestRule.onNodeWithText(book.title ?: "").assertIsDisplayed()
        }
    }

    @Test
    fun bookListContent_withNullableFields_displaysCorrectly() {
        // Given
        val books = listOf(
            BookItemUi(
                title = "Complete Book",
                authorsName = "Known Author",
                firstPublishYear = 2020,
                coverUrl = null
            ),
            BookItemUi(
                title = "Book Without Author",
                authorsName = null,
                firstPublishYear = null,
                coverUrl = null
            ),
            BookItemUi(
                title = null,
                authorsName = "Author Without Title",
                firstPublishYear = 2021,
                coverUrl = null
            )
        )

        composeTestRule.setContent {
            QuilterAndroidTechTestTheme {
                BookListContent(
                    books = books,
                    onBookClick = {}
                )
            }
        }

        // Then - books with data should display
        composeTestRule.onNodeWithText("Complete Book").assertIsDisplayed()
        composeTestRule.onNodeWithText("Known Author").assertIsDisplayed()
        composeTestRule.onNodeWithText("Book Without Author").assertIsDisplayed()
        composeTestRule.onNodeWithText("Author Without Title").assertIsDisplayed()
        
        // Default values for null fields should display
        composeTestRule.onAllNodesWithText("-").assertCountEquals(4) // 2 null titles + 1 null author + 1 for the partial
    }
}
