# FreshSnap - Fruits and Vegetables Quality Detection
![freshsnap-logo](https://user-images.githubusercontent.com/73012972/173266996-7f98b804-9da8-42e6-8a49-39af240f960e.png)

FreshSnap is a shopping companion application, which is expected to help user to detect the quality of fruit and vegetables by simply capturing pictures through the camera or upload pictures stored in the smartphone gallery. After the user capture or upload image of fruit or vegetable, our application will predict the freshness percentage of the fruits and vegetables, how to keep them, and food reference based on the prediction. we also add a location information feature so that all users can find out which market with good quality ingredients and which with the bad ones.

Freshnap - Android Application
==
Description
--
Freshnap application is used for freshness detection of fruit or vegetables(certain), With the feature of reviews from other users about the freshness of fruit in other places. This app uses the Kotlin language, uses a database, and uses TFlite.

How to make the application
--
1. Create the mock-up design in Figma as an initial design idea
2. Create various pages on android applications (Splash Screen, Login Page, Register Page, Home Page, Detail Page, Identify Page, Account Page)
3. Deploy Machine Learning Model on Android with Tensor Flow Lite
4. Create upload Image using camera and get Image from gallery
5. Perform Rest API using Retrofit in Android
6. Create Architecture MVVM (Model-View-ModelView)

Used library
--
Freshsnap use some library to support the functionality of the application
1. Fragment\
To create navigation in android.
2. Glide\
To make it easier to process photos into layouts, and maintain the quality of uploaded photos.
3. Retrofit\
Networking library to handle Network Call and communicate with web server
4. Retrofit Converter Gson (2.9.0)\
Retrofit extension library to parse Javascript Object Notation (JSON) data
5. OkHttp\
To make a request to a URL.
6. JWT (JSON Web Tokens)\
To retrieve token from database
7. Material\
Displaying photos on adapter with glide


Step to Reproduce
--
1. Clone this repository
2. Checkout 'android' branch
3. Open with Android Studio or any IDE for android development
4. Wait for the gradle to be sync
5. Launch the app

Screenshot Application
--
![WhatsApp Image 2022-06-13 at 09 58 14](https://user-images.githubusercontent.com/63099572/173271730-c3cd52b7-ef7c-4300-9cc2-00f935751dd5.jpeg)
![WhatsApp Image 2022-06-13 at 09 59 50](https://user-images.githubusercontent.com/63099572/173271851-881da915-7fee-428c-b3c0-15d0833bebe6.jpeg)



