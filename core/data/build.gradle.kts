plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.hilt)
    id("com.google.devtools.ksp")
}
android {
    namespace = "com.freedom.data"
    compileSdk = 35

    defaultConfig {
        minSdk = 24
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
    api(projects.core.common)
    implementation(projects.core.model)
    implementation(projects.core.domain)
    implementation(projects.core.network)

    ksp(libs.hilt.compiler)
    implementation(libs.hilt.android)
    implementation(libs.rxjava)

    testImplementation(libs.mockk)
    testImplementation(libs.junit)
}