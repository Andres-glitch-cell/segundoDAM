import java.io.File

// Auto-detect Android SDK path across Windows and Linux environments
val localPropsFile = rootProject.file("local.properties")
val validSdk = listOf(
    "C:/Android/Sdk",
    "C:/Users/Andrés/AppData/Local/Android/Sdk",
    "/home/andfersal/Android/Sdk",
    System.getenv("ANDROID_HOME") ?: "",
    System.getenv("ANDROID_SDK_ROOT") ?: ""
).firstOrNull { it.isNotBlank() && File(it).exists() }

if (validSdk != null && (!localPropsFile.exists() || !localPropsFile.readText().contains(validSdk))) {
    localPropsFile.writeText("sdk.dir=${validSdk.replace("\\", "/")}\n")
}

plugins {
id("com.android.application") version "9.5.2"
}

android {
    namespace = "com.example.practicarparaexamen"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.practicarparaexamen"
        minSdk = 24
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
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
}

dependencies {
    implementation("androidx.activity:activity-ktx:1.13.0")
    implementation("androidx.appcompat:appcompat:1.8.0")
    implementation("androidx.constraintlayout:constraintlayout:2.2.2")
    implementation("com.google.android.material:material:1.14.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.7.0")
    androidTestImplementation("androidx.test.ext:junit:1.3.0")
}
