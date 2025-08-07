plugins {
    java
    idea
    war
    id("org.springframework.boot") version "3.3.0"
//    id("io.spring.dependency-management") version "1.1.5"
    id("org.graalvm.buildtools.native") version "0.10.2"
}

group = "pl.akmf.ksef"
version = "2.0.1"

val springBootVersion = "3.3.0"
val lombokVersion = "1.18.32"
val commonsLang3Version = "3.14.0"
val micrometerVersion = "1.12.5"

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
val jakartaVersion = "4.0.2"
val jakartaValidationApiVersion = "3.0.2"
val jakartaAnnotationApiVersion = "3.0.0"
val commonsCollectionsVersion = "4.5.0"
val santuarioXmlsecVersion = "3.0.4"
val httpcomponentsHttpClientVersion = "4.3.6"
val bouncycastleBcpkixJdk18onVersion = 1.76
val jsr310Version = "2.17.1"
val jsxbVarsion = "4.0.5"
val wiremockStandaloneVersion = "3.9.1"
val testcontainersVersion = "1.20.0"
val junitJupiterVersion = "5.10.3"
val awaitilityVersion = "4.2.0"
val googleZxingCodeVersion = "3.5.3"
val googleZxingJavaseVersion = "3.5.3"
val springRetry = "2.0.3"

val xjc by configurations.creating

dependencies {
    implementation(project(":ksef-client"))

    implementation("jakarta.xml.bind:jakarta.xml.bind-api:$jakartaVersion")
    // Validation
    implementation("jakarta.validation:jakarta.validation-api:$jakartaValidationApiVersion")
    implementation("jakarta.annotation:jakarta.annotation-api:$jakartaAnnotationApiVersion")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:$jsr310Version")

    //
    implementation("org.apache.commons:commons-lang3:$commonsLang3Version")
    implementation("org.apache.commons:commons-collections4:$commonsCollectionsVersion")
    implementation("org.apache.httpcomponents:httpclient:$httpcomponentsHttpClientVersion")
    implementation("org.apache.santuario:xmlsec:$santuarioXmlsecVersion")
    implementation("org.bouncycastle:bcpkix-jdk18on:$bouncycastleBcpkixJdk18onVersion")

    //qr code
    implementation("com.google.zxing:core:$googleZxingCodeVersion")
    implementation("com.google.zxing:javase:$googleZxingJavaseVersion")

    implementation("org.springframework.retry:spring-retry")
    implementation("org.springframework.boot:spring-boot-starter-web")
    compileOnly("org.projectlombok:lombok")
    runtimeOnly("io.micrometer:micrometer-registry-prometheus")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.testcontainers:junit-jupiter")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    integrationTestImplementation("org.springframework.boot:spring-boot-testcontainers:$springBootVersion") {
        exclude("org.testcontainers:testcontainers-shaded-jackson")
    }
    integrationTestImplementation("org.testcontainers:junit-jupiter:${testcontainersVersion}")
    integrationTestImplementation("org.testcontainers:testcontainers:${testcontainersVersion}")
    integrationTestImplementation("org.junit.jupiter:junit-jupiter-api:${junitJupiterVersion}")
    integrationTestImplementation("org.wiremock:wiremock-standalone:${wiremockStandaloneVersion}")
    implementation("org.awaitility:awaitility:${awaitilityVersion}")}


sourceSets {
    create("integrationTest") {
        java.setSrcDirs(listOf("src/integrationTest/java"))
        resources.setSrcDirs(listOf("src/integrationTest/resources"))
        compileClasspath += sourceSets["main"].output
        runtimeClasspath += sourceSets["main"].output
    }
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
    dependsOn(tasks.named("integrationTest"))
}

tasks.withType<Test> {
    useJUnitPlatform()
}
