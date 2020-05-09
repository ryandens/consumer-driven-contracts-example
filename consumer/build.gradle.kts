plugins {
    application
}

application {
    mainClassName = "com.github.ryandens.consumer.Main"
}

dependencies {
    // AutoValue for reducing boilerplate in creating immutable value objects
    val autoValueVersion = "1.7"
    compileOnly("com.google.auto.value", "auto-value-annotations", autoValueVersion)
    annotationProcessor("com.google.auto.value", "auto-value", autoValueVersion)
    // jackson for object serialization/deserialization
    implementation("com.fasterxml.jackson.core", "jackson-databind", "2.11.0")
    testImplementation("au.com.dius", "pact-jvm-consumer-junit5", "4.0.10")
}
