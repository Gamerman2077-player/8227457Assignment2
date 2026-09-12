plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.s8227457assignment2"

    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "com.example.s8227457assignment2"

        minSdk = 24
        targetSdk = 37

        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner =
            "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {

    // -------------------------
    // Core Android
    // -------------------------

    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.core.ktx)
    implementation(libs.material)


    // -------------------------
    // RecyclerView
    // -------------------------

    implementation("androidx.recyclerview:recyclerview:1.4.0")


    // -------------------------
    // ViewModel / Lifecycle
    // -------------------------

    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.10.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.10.0")


    // -------------------------
    // Navigation
    // -------------------------

    implementation("androidx.navigation:navigation-fragment-ktx:2.9.5")
    implementation("androidx.navigation:navigation-ui-ktx:2.9.5")


    // -------------------------
    // Retrofit / Networking
    // -------------------------

    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.11.0")

    implementation("com.squareup.moshi:moshi-kotlin:1.15.2")

    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")


    // -------------------------
    // Unit Testing
    // -------------------------

    testImplementation(libs.junit)
    testImplementation("io.mockk:mockk:1.14.6")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.10.2")


    // -------------------------
    // Android Testing
    // -------------------------

    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
}