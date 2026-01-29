plugins {
    java
    idea
    war
    id("org.springframework.boot") version "3.5.7"
    id("io.spring.dependency-management") version "1.1.5"
    id("org.graalvm.buildtools.native") version "0.10.2"
}

group = "pl.akmf.ksef"
version = "3.0.13"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
    create("integrationTestImplementation") {
        extendsFrom(configurations["testImplementation"], configurations["annotationProcessor"])
    }
    create("integrationTestRuntimeOnly") {
        extendsFrom(configurations["testRuntimeOnly"], configurations["annotationProcessor"])
    }
}

repositories {
    mavenCentral()
}

val integrationTestImplementation by configurations
val jakartaVersion = "4.0.4"
val jakartaValidationApiVersion = "3.0.2"
val jakartaAnnotationApiVersion = "3.0.0"
val commonsCollectionsVersion = "4.5.0"
val commonsLangsVersion = "3.18.0"
val jsr310Version = "2.17.1"
val wiremockStandaloneVersion = "3.9.1"
val testcontainersVersion = "1.21.3"
val awaitilityVersion = "4.2.0"
val googleZxing = "3.5.3"
val reflectionsVersion = "0.10.2"

dependencies {
    implementation(project(":ksef-client"))

    implementation("jakarta.xml.bind:jakarta.xml.bind-api:$jakartaVersion")
    // Validation
    implementation("jakarta.validation:jakarta.validation-api:$jakartaValidationApiVersion")
    implementation("jakarta.annotation:jakarta.annotation-api:$jakartaAnnotationApiVersion")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:$jsr310Version")

    //
    implementation("org.apache.commons:commons-lang3:$commonsLangsVersion")
    implementation("org.apache.commons:commons-collections4:$commonsCollectionsVersion")

    //qr code
    implementation("com.google.zxing:core:$googleZxing")
    implementation("com.google.zxing:javase:$googleZxing")

    implementation("org.springframework.retry:spring-retry")
    implementation("org.springframework.boot:spring-boot-starter-web")
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.testcontainers:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    integrationTestImplementation("org.springframework.boot:spring-boot-testcontainers:") {
        exclude("org.testcontainers:testcontainers-shaded-jackson")
    }
    integrationTestImplementation("org.testcontainers:junit-jupiter:${testcontainersVersion}")
    integrationTestImplementation("org.testcontainers:testcontainers:${testcontainersVersion}")
    integrationTestImplementation("org.wiremock:wiremock-standalone:${wiremockStandaloneVersion}")
    implementation("org.awaitility:awaitility:${awaitilityVersion}")

    testImplementation("org.reflections:reflections:${reflectionsVersion}")
}

sourceSets {
    create("integrationTest") {
        java.setSrcDirs(listOf("src/integrationTest/java"))
        resources.setSrcDirs(listOf("src/integrationTest/resources"))
        compileClasspath += sourceSets["main"].output
        runtimeClasspath += sourceSets["main"].output
    }
}

tasks.register<Test>("unitTest") {
    description = "Runs unit tests."
    group = "Verification"
    useJUnitPlatform()
}

tasks.register<Test>("integrationTest") {
    description = "Runs integration tests."
    group = "Verification"
    testClassesDirs = sourceSets["integrationTest"].output.classesDirs
    classpath = sourceSets["integrationTest"].runtimeClasspath
    useJUnitPlatform()
    testLogging {
        events("failed")
        setExceptionFormat("full")
    }
}

tasks.named("check") {
    dependsOn(tasks.named("integrationTest"), tasks.named("unitTest"))
}

tasks.withType<Test> {
    useJUnitPlatform()
}
