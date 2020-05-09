plugins {
    application
}

application {
    mainClassName = "com.github.ryandens.provider.Main"
}

dependencies {
    // JAX-RS annotations an interfaces, provided by Jersey at runtime
    compileOnly("jakarta.ws.rs", "jakarta.ws.rs-api", "2.1.6")
    val jerseyVersion = "2.30"
    implementation("org.glassfish.jersey.containers", "jersey-container-jdk-http", jerseyVersion)
    // lets us interop between JAX-RS and jackson beautifully
    implementation("org.glassfish.jersey.media", "jersey-media-json-jackson", jerseyVersion)
    // Eclipse Foundation lightweight dependency injection framework, used by Jersey internally
    runtimeOnly("org.glassfish.jersey.inject", "jersey-hk2", jerseyVersion)
    // JAXB runtime was removed from JRE 11, but is still used by the Jersey JAX-RS implementation
    runtimeOnly("org.glassfish.jaxb", "jaxb-runtime", "2.3.2")
    // AutoValue for reducing boilerplate in creating immutable value objects
    val autoValueVersion = "1.7"
    compileOnly("com.google.auto.value", "auto-value-annotations", autoValueVersion)
    annotationProcessor("com.google.auto.value", "auto-value", autoValueVersion)
    testImplementation("au.com.dius", "pact-jvm-provider-junit5", "4.0.10")
}

tasks.test {
    // depend on the consumer tests running so we always have the latest pact available
    dependsOn(project(":consumer").tasks["test"])
}
