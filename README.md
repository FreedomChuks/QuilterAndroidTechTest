# Books App - Android Tech Test

<img width="787" height="400" alt="image" src="https://github.com/user-attachments/assets/bb1ac6ca-b19f-4c5b-a5de-c93d2a2a46e4" />

## Technologies

**Languages & SDKs:** Kotlin, Android SDK 34  
**UI:** Jetpack Compose, Material Design 3, Coil  
**Networking:** Retrofit, OkHttp, Kotlinx Serialization  
**Async:** RxJava 3, RxAndroid, RxKotlin  
**Navigation:** Jetpack Navigation 3  
**DI:** Hilt  
**Testing:** JUnit 4, Mockk

## Code Structure

```
:app → Main entry point & navigation
:core:designsystem → Themes & color
:core:common → NetworkResult & exceptions
:core:domain → Business logic & interfaces
:core:data → Repository & data mapping
:core:network → API & datasource
:feature:book-list → UI & ViewModel
```

## Architecture

MVVM with clean layered architecture. UI communicates through ViewModel using StateFlow, which calls Repository via RxJava. Repository wraps API calls with error handling before returning results.

## Testing

- **BooksViewModelTest:** 8 tests (loading, success, error states)
- **BooksRepositoryImplTest:** 5 tests (mapping, filtering, error handling)
- **NetworkDataSourceImplTest:** 3 tests (API delegation & errors)
- **UI Tests:** 15 tests covering all UI components and states
  - BookListComponentsTest: 7 tests (Loading, Error, Empty states, BookItem)
  - BookListContentTest: 5 tests (List rendering, interactions, data handling)
  - MainActivityTest: 3 tests (App launch, integration)

See [UI_TEST_DOCUMENTATION.md](UI_TEST_DOCUMENTATION.md) for detailed UI test documentation.

## Notes

- Used `safeApiCall` wrapper to centralize error handling across API calls
- All exceptions are mapped to custom domain exceptions
- StateFlow manages UI state with proper lifecycle handling
- Hilt handles all dependency injection

## Build

```bash
./gradlew build
./gradlew test  # Run unit tests
./gradlew connectedAndroidTest  # Run UI tests on device/emulator
./gradlew installDebug
```

