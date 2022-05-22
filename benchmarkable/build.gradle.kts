plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    compileSdk = 32

    defaultConfig {
        minSdk = 23
        targetSdk = 32

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
}

dependencies {

    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.appcompat:appcompat:1.4.1")
    implementation("com.google.android.material:material:1.6.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.1")
    implementation("app.cash.turbine:turbine:0.8.0")

    testImplementation("junit:junit:4.13.2")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:1.6.21")
    testImplementation("org.hamcrest:hamcrest:2.2")
    testImplementation("org.assertj:assertj-core:3.22.0")
    testImplementation("com.google.truth:truth:1.1.3")
    testImplementation("io.kotest:kotest-assertions-core:5.3.0")
    testImplementation("org.amshove.kluent:kluent:1.65")
    testImplementation("io.strikt:strikt-core:0.34.1")
    testImplementation("ch.tutteli.atrium:atrium-fluent-en_GB:0.15.0")
    testImplementation("com.natpryce:hamkrest:1.8.0.1")
    testImplementation("com.winterbe:expekt:0.5.0")
    testImplementation("com.willowtreeapps.assertk:assertk:0.25")
    testImplementation("com.varabyte.truthish:truthish:0.6.3")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.1")
    testImplementation("app.cash.turbine:turbine:0.8.0")

    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}