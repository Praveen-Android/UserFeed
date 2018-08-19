# UserFeed
Application that shows user feed and gives the ability to like/unlike pictures

# App Architecture
https://github.com/Praveen-Android/UserFeed/blob/master/App%20Architecture.png

#### The app has following packages:
1. **data**: It contains all the data access class
2. **di**: Dependency providing classes using Dagger2.
3. **ui**: View classes (activites/fragments) along with View Models.
4. **api**: Api services used in the application.
5. **utils**: Utility classes used in the application.
6. **repo**: provides all the essentail methods to interact with application's backend
7. **prefs**: provides shared preferences for the application

#### Technology stack used
1. **Kotlin** - Concise, less boiler plate code, null safe type system, smart cast and smart conditional statements
2. **RxJava2** - Powerful library with useful operators for asynchronous processing of of data as streams
3. **Dagger2** - for dependency injection so that each class can function independently and delete the creation of dependencies to Dagger 2 and due to this separation of concerns, everything can be tested in isolation 
4. **Glide** - Fluid library used for image and bitmap processing and has vast capabilities to perform various transformations
5. **RecyclerView** - Show list of items in grid layout view. Has vast improvements over a list view in terms of performance and code   structure
6. **Retrofit** - type safe rest client that provides ease of implementation, abstraction in terms of response parsing and works well with RxJava2 and Android Architectural components
7. **Android Architecture Components** - View Model and Live Data for MVVM architecture and more sepration of concerns thereby providing the flexibility
8. **Espresso** - provides framework for funtional UI testing

# Limitations
1. Low on Unit tests. Can have a very high coverage that MVVM provides
2. Lack of DB. Can be implemented as an enhancement when we scale up

# Enhancements
1. Use Room to implement a DB and save the meta data of various pictures so as to save expensive network calls expecially when scaling up
2. Monitor network connectivity based on which, we could decide whether to show a standard or low resolution images. its better to show low resolution images rather than keeping users waiting to see them in the first place. This obviously depends on product owner's requirement (high availability/throughout vs higher quality pictures)
4. Implement a job scheduler may be few times a day to fetch the latest pictures and provide notification to the user that there a new pictures to view. This keeps the user engaged with the app and therefore better retention capabilities


