
# Hydration Tracker

This app is used to track water consumed per day. This app helps remind us about our goals to drink water in a day. We can set our own goals and see histories by date
## Tech Stack

* **100% Kotlin**, using coroutines for background thread
* **Splash Screen** API
* **Jetpack Compose** for UI Components
* **Work Manager** for automation task scheduler
* **Room** Database
* **Dagger hilt** for dependency injection
* **Navigation components**

## Folder Structures
The structure module used follows the guidelines of a clean architecture by dividing it into several folders with the following details
- data : Used to handle all of the transactions in the application (mainly used to handle database transaction)
- di : DI stands for Dependency Injection, use to inject class that we will need to use in our application
- navigation : Used to define navigations in the application
- theme : Used to define UI properties (color, font, shape, and theme)
- ui : Handle user interface (divided into components and screens), every screens has subfolder and every subfolder contains at least viewmodel, state, and screen
- utils : Define helper const and functions
- worker : Place for Work Manager

## Demonstration

<img src="https://raw.github.com/andriawan24/hydration-tracker/master/screenshots/screen.gif" width="300" />

## TODO
- [x] Create bottom sheet for another options
- [ ] Make options dynamic
- [ ] User management using firebase
- [ ] Find best practice for unit testing
- [x] Compose code refactor

## Contributing

Please contribute! I'm just getting my hands dirty with Jetpack Compose.
There is heavy chance that the code may/may not be correct/holding best practices. I request you to contribute/ raise issues/ send PRs so I can learn too. You can use the Github Discussion to discuss and ask questions. Or you can reach out to me on email fawaznaufal23@gmail.com

## Feedback

If you have any feedback, please reach out me at fawaznaufal23@gmail.com

