plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.gms.google-services")
}

android {
    namespace = "fr.isen.aliagafuentesjuanandres.isenanimalpark"
    compileSdk = 35

    defaultConfig {
        applicationId = "fr.isen.aliagafuentesjuanandres.isenanimalpark"
        minSdk = 24
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
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

    // Firebase dependencies using BoM
    implementation(platform(libs.firebase.bom))
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-database-ktx")
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation(libs.androidx.navigation.runtime.android)

    // ================= DEPENDENCIAS PRINCIPALES =================

    // Bibliotecas fundamentales de AndroidX
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // ================= JETPACK COMPOSE =================

    // Componentes base de Compose
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)

    // Material Design 3 para Compose
    implementation(libs.androidx.material3)

    // Iconos extendidos de Material Design
    implementation("androidx.compose.material:material-icons-extended:1.5.0")

    // ================= NAVEGACIÓN =================

    // Sistema de navegación para Compose
    implementation("androidx.navigation:navigation-compose:2.8.7")

    // ================= PRUEBAS UNITARIAS E INSTRUMENTADAS =================

    // Biblioteca JUnit para pruebas unitarias
    testImplementation(libs.junit)

    // Pruebas instrumentadas de AndroidX
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

    // Herramientas de depuración para Compose
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // ================= NETWORKING Y SERIALIZACIÓN =================

    // Gson para serialización/deserialización JSON
    implementation("com.google.code.gson:gson:2.10.1")

    // Retrofit para peticiones HTTP
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // OkHttp para comunicación HTTP avanzada
    implementation("com.squareup.okhttp3:okhttp:4.10.0")

    // ================= INTELIGENCIA ARTIFICIAL =================

    // Cliente Gemini AI de Google
    implementation("com.google.ai.client.generativeai:generativeai:0.4.0")

    // ================= CORRUTINAS =================

    // Soporte de corrutinas para operaciones asíncronas
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // ================= PERSISTENCIA DE DATOS =================

    // Room para base de datos SQLite
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")


    // Preferencias compartidas
    implementation("androidx.preference:preference-ktx:1.1.1")

    // ================= TAREAS EN SEGUNDO PLANO =================

    // WorkManager para gestión de tareas en segundo plano
    implementation("androidx.work:work-runtime-ktx:2.7.1")

    // ================= NOTIFICACIONES =================

    // Soporte para sistema de notificaciones
    implementation("androidx.core:core-ktx:1.8.0")

    // ================= COMPONENTES DE INTERFAZ ADICIONALES =================

    // Componentes Material 3 (versión alpha para características avanzadas)
    implementation("androidx.compose.material3:material3:1.2.0-alpha10")

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}