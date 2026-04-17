plugins {
    java
    id("org.springframework.boot") version "3.2.5"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "com.legendbot"
version = "1.0.0"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
    maven("https://repo.spring.io/milestone")
}

dependencies {

    // Spring Boot
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    // Spring AI
    implementation(libs.spring.ai.openai)
    implementation(libs.spring.ai.pgvector)
    implementation("org.springframework.ai:spring-ai-pgvector-store")

    // Database
    runtimeOnly(libs.postgresql)

    // Utils
    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)
    implementation(libs.jackson.databind)

    // Logging
    implementation(libs.logback.encoder)

    // Testing
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<Test> {
    useJUnitPlatform()
}