import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    id("org.jetbrains.kotlin.plugin.compose") version "2.1.0"  // ✅ НОВЫЙ ПЛАГИН
}

android {
    namespace = "com.tnvdeveloper.vitalis"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.tnvdeveloper.vitalis"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables { useSupportLibrary = true }
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

    // Для AGP 8.9.x и актуального toolchain нужен JDK 17 [web:60]
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures { compose = true }

    // Для Kotlin 2.x отдельный composeCompilerExtensionVersion обычно не нужен,
    // если используется Compose BOM и актуальные зависимости.
    // Если у вас появится ошибка про Compose compiler, скажите — подберём точную связку.

    packaging {
        resources { excludes += "/META-INF/{AL2.0,LGPL2.1}" }
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

    implementation(libs.androidx.navigation.compose)

    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.coroutines.core)

    implementation(libs.vico.compose)
    implementation(libs.vico.compose.m3)
    implementation(libs.vico.core)

    implementation(libs.kotlinx.datetime)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

tasks.withType<KotlinCompile>().configureEach {
    compilerOptions {  // ✅ НОВЫЙ СИНТАКСИС
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
    }
}
