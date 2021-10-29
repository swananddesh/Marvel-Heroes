[![API](https://img.shields.io/badge/API-23%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=23)

# Marvel
### Marvel is an Android demo application that provides the information about famous Marvel characters. This app demonstrates the clean architecture using MVVM framework. The structure of this project is based on multi module concept, i.e. there are multiple modules that is designated to perform respective task.
- _app_ module represents Presentation layer (Activities, Fragments, ViewModels etc.)
- _data_ module represents Data layer (Repositories, Data source etc.)
- _domain_ module represents Domain layer (Use cases, Entities etc.)

- APIs used from _[Marvel API](https://developer.marvel.com/docs)_ docs. You need to create an account in order to generate Public Key & Private Key to use its APIs.

## Tech stack & Open-source libraries
- Minimum SDK level 23
- [Kotlin](https://kotlinlang.org/) based + [Couroutines](https://github.com/Kotlin/kotlinx.coroutines)- for asynchronous
- Jetpack 
-- Lifecycle - dispose of observing data when lifecycle state changes.
-- ViewModel - UI related data holder, lifecycle aware.
- Architecture - MVVM
- [Hilt](https://dagger.dev/hilt/) - Dependency injection.
- [Retrofit & Moshi](https://github.com/square/retrofit) - To construct REST APIs.
- [OkHttp3](https://github.com/square/okhttp) - To implement interceptor, logging etc.
- [Picasso](https://square.github.io/picasso/) - For image rendering.
- [Mockito](https://developer.android.com/training/testing/unit-testing/local-unit-tests): For Unit Testing.

## Screenshots
[![characters.png](https://i.postimg.cc/LgXrkW9N/characters.png)](https://postimg.cc/LgXrkW9N)  [![character-details.png](https://i.postimg.cc/FdGMLv4Y/character-details.png)](https://postimg.cc/FdGMLv4Y)
