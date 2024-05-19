<img src="/app/src/main/res/mipmap-xxxhdpi/ic_launcher_round.webp">

# One Rep Max

A sample application that given a set of data for historical workouts, will calculate the theoretical one-rep max using the Brzycki formula.

## Overview
On the main screen, users can see their list of workouts and what the record calculated one-rep max is for each one.
Clicking on a list entry will take the users to a graph that shows them their calculated one-rep max over time. The graph will show only the record one-rep max for each day.

## Architecture
The app is written in Kotlin and uses Jetpack Compose for creating views. It follows the MVVM pattern for separation of concerns.
Different functionality is split into separate modules.
- app: View and view models
- core: Houses all functionality outside of Android-dependent code
- core:data: Includes repository for fetching data and model classes to represent that data
- core:localdata: Includes code for reading the history workout data from the given txt file
- core:testing: Includes common code that can be used for UI and unit tests
- core:util: Includes utility functions for date parsing/formatting and for calculating one-rep max

## Running the app
The app should run as-is after cloning the repository. If you wish to test a new set of data, replace the contents of [workoutData.txt](/core/localdata/src/main/assets/workoutData.txt) with new data.

The app is built under the assumption that any data in that file will follow the same pattern that already exists:

Date of workout (MMM dd yyyy),Exercise Name,Reps,Weight
