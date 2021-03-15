import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.31"
    application
}

group = "me.phellipesilva"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation("junit:junit:4.12")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.2")

    testImplementation(kotlin("test-junit"))
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:1.4.10")
    testImplementation("org.hamcrest:hamcrest:2.2")
    testImplementation("org.assertj:assertj-core:3.19.0")
    testImplementation("com.google.truth:truth:1.1.2")
    testImplementation("io.kotest:kotest-assertions-core:4.4.1")
    testImplementation("org.amshove.kluent:kluent:1.65")
    testImplementation("io.strikt:strikt-core:0.29.0")
    testImplementation("ch.tutteli.atrium:atrium-fluent-en_GB:0.15.0")
    testImplementation("com.natpryce:hamkrest:1.8.0.1")
    testImplementation("com.winterbe:expekt:0.2.0")
    testImplementation("com.willowtreeapps.assertk:assertk-jvm:0.23.1")

    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.4.2")
    testImplementation("app.cash.turbine:turbine:0.4.0")
}

tasks.test {
    useJUnit()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClassName = "MainKt"
}