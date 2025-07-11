plugins {
    `java-library`
}

group = "pl.akmf.ksef-sdk"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

val jacksonDatabindNullableVersion = "0.2.6"
val jsr310Version = "2.15.2"
val junitVersion = "4.4"
val junitEngineVersion = "5.8.2"
val jsxbVarsion = "4.0.5"
val xjc by configurations.creating
val xadesVersion = "6.0"

dependencies {
    // Validation
    implementation("eu.europa.ec.joinup.sd-dss:dss-xades:$xadesVersion")
    implementation("eu.europa.ec.joinup.sd-dss:dss-token:$xadesVersion")
    implementation("eu.europa.ec.joinup.sd-dss:dss-utils-apache-commons:$xadesVersion")
    implementation("org.openapitools:jackson-databind-nullable:$jacksonDatabindNullableVersion")

    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:$jsr310Version")

    testImplementation("junit:junit:$junitVersion")
    testRuntimeOnly("org.junit.vintage:junit-vintage-engine:$junitEngineVersion")

    //xsd
    xjc("org.jvnet.jaxb2_commons:jaxb2-fluent-api:3.0")
    xjc("com.sun.xml.bind:jaxb-xjc:$jsxbVarsion")
    xjc("com.sun.xml.bind:jaxb-impl:$jsxbVarsion")

    //bouncycastle
    implementation("org.bouncycastle:bcpkix-jdk18on:1.76")
    implementation("org.bouncycastle:bcprov-jdk18on:1.76")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<JavaCompile>().configureEach {
    options.release.set(11)
}


sourceSets["main"].java.srcDir("${buildDir}/generated/src/main/java")


tasks.named("compileJava") {
    dependsOn("generateJaxb")
}

tasks.register("generateJaxb") {
    doLast {
        ant.withGroovyBuilder {
            "taskdef"(
                "name" to "xjc",
                "classname" to "com.sun.tools.xjc.XJCTask",
                "classpath" to configurations["xjc"].asPath
            )
            "xjc"(
                "destdir" to "src/main/java",
                "package" to "pl.akmf.ksef.sdk.client.model.xml"
            ) {
                "arg"("value" to "-Xfluent-api")
                "schema"(
                    "dir" to "src/main/resources/xsd"
                )
            }
        }
    }
}





