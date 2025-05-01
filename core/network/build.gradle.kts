plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.google.ksp)
    alias(libs.plugins.dagger.hilt)
}

android {
    namespace = "com.flopez.core.network"
    compileSdk = 36

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildFeatures {
        buildConfig = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            buildConfigField(
                "String",
                "BASE_URL",
                "\"https://rickandmortyapi.com/api/\""
            )
        }


        debug {
            buildConfigField(
                "String",
                "BASE_URL",
                "\"https://rickandmortyapi.com/api/\""
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    //serialization with kotlinx
    api(libs.kotlinx.serialization.json)
    api(libs.retrofit2.kotlinx.serialization.converter)

    //retrofit
    api(libs.retrofit)
    api(libs.logging.interceptor)

    //Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
}