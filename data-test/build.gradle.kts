plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.arrowcode.data_test"
    compileSdk = 34

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    testImplementation(project(":data"))
    testImplementation(project(":domain"))

    testImplementation(libs.mockk)
    testImplementation(libs.retrofit)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.junit)
}