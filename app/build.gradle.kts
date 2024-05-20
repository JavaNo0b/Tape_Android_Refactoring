plugins {

    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
}

android {
    namespace = "com.janob.tape_aos"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.janob.tape_aos"
        minSdk = 30
        targetSdk = 33
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
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
        dataBinding = true
    }

}



dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.retrofit2:converter-scalars:2.9.0")
//    implementation("com.android.identity:identity-credential-android:20231002")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    //recyclerview 의존성 추가
    implementation ("androidx.recyclerview:recyclerview:1.2.1")

    //CircleIndicator
    implementation("me.relex:circleindicator:2.1.6")

    // GSON
    implementation ("com.google.code.gson:gson:2.8.5")

    // viewpager2
    implementation ("androidx.viewpager2:viewpager2:1.0.0")

    // Retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.retrofit2:adapter-rxjava2:2.9.0")
    implementation("com.squareup.retrofit2:converter-scalars:2.5.0")
    implementation("com.google.code.gson:gson:2.8.0")
    // okHttp
    implementation ("com.squareup.okhttp3:okhttp:4.9.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.9.0")

    // Glide
    implementation ("com.github.bumptech.glide:glide:4.11.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.11.0")

    //Circle ImageView
    implementation ("de.hdodenhof:circleimageview:3.1.0")

    //카카오 로그인
    implementation ("com.kakao.sdk:v2-user:2.12.1")

    //  RoomDB
    implementation("androidx.room:room-ktx:2.4.1")
    implementation("androidx.room:room-runtime:2.4.1")
    kapt ("androidx.room:room-compiler:2.4.1")

    // Indicator
    implementation ("com.tbuonomo:dotsindicator:5.0")

    //ViewModel
    implementation ("androidx.fragment:fragment-ktx:1.3.6")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")

    //searchView
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.4.1")
    //이미지 zoomin
    implementation("com.github.chrisbanes:PhotoView:2.3.0")


    //스플래쉬 이미지 gif
    implementation ("pl.droidsonroids.gif:android-gif-drawable:1.2.19")

    //기본 안드로이드 화면 삭제
    implementation ("androidx.core:core-splashscreen:1.0.0-beta01")

    //coroutine
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.4.2")
}