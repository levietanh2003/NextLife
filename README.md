# Github Trending Repositories [mvvmclean]

An Android app consuming [GitHub Trending API](https://githubtrendingapi.docs.apiary.io/#reference/0/repositories/list-trending-repositories) to display the current trending
Github repositories. It has been built using kotlin with clean architecture principles, Repository Pattern and MVVM
pattern as well as Architecture Components.

Min API Level Supported : 19

## Prerequisite

Before running the project check your gradle version matches the required.
```
package-name: com.mvvmclean.trendingrepos
```
    compileSdkVersion = 29
    targetSdkVersion = 29
    minSdkVersion = 19
    buildToolsVersion = "29.0.2"
    
## Development Environment

    Android Studio 3.5
    Build #AI-191.8026.42.35.5791312, built on August 9, 2019
    JRE: 1.8.0_202-release-1483-b49-5587405 x86_64
    JVM: OpenJDK 64-Bit Server VM by JetBrains s.r.o
    macOS Mojave 10.14

## Table of Contents

- [Architecture Blueprint](#architecture)
- [Features](#features)
- [Libraries](#libraries)
- [Extras](#extras)
- [Screenshots](#screenshots)

## Architecture

The Application is split into a three layer architecture inorder to provide clean separation of concerns - making the code easier to navigate and maintain.
- Data - Layer that holds APIs, Database, Cache
- Domain - Layer that holds Use Cases, and Model Objects. Business logic happens here.
- Application - Layer that holds presentation, Android components, Viewmodels, Dagger components/modules handles Dependency Injection, etc. MVVM exists at this layer.

![Data Flow Diagram](media/arch-flow.png)

The three layered architectural approach is majorly guided by clean architecture which provides
a clear separation of concerns with its Abstraction Principle.

## Features
 
 - Trending Repository Listing
 - Repository sorting by stars and name
 - Take user to corresponding repository on long press
 - Pull to refresh repository
 - Offline Storage ( Scheduled repo remote controller syncing, App will sync with backend server in every 2hrs )
 
 ## Testing

 - `app/test/ - Unit tests` - test -> Right click on package name(com.mvvmclean.trendingrepos) -> Run Test In 'com.mvvmclean.trendingrepos'
 - `app/androidTest/ - Instrumentation tests` - androidTest -> Right click on package name(com.mvvmclean.trendingrepos) -> Run Test In 'com.mvvmclean.trendingrepos'

## Libraries

Following are the Libraries used:

- [Material Design](https://material.io/develop/android/docs/getting-started/) - Google material design UIs.
- [Dagger2](https://github.com/google/dagger) - Dependency Injection lib with large community support.
- [Retrofit](https://square.github.io/retrofit/) - Network Http Client
- [Jetpack](https://developer.android.com/jetpack)
  - [Viewmodel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Channel between use cases and UI
  - [Data Binding](https://developer.android.com/topic/libraries/data-binding) - For binding of UI components in layouts to data sources, and coroutines support.
- [Moshi](https://github.com/square/moshi) - Data, Model & Entity JSON Parser that understands Kotlin non-nullable and default parameters
- [okhttp-logging-interceptor](https://github.com/square/okhttp/blob/master/okhttp-logging-interceptor/README.md) - logs HTTP request and response data.
- [Mockito](https://site.mockito.org/) - Mocking framework used in unit tests.
- [kotlinx.coroutines](https://github.com/Kotlin/kotlinx.coroutines) - Library Support for coroutines, provides `runBlocking` coroutine builder used in tests
- [MockWebServer](https://github.com/square/okhttp/tree/master/mockwebserver) - web server for testing HTTP clients.
- [Leak Canary](https://square.github.io/leakcanary/) - Leak Detection Library
- [Espresso](https://developer.android.com/training/testing/espresso) - Test framework to write UI Tests
- [recyclerview-animators](https://github.com/wasabeef/recyclerview-animators) - Recycler View Animations
- [Room Persistence Library](https://developer.android.com/topic/libraries/architecture/room) - Robust database access while harnessing the full power of SQLite
- [Robolectric](http://robolectric.org/) - Android Unit Tests framework.
- [Truth](https://truth.dev/) - Provides fluent assertions for Java and Android

## Extras

#### Gradle Dependencies

- dependencies.gradle - Centralized versioning of gradle dependencies in a global file
- Version.properties - App version details

#### Resource Values

- Fonts
- Dimension & String Values
- Themes & Styles
- Network Config

## Android Screenshots

<table>
  <tr>
    <td>
      <p align="center">
        <img src="https://github.com/user-attachments/assets/02bf4f13-117c-4cfa-bcea-128b81c69a7e" alt="Home" width="500"/>
      </p>
    </td>
    <td>
      <p align="center">
        <img src="https://github.com/user-attachments/assets/a1333d5c-5673-4c16-aa47-c1203624a0ba" alt="Detail1" width="500"/>
      </p>
    </td>
    <td>
      <p align="center">
        <img src="https://github.com/user-attachments/assets/6e92c825-dd2d-43e2-93f3-2d55785d68a6" alt="Detail2" width="500"/>
      </p>
    </td>
    <td>
      <p align="center">
        <img src="https://github.com/user-attachments/assets/a9aa40d9-1943-45fd-ba5b-c704ceb68a17" alt="Detail3" width="500"/>
      </p>
    </td>
  </tr>
  <tr>
    <td>
      <p align="center">
        <img src="https://github.com/user-attachments/assets/503df634-9c6d-49d8-83c2-5cb0aef455d4" alt="Profile" width="500"/>
      </p>
    </td>
    <td>
      <p align="center">
        <img src="https://github.com/user-attachments/assets/2b6557ca-047f-482b-ac54-b9841c71646a" alt="Wallet" width="500"/>
      </p>
    </td>
    <td>
      <p align="center">
        <img src="https://github.com/user-attachments/assets/cd0bf760-733f-466a-babd-f890bf3902f4" alt="Deposit History" width="500"/>
      </p>
    </td>
    <td>
      <p align="center">
        <img src="https://github.com/user-attachments/assets/a6028200-00dc-444e-8a42-99c8b51fa45c" alt="Edit Profile" width="500"/>
      </p>
    </td>
  </tr>
  <tr>
    <td>
      <p align="center">
        <img src="https://github.com/user-attachments/assets/919515e4-a990-408f-ad22-38dfb04035d9" alt="Post news1" width="500"/>
      </p>
    </td>
    <td>
      <p align="center">
        <img src="https://github.com/user-attachments/assets/8a027ee1-eb59-4ec8-b702-c3e31eef5715" alt="Post news2" width="500"/>
      </p>
    </td>
    <td>
      <p align="center">
        <img src="https://github.com/user-attachments/assets/8dcf695a-ce28-4ac6-833f-9797a7cfecdd" alt="Post news3" width="500"/>
      </p>
    </td>
    <td>
      <p align="center">
        <img src="https://github.com/user-attachments/assets/ec82cd07-212d-4dbe-84e7-474df2dbf4dc" alt="Post news4" width="500"/>
      </p>
    </td>
  </tr>
</table>


## Demo

[Demo Video](https://www.dropbox.com/s/1irwrefq9pqaeq2/app-demo.mp4?dl=0)

## License

 ```
   Copyright 2020 Anoop M Maddasseri

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 ```


