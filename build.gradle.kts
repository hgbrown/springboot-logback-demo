import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar
import java.text.SimpleDateFormat
import java.util.Date

plugins {
    id("org.springframework.boot") version "3.0.4"
    id("io.spring.dependency-management") version "1.1.0"
    kotlin("jvm") version "1.7.22"
    kotlin("plugin.spring") version "1.7.22"
}

group = "za.co.vine"
version = "1.0.0"
java.sourceCompatibility = JavaVersion.VERSION_17

springBoot.buildInfo()

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<BootJar> {
    archiveFileName.set("springboot-logback-demo.jar")

    manifest {
        attributes(
            "Built-By" to System.getProperty("user.name"),
            "Created-By" to System.getProperty("java.version"),
            "Vendor" to "Grapevine Interactive Pty(Ltd)",
            "Component-Release" to project.version,
            "Jar-Build-Date" to SimpleDateFormat("yyyyMMdd").format(Date()),
            "Gradle-Version" to "Gradle ${GradleVersion.current().version}"
        )
    }
}
