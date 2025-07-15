plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.compose.android)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.google.serivce)
    kotlin("kapt")
}

android {
    namespace = "com.happymesport.merchant"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.happymesport.merchant"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            applicationIdSuffix = ".debug"
        }
        create("uat") {
            isMinifyEnabled = true
            buildConfigField("String", "BASE_URL", "")
            applicationIdSuffix = ".uat"
        }
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
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
    buildFeatures {
        compose = true
        buildConfig = true
        viewBinding = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.core.splashscreen)

    implementation(libs.navigation.compose)
    implementation(libs.kotlinx.json.serialization)

    //logging
    implementation(libs.timber.log)

    //countryCodePicker
   // implementation(libs.country.code.picker)

//    hilt
    implementation(libs.hilt.android)
    implementation(libs.androidx.datastore.core.android)
    implementation(libs.material)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.firebase.storage.ktx)
//    implementation(libs.androidx.navigation.fragment.ktx)
//    implementation(libs.androidx.navigation.ui.ktx)
    kapt(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)

    //coil
    implementation(libs.coil.compose)
    implementation(libs.coil.network)

//    firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)
    implementation(libs.play.service.auth)

    //system-ui
    implementation(libs.android.system.ui)

    //data-store
    implementation(libs.data.store)

    // live data
//    implementation(libs.android.livedata.compose)
//    implementation(libs.android.livedata.ktx)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
