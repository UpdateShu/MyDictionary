import org.gradle.api.JavaVersion

object Config {
    const val application_id = "com.geekbrains.mydictionary"
    const val compile_sdk = 33
    const val min_sdk = 29
    const val target_sdk = 33

    val java_version = JavaVersion.VERSION_17
    val jvm_target = "17"
    val test_runner = "androidx.test.runner.AndroidJUnitRunner"
}

object Releases {
    const val version_code = 1
    const val version_name = "1.0"
}

object Modules {
    const val app = ":app"
    const val utils = ":utils"
    const val entities = ":entities"
    const val repo = ":repo"
    const val viewmodel = ":viewmodel"
}

object Versions {

    //Design
    const val appcompat = "1.6.1"
    const val material = "1.5.0"
    const val constraintlayout = "2.1.4"

    //Kotlin
    const val core = "1.8.0"

    const val io_rx = "3.0.0"

    //Retrofit
    const val retrofit = "2.9.0"
    const val converterGson = "2.9.0"
    const val interceptor = "3.12.1"
    const val adapterCoroutines = "0.9.2"

    //Koin
    const val koinAndroid = "3.1.6"
    const val koinViewModel = "3.1.6"

    //Download images
    //Picasso
    const val picasso = "2.71828"
    //Glide
    const val glide = "4.13.0"
    const val glideCompiler = "4.13.0"
    //Coil
    const val coil = "0.11.0"

    //Room
    const val roomKtx = "2.5.2"
    const val runtime = "2.5.2"
    const val roomCompiler = "2.5.2"

    //Test
    const val jUnit = "4.13.2"
    const val extjUnit = "1.1.5"
    const val espressoCore = "3.2.0"
}

object Design {
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val material = "com.google.android.material:material:${Versions.material}"
    const val constraintlayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintlayout}"
}

object Kotlin {
    const val core = "androidx.core:core-ktx:${Versions.core}"
}

object IO {
    const val io_rx_java = "io.reactivex.rxjava3:rxjava:3.0.0:${Versions.io_rx}"
}

object Retrofit {
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val converter_gson = "com.squareup.retrofit2:converter-gson:${Versions.converterGson}"
    const val adapter_coroutines =
        "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:${Versions.adapterCoroutines}"
    const val logging_interceptor =
        "com.squareup.okhttp3:logging-interceptor:${Versions.interceptor}"
}

object Koin {
    const val koin_android = "io.insert-koin:koin-core:${Versions.koinAndroid}"
    const val koin_view_model = "io.insert-koin:koin-android:${Versions.koinViewModel}"
    const val koin_java_compat = "io.insert-koin:koin-android-compat:${Versions.koinViewModel}"
}

object Coil {
    const val coil = "io.coil-kt:coil:${Versions.coil}"
}

object Picasso {
    const val picasso = "com.squareup.picasso:picasso:${Versions.picasso}"
}

object Glide {
    const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    const val glideCompiler = "com.github.bumptech.glide:compiler:${Versions.glideCompiler}"
}

object Room {
    const val runtime = "androidx.room:room-runtime:${Versions.runtime}"
    const val compiler = "androidx.room:room-compiler:${Versions.roomCompiler}"
    const val room_ktx = "androidx.room:room-ktx:${Versions.roomKtx}"
}

object TestImpl {
    const val junit = "junit:junit:${Versions.jUnit}"
    const val extjUnit = "androidx.test.ext:junit:${Versions.extjUnit}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espressoCore}"
}