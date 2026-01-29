import java.util.Properties
import kotlin.apply

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.hilt)
    id("com.google.devtools.ksp")
    id("kotlinx-serialization")
}

val keysProperties = Properties().apply {
    load(rootProject.file("keys.properties").inputStream())
}

android {
    namespace = "com.freedom.network"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        minSdk = 24
        buildConfigField(
            type ="String",
            name ="API_BASE_URL",
            value = "\"${keysProperties["API_BASE_URL"]}\""
        )
        consumerProguardFiles("consumer-rules.pro")
    }
    buildFeatures {
        buildConfig = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    ksp(libs.hilt.compiler)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.hilt.android)

    implementation(libs.kotlinx.serialization.json)
    implementation(libs.okhttp.logging)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlin.serialization)

    implementation(libs.rxjava)
    implementation(libs.retrofit.adapter.rxjava3)
    implementation(libs.androidx.annotation.experimental)

    testImplementation(libs.mockk)
    testImplementation(libs.junit)
}