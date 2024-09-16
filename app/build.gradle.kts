plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.umda.poetryapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.umda.poetryapp"
        minSdk = 26
        targetSdk = 34
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
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    //Responsive Size Dependency
    implementation (libs.sdp.android)
    //Retrofit Dependency to generate http request but the API response is in JSON format
    implementation(libs.retrofit)
    //Gson Dependency to convert JSON to Java Object
    implementation (libs.converter.gson.v2110)

}