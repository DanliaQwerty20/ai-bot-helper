rootProject.name = "ai-bot-helper"

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        maven("https://repo.spring.io/milestone")
    }

    versionCatalogs {
        create("libs") {
            // Logging
            library("logback-encoder", "net.logstash.logback:logstash-logback-encoder:7.4")
            library("swagger-annotations", "io.swagger.core.v3:swagger-annotations:2.2.38")

            // Utils
            library("lombok", "org.projectlombok:lombok:1.18.30")
            library("jackson-databind", "com.fasterxml.jackson.core:jackson-databind:2.16.0")

            // Spring AI
            library("spring-ai-openai", "org.springframework.ai:spring-ai-starter-model-openai:1.1.0")
            library("spring-ai-chroma", "org.springframework.ai:spring-ai-starter-vector-store-chroma:1.1.0")
            library("spring-ai-pgvector", "org.springframework.ai:spring-ai-starter-vector-store-pgvector:1.1.0")

            // Telegram
            library("telegrambots", "org.telegram:telegrambots:6.8.0")

            // Database
            library("postgresql", "org.postgresql:postgresql:42.7.0")
        }
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version("0.8.0")
}