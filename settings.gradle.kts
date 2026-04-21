rootProject.name = "ai-bot-helper"

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        maven("https://repo.spring.io/milestone")
        maven("https://repo.spring.io/snapshot")
    }

    versionCatalogs {
        create("libs") {
            version("spring-ai", "1.0.0-M4")

            library("spring-ai-openai", "org.springframework.ai", "spring-ai-openai-spring-boot-starter").versionRef("spring-ai")
            library("spring-ai-ollama", "org.springframework.ai", "spring-ai-ollama-spring-boot-starter").versionRef("spring-ai")
            library("spring-ai-pgvector", "org.springframework.ai", "spring-ai-pgvector-store-spring-boot-starter").versionRef("spring-ai")
        }
    }
}