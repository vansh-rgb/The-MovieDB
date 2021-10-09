# The MovieDB
## _(Android, Kotlin)_

[<img src="/ScreenShots/Logo.jpg" height=300 align = center 
    hspace="10" vspace="10">](/ScreenShots/Logo.jpg)

#### Link
[Click here to download](shorturl.at/foyOT)

## About

An app to make API Request calls to The Movie Database API through Volley Library in Android. 

Used features and Libraries:
- Recycler View
- Fragments
- Kotlin Coroutines
- Volley
- Picasso
- Room Persistence Library

## Screenshots

[<img src="/ScreenShots/Home.jpg" height=300 
    hspace="10" vspace="10">](/ScreenShots/Home.jpg)
[<img src="/ScreenShots/Drawer.jpg" height=300 
    hspace="10" vspace="10">](/ScreenShots/Drawer.jpg)
[<img src="/ScreenShots/Favorites.jpg" height=300 
    hspace="10" vspace="10">](/ScreenShots/Favorites.jpg)
[<img src="/ScreenShots/HomeFav.jpg" height=300 
    hspace="10" vspace="10">](/ScreenShots/HomeFav.jpg)
[<img src="/ScreenShots/Favorites-Populated.jpg" height=300 
    hspace="10" vspace="10">](/ScreenShots/Favorites-Populated.jpg)
[<img src="/ScreenShots/No-connection.jpg" height=300 
    hspace="10" vspace="10">](/ScreenShots/No-connection.jpg)

## Guide
- [Get API key from Official MovieDB Site](https://www.themoviedb.org) 
- Create a gradle.properties file on your pc in  ``` Users\%User%\.gradle```
- In this file, put your api key as in the format:
    ```TMDB_API_KEY="your_api_key_here" ```
- In gradle.build(app) put 
- ```buildTypes.each{it.buildConfigField 'String','TMDB_API_KEY', TMDB_API_KEY}```
- Run the app

## Permissions

On Android versions prior to Android 6.0, wallabag requires the following permissions:
- Full Network Access.
- View Network Connections.
