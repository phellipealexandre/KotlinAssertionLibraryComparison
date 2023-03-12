plugins {
    id("com.android.library")
    id("androidx.benchmark")
    id("org.jetbrains.kotlin.android")
}

android {
    compileSdk = 33

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }

    defaultConfig {
        minSdk = 23
        targetSdk = 33

        testInstrumentationRunner = "androidx.benchmark.junit4.AndroidBenchmarkRunner"
    }

    testBuildType = "release"
    buildTypes {
        debug {
            // Since isDebuggable can"t be modified by gradle for library modules,
            // it must be done in a manifest - see src/androidTest/AndroidManifest.xml
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "benchmark-proguard-rules.pro"
            )
        }
        release {
            isDefault = true
        }
    }
}

dependencies {
    androidTestImplementation(project(":benchmarkable"))

    androidTestImplementation("androidx.test:runner:1.5.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.benchmark:benchmark-junit4:1.2.0-alpha11")
    androidTestImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")
    androidTestImplementation("app.cash.turbine:turbine:0.8.0")

    androidTestImplementation("org.hamcrest:hamcrest:2.2")
    androidTestImplementation("org.hamcrest:hamcrest-library:2.2")
    androidTestImplementation("org.jetbrains.kotlin:kotlin-test-junit:1.8.0")
    androidTestImplementation("org.assertj:assertj-core:3.24.2")
    androidTestImplementation("com.google.truth:truth:1.1.3")
    androidTestImplementation("io.kotest:kotest-assertions-core:5.3.0")
    androidTestImplementation("org.amshove.kluent:kluent-android:1.65")
    androidTestImplementation("io.strikt:strikt-core:0.34.1")
    androidTestImplementation("ch.tutteli.atrium:atrium-fluent-en_GB:0.15.0")
    androidTestImplementation("com.natpryce:hamkrest:1.8.0.1")
    androidTestImplementation("com.winterbe:expekt:0.5.0")
    androidTestImplementation("com.willowtreeapps.assertk:assertk:0.25")
    androidTestImplementation("com.varabyte.truthish:truthish:0.6.5")
}