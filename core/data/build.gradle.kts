plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("kotlin-kapt")
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.nickcook.onerepmax.core.data"
    compileSdk = 34

    defaultConfig {
        minSdk = 23

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    // Hilt - DI
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    // Timber - logging
    implementation(libs.timber)

    // Other modules in the app
    implementation(project(":core:localdata"))
    implementation(project(":core:util"))

    testImplementation(project(":core:testing"))
    testImplementation(libs.junit)
    testImplementation(libs.assertk)
}

kapt {
    correctErrorTypes = true
}