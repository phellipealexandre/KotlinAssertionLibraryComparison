plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

version = "1.0"

kotlin {
    android()

    sourceSets {
        val commonMain by getting
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation("org.jetbrains.kotlin:kotlin-test-junit:1.8.0")
                implementation("org.hamcrest:hamcrest:2.2")
                implementation("org.assertj:assertj-core:3.24.2")
                implementation("com.google.truth:truth:1.1.3")
                implementation("io.kotest:kotest-assertions-core:5.3.0")
                implementation("org.amshove.kluent:kluent:1.72")
                implementation("io.strikt:strikt-core:0.34.1")
                implementation("ch.tutteli.atrium:atrium-fluent-en_GB:0.15.0")
                implementation("com.natpryce:hamkrest:1.8.0.1")
                implementation("com.winterbe:expekt:0.5.0")
                implementation("com.willowtreeapps.assertk:assertk:0.25")
                implementation("com.varabyte.truthish:truthish:0.6.5")
            }
        }
        val androidMain by getting
        val androidTest by getting
    }
}

android {
    compileSdk = 33
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 23
        targetSdk = 33
    }
}