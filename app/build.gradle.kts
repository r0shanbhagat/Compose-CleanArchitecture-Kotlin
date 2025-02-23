plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp.google)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.roshan.sample"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.roshan.sample"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
        }
    }

    flavorDimensions.add("environment")
    productFlavors {
        create("dev") {
            resValue("string", "app_name", "Compose Clean Architecture")
            buildConfigField("String", "BASE_URL", "\"https://fakestoreapi.com/\"")

        }
        create("production") {
            resValue("string", "app_name", "Compose Clean Architecture")
            buildConfigField("String", "BASE_URL", "\"https://fakestoreapi.com/\"")

        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_19
        targetCompatibility = JavaVersion.VERSION_19
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_19.toString()
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    /**
     ******************************* Android Common Component***************************************
     **/
    implementation(libs.androidx.core.ktx)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.navigation.compose)
    implementation(libs.androidx.material.icons.extended)


    /**
     ******************************* Image Loading *************************************************
     **/
    implementation(libs.coil.compose)
    implementation(libs.coil.svg)

    /**
     ******************************* ViewModel and LiveData ****************************************
     **/
    implementation(libs.lifecycle.viewmodel.ktx)

    /**
     ******************************* DI ************************************************************
     **/
    implementation(libs.koin.android)
    implementation(libs.koin.compose)
    //implementation(libs.koin.viewmodel)

    /**
     ******************************* Network-KTOR **************************************************
     **/
    implementation(libs.ktor.core)
    implementation(libs.ktor.android)
    implementation(libs.ktor.serialization)
    implementation(libs.ktor.content.negotiation)
    implementation(libs.ktor.kotlinx.json)
    implementation(libs.ktor.logging)
    implementation(libs.ktor.okhttp)

    /**
     ******************************* Unit Testing ************************************
     **/
    debugImplementation(libs.androidx.ui.tooling)
    androidTestImplementation(libs.androidx.junit)
    testImplementation(libs.koin.test)

}