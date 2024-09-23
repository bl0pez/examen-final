plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-kapt")
}

android {
    namespace = "com.touristmap"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.touristmap"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
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

val room_version = "2.6.1"

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // LiveData con Compose, permite observar datos en la UI.
    implementation("androidx.compose.runtime:runtime-livedata:1.6.4")
    // Implementación principal de Room para persistencia de datos en SQLite.
    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")
    // To use Kotlin annotation processing tool (kapt)
    kapt("androidx.room:room-compiler:$room_version")
    implementation("androidx.room:room-ktx:2.6.1")
    // Bibliotecas para la navegación en aplicaciones de Compose.
    implementation("androidx.navigation:navigation-compose:2.5.2")
    // Biblioteca para realizar llamadas a APIs HTTP de manera sencilla.
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    // Biblioteca para cargar y mostrar imágenes en Compose, optimizada para el rendimiento.
    implementation("io.coil-kt:coil-compose:2.4.0")
    // Determina ubicación
    implementation("com.google.android.gms:play-services-location:21.3.0")
    //Open Street Map (osmdroid)
    implementation("org.osmdroid:osmdroid-android:6.1.16")
}