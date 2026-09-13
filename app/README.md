# 8227457Assignment2

NIT3213 Final Assignment Android application.

The app authenticates users with the supplied NIT3213 API and displays investment data across three screens:

- Login
- Dashboard
- Details

## Features

- Login using the Sydney NIT3213 authentication endpoint
- Dashboard displaying API data in a RecyclerView
- Details screen showing all information for the selected investment
- Hilt dependency injection
- Retrofit API integration
- Kotlin Coroutines and StateFlow
- Unit tests for LoginViewModel and DashboardViewModel
- Custom investment-themed user interface
- Illustrative investment trend chart
- Password visibility toggle
- Portfolio and Available Funds interface cards
- Custom application launcher icon

## API

Base URL:

https://nit3213apinew.onrender.com/

Authentication endpoint:

POST /sydney/auth

Dashboard endpoint:

GET /dashboard/{keypass}

Login credentials follow the assignment requirements:

Username: Student ID without the "s"
Password: Student first name, case-sensitive

After a successful login, the API returns a keypass which is used to retrieve the Dashboard data.

## Architecture

The application uses a single Activity with multiple Fragments.

Application flow:

Fragment / UI
↓
ViewModel
↓
Repository
↓
ApiService
↓
NIT3213 API

Main screens:

- LoginFragment
- DashboardFragment
- DetailsFragment

LoginFragment and DashboardFragment use ViewModels to manage UI state and API operations.

The Details screen receives the selected investment information and displays it in a user-friendly layout.

## Project Structure

com.example.s8227457assignment2

- data
    - model
    - network
    - repository
- di
- ui
    - login
    - dashboard
    - details
- MainActivity
- MyApplication

## Dependency Injection

Hilt is used for dependency injection.

The project uses:

- @HiltAndroidApp
- @AndroidEntryPoint
- @HiltViewModel
- @Inject
- @Module
- @Provides
- @InstallIn
- @Singleton

The NetworkModule provides the Retrofit, Moshi, OkHttp and ApiService dependencies.

## Networking

Retrofit is used to communicate with the NIT3213 API.

Moshi is used to convert JSON responses into Kotlin data classes.

OkHttp is used as the HTTP client.

HTTP logging is configured using BASIC logging rather than BODY logging so login request information is not printed in full to Logcat.

## Coroutines and StateFlow

Kotlin Coroutines are used for asynchronous operations.

API requests are defined as Retrofit suspend functions and are launched from ViewModels using viewModelScope.

This allows network operations to run without blocking the Android user interface.

StateFlow is used to send UI state from the ViewModels to the Fragments.

The application does not manually create or manage Java threads.

## RecyclerView

The Dashboard uses a RecyclerView to display the investment entities returned by the API.

Each item displays a summary of the investment without the description.

Selecting an investment opens the Details screen.

## Trend Chart

The supplied NIT3213 API provides the current price of each investment but does not provide historical market price data.

For this reason, the Details screen generates deterministic illustrative trend data based on the current API price.

The final point of each generated trend matches the current API price.

Available chart ranges:

- 1D
- 1W
- 1M
- 1Y
- ALL

The chart is not intended to represent real historical financial market data.

## Dependencies

The project uses:

- Kotlin
- Android Views / XML
- ConstraintLayout
- Material Components
- RecyclerView
- Android Navigation Component
- Retrofit
- Moshi
- OkHttp
- Hilt
- Kotlin Coroutines
- StateFlow
- JUnit
- MockK
- kotlinx-coroutines-test

## Development Requirements

- Java 17
- Compile SDK 37
- Target SDK 37
- Minimum SDK 24
- Android Studio
- Internet connection

An internet connection is required because the application communicates with the NIT3213 API.

## How to Build the Application

1. Clone or download the repository.
2. Open Android Studio.
3. Open the 8227457Assignment2 project folder.
4. Wait for Gradle sync to complete.
5. Ensure the required Android SDK is installed.
6. Select an Android emulator or connected Android device.
7. Select Build > Assemble Project.
8. Confirm that the project builds successfully.

## How to Run the Application

1. Open the project in Android Studio.
2. Select the app run configuration.
3. Select an emulator or connected Android device.
4. Press the Run button.
5. Enter the NIT3213 login credentials.
6. Use the Student ID without the "s" as the username.
7. Use the student's first name as the password. The password is case-sensitive.
8. Press Sign In.
9. After successful login, the Dashboard will load the investment data.
10. Select any investment to open the Details screen.

## Unit Testing

Unit tests are located in:

app/src/test/

The project includes:

- LoginViewModelTest
- DashboardViewModelTest

The tests cover:

- Successful login
- Login input validation
- Successful Dashboard loading
- Dashboard keypass validation
- ViewModel state updates

To run the tests in Android Studio:

1. Open app/src/test/.
2. Locate the required test class.
3. Right-click the test class.
4. Select Run.
5. Confirm that all tests pass.

## Error Handling

The application handles common failure conditions including:

- Empty username
- Empty password
- Invalid login credentials
- Missing Dashboard keypass
- Failed Dashboard requests
- Network request failures

Appropriate error messages are displayed to the user.

## User Interface

The application uses a custom dark investment-themed interface featuring:

- Dark background
- Magenta and neon accent colours
- Custom fonts
- Custom application launcher icon
- Investment cards
- Portfolio and Available Funds cards
- Balance visibility controls
- Interactive trend chart controls

## Additional Features

Additional features include:

- Interactive investment trend charts
- Multiple chart time ranges
- Custom PriceTrendView
- Portfolio and Available Funds interface cards
- Independent balance visibility controls
- Password visibility control
- Custom application launcher icon

These additional features do not replace the required assignment functionality.

## Git Version Control

Git was used throughout development.

The repository contains multiple commits representing major development milestones including:

- Initial Android project setup
- Core architecture dependencies
- Hilt dependency injection
- Navigation and screen setup
- API models and networking
- Repository implementation
- Login functionality
- Dashboard functionality
- Details functionality
- Unit testing
- UI redesign
- Trend chart implementation
- Custom application icon
- Final project cleanup

Meaningful commit messages were used to maintain a clear project history.

## Security

Login credentials are not hard-coded into the application.

The user enters their credentials on the Login screen and they are sent to the supplied authentication API.

Detailed HTTP BODY logging is not enabled in the final application configuration.

## Project Name

8227457Assignment2

This follows the required StudentIDAssignment2 naming format.

## Author

NIT3213 Final Assignment

Bachelor of Information Technology