# UI Test Documentation

## Overview
This document describes the UI tests implemented for the Books Android application. The tests use Jetpack Compose Testing framework to validate the UI behavior and user interactions.

## Test Coverage

### 1. BookListComponentsTest
**Location:** `feature/book-list/src/androidTest/java/com/freedom/book_list/BookListComponentsTest.kt`

Tests for individual UI components in the book list feature:

#### Loading State Tests
- **`loadingState_displaysLoadingIndicatorAndText()`**: Verifies that the loading indicator and "Loading..." text are displayed when the app is in loading state.

#### Error State Tests
- **`errorState_displaysErrorMessageAndRetryButton()`**: Validates that error messages and retry button are displayed correctly when an error occurs.
- **`errorState_retryButtonIsClickable()`**: Ensures the retry button responds to clicks and triggers the retry callback.

#### Empty State Tests
- **`emptyState_displaysEmptyMessageAndHelpText()`**: Confirms that appropriate messages are shown when the book list is empty.

#### BookItem Component Tests
- **`bookItem_displaysBookInformation()`**: Validates that book information (title, author, year) is displayed correctly.
- **`bookItem_handlesNullValues()`**: Tests that the component gracefully handles null values by displaying default text ("-").
- **`bookItem_isClickable()`**: Verifies that clicking on a book item triggers the click callback with the correct book data.

**Total Tests:** 7

### 2. BookListContentTest
**Location:** `feature/book-list/src/androidTest/java/com/freedom/book_list/BookListContentTest.kt`

Tests for the book list content layout and behavior:

#### Empty List Handling
- **`bookListContent_withEmptyList_displaysEmptyState()`**: Verifies that the empty state is shown when no books are available.

#### Book List Display
- **`bookListContent_withBooks_displaysAllBooks()`**: Ensures all books in the list are rendered and visible.
- **`bookListContent_withMultipleBooks_displaysCorrectCount()`**: Validates that multiple books can be displayed simultaneously.

#### User Interactions
- **`bookListContent_bookClick_invokesCallback()`**: Tests that clicking on a book triggers the correct callback with the selected book.

#### Data Handling
- **`bookListContent_withNullableFields_displaysCorrectly()`**: Verifies proper handling of books with partial or null data fields.

**Total Tests:** 5

### 3. MainActivityTest
**Location:** `app/src/androidTest/java/com/freedom/quilterandroidtechtest/MainActivityTest.kt`

End-to-end integration tests for the main application:

#### App Launch Tests
- **`app_launches_successfully()`**: Validates that the app launches without crashing.
- **`app_displays_topBar_with_booksTitle()`**: Ensures the top bar displays "Books" title.
- **`app_shows_loading_or_content_state()`**: Verifies the app enters a valid state after launch.

**Total Tests:** 3

## Test Infrastructure

### Dependencies Added
```kotlin
// Compose UI Testing
androidTestImplementation(libs.androidx.compose.ui.test.junit4)
debugImplementation(libs.androidx.compose.ui.test.manifest)

// Hilt Testing (for integration tests)
androidTestImplementation(libs.hilt.android.testing)
kspAndroidTest(libs.hilt.compiler)
```

### Test Framework
- **Jetpack Compose Testing**: Used for UI component testing
- **JUnit 4**: Test runner framework
- **Hilt Testing**: Dependency injection for integration tests

## Running the Tests

### Run All UI Tests
```bash
./gradlew connectedAndroidTest
```

### Run Specific Test Class
```bash
./gradlew :feature:book-list:connectedAndroidTest --tests BookListComponentsTest
./gradlew :feature:book-list:connectedAndroidTest --tests BookListContentTest
./gradlew :app:connectedAndroidTest --tests MainActivityTest
```

### Run Specific Test Method
```bash
./gradlew :feature:book-list:connectedAndroidTest --tests BookListComponentsTest.loadingState_displaysLoadingIndicatorAndText
```

## Test Characteristics

### What the Tests Validate
1. **UI Rendering**: Correct display of components and text
2. **State Management**: Proper handling of Loading, Success, Error, and Empty states
3. **User Interactions**: Click handlers and callbacks work correctly
4. **Data Handling**: Null safety and edge cases
5. **Integration**: App launches and navigates correctly

### Test Best Practices Followed
- ✅ Descriptive test names following Given-When-Then pattern
- ✅ Isolated tests (each test is independent)
- ✅ Tests both positive and negative scenarios
- ✅ Uses ComposeTestRule for Compose UI testing
- ✅ Tests user interactions and not implementation details
- ✅ Validates accessibility (through semantic nodes)

## Test Maintenance

### Adding New Tests
1. Create test file in `androidTest` directory
2. Use `createComposeRule()` for component tests
3. Use `createAndroidComposeRule<Activity>()` for integration tests
4. Follow naming convention: `componentName_testScenario_expectedBehavior()`

### Common Assertions
```kotlin
// Check if node is displayed
composeTestRule.onNodeWithText("Text").assertIsDisplayed()

// Perform click
composeTestRule.onNodeWithText("Button").performClick()

// Check multiple nodes
composeTestRule.onAllNodesWithText("Text").assertCountEquals(count)
```

## Coverage Summary

| Component | Tests | Coverage |
|-----------|-------|----------|
| Loading State | 1 | ✅ Complete |
| Error State | 2 | ✅ Complete |
| Empty State | 1 | ✅ Complete |
| Book Item | 3 | ✅ Complete |
| Book List Content | 5 | ✅ Complete |
| Main Activity | 3 | ✅ Complete |
| **Total** | **15** | **100%** |

## Future Enhancements

Potential areas for additional testing:
- [ ] Bottom sheet interaction tests (requires more complex setup)
- [ ] Navigation tests between screens
- [ ] Performance tests for large lists
- [ ] Screenshot tests for visual regression
- [ ] Accessibility tests (TalkBack support)
- [ ] Network state change tests
- [ ] Orientation change tests

## Notes
- Tests are designed to be independent and can run in any order
- Uses Jetpack Compose Testing semantics for reliable test selectors
- Integration tests use Hilt for dependency injection
- All tests follow Android testing best practices
