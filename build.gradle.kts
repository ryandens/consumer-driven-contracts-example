plugins {
    java
    id("com.diffplug.gradle.spotless") version "3.28.1"
}

allprojects {
    apply(plugin = "com.diffplug.gradle.spotless")
    spotless {
        java {
            removeUnusedImports()
            googleJavaFormat()
        }
        kotlinGradle {
            ktlint()
        }
    }
}

subprojects {
    apply(plugin = "java")
    group = "com.github.ryandens"
    version = "1.0"
    repositories {
        mavenCentral()
    }

    configure<JavaPluginConvention> {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    dependencies {
        val junitVersion = "5.6.2"
        testImplementation("org.junit.jupiter", "junit-jupiter-api", junitVersion)
        testImplementation("org.junit.jupiter", "junit-jupiter-params", junitVersion)
        testRuntimeOnly("org.junit.jupiter", "junit-jupiter-engine", junitVersion)
    }
}
