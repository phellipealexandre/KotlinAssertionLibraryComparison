plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
}

version = "1.0"

kotlin {
    android()
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        ios.deploymentTarget = "14.1"
        framework {
            baseName = "multiplatform"
        }
    }
    
    sourceSets {
        val commonMain by getting
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation("org.jetbrains.kotlin:kotlin-test-junit:1.6.21")
                implementation("org.hamcrest:hamcrest:2.2")
                implementation("org.assertj:assertj-core:3.22.0")
                implementation("com.google.truth:truth:1.1.3")
                implementation("io.kotest:kotest-assertions-core:5.3.0")
                implementation("org.amshove.kluent:kluent:1.65")
                implementation("io.strikt:strikt-core:0.34.1")
                implementation("ch.tutteli.atrium:atrium-fluent-en_GB:0.15.0")
                implementation("com.natpryce:hamkrest:1.8.0.1")
                implementation("com.winterbe:expekt:0.5.0")
                implementation("com.willowtreeapps.assertk:assertk:0.25")
                implementation("com.varabyte.truthish:truthish-js:0.6.3")
            }
        }
        val androidMain by getting
        val androidTest by getting
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }
}

android {
    compileSdk = 32
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 23
        targetSdk = 32
    }
}