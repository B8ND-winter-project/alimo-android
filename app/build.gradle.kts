import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs")
    id("dagger.hilt.android.plugin")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
}

val prperties = Properties()
prperties.load(project.rootProject.file("local.properties").inputStream())

android {
    namespace = "com.b1nd.alimo"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.b1nd.alimo"
        minSdk = 28
        targetSdk = 34
        versionCode = 7
        versionName = "1.2.0"

        buildConfigField("String", "SERVER_URL", "${prperties["SERVER_URL"]}")

        buildConfigField("String", "CLIENT_SECRET", "${prperties["CLIENT_SECRET"]}")

        buildConfigField("String", "CLIENT_ID", "${prperties["CLIENT_ID"]}")

        buildConfigField("String", "REDIRECT_URL", "${prperties["REDIRECT_URL"]}")

        buildConfigField("String", "DAUTH_SERVER_URL", "${prperties["DAUTH_SERVER_URL"]}")


        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    buildFeatures{
        dataBinding{
            enable = true
        }
        buildConfig = true
    }
}

dependencies {


    implementation("androidx.core:core-ktx:1.8.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.8.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")


    implementation("androidx.activity:activity-ktx:1.7.0")
    implementation("androidx.fragment:fragment-ktx:1.5.0")

    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.5.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1")

    val nav_version = "2.5.3"

    implementation("androidx.navigation:navigation-fragment-ktx:$nav_version")
    implementation("androidx.navigation:navigation-ui-ktx:$nav_version")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // firebase
    implementation(platform("com.google.firebase:firebase-bom:32.7.2"))
    implementation ("com.google.firebase:firebase-messaging")
    implementation("com.google.firebase:firebase-crashlytics")
    implementation ("com.google.firebase:firebase-analytics")

    // circle
    implementation("de.hdodenhof:circleimageview:3.1.0")

    // paging
    implementation("androidx.paging:paging-runtime-ktx:3.2.1")

    // glide
    implementation("com.github.bumptech.glide:glide:4.16.0")

    // hilt
    implementation("com.google.dagger:hilt-android:2.50")
    kapt("com.google.dagger:hilt-android-compiler:2.50")

    // ktor
    val ktor_version = "2.3.7"
    implementation("io.ktor:ktor-client-core:$ktor_version")
    implementation("io.ktor:ktor-client-cio:$ktor_version")
    implementation("io.ktor:ktor-client-content-negotiation:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")
    implementation("io.ktor:ktor-serialization-gson:$ktor_version")
    implementation("io.ktor:ktor-client-android:$ktor_version")
    implementation("io.ktor:ktor-client-logging:$ktor_version")
    implementation("io.ktor:ktor-client-auth:$ktor_version")

    // room
    val room_version = "2.6.1"
    implementation("androidx.room:room-runtime:$room_version")
    implementation("androidx.room:room-ktx:$room_version")
    kapt("androidx.room:room-compiler:$room_version")

    // okhttp
    implementation ("com.squareup.okhttp3:okhttp:4.9.3")

    // swipe refresh layout
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")

    // photo view
    implementation("com.github.chrisbanes:PhotoView:2.3.0")

    // in-app update
    implementation ("com.google.android.play:app-update:2.1.0")
    implementation ("com.google.android.play:app-update-ktx:2.1.0")

}

kapt {
    correctErrorTypes = true
}