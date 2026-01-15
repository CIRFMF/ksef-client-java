plugins {
    `java-library`
    `maven-publish`
}


val appVersion = "3.0.10"
val artifactName = "ksef-client"

val githubRepositoryToken = "token"
val githubRepositoryOwner = "CIRFMF"
val githubRepository = "$githubRepositoryOwner/ksef-client-java"

val tagVersion = System.getenv("GITHUB_REF_NAME")
val cleanVersion = tagVersion?.removePrefix("v") ?: appVersion

group = "pl.akmf.ksef-sdk"
version = cleanVersion


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
val bouncycastleVersion = "1.82"
val jsr310Version = "2.17.1"
val junitVersion = "4.4"
val junitEngineVersion = "5.8.2"
val jsxbVersion = "4.0.6"
val jakartaBindApi = "4.0.4"
val jaxbFluentApiVersion = "3.0"
val xjc by configurations.creating
val xadesVersion = "6.3"
val googleZxing = "3.5.3"
val commonsLangsVersion = "3.18.0"

dependencies {
    // Validation
    implementation("eu.europa.ec.joinup.sd-dss:dss-xades:$xadesVersion")
    implementation("eu.europa.ec.joinup.sd-dss:dss-token:$xadesVersion")
    implementation("eu.europa.ec.joinup.sd-dss:dss-utils-apache-commons:$xadesVersion")

    implementation("org.apache.commons:commons-lang3:$commonsLangsVersion")

    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:$jsr310Version")

    testImplementation("junit:junit:$junitVersion")
    testRuntimeOnly("org.junit.vintage:junit-vintage-engine:$junitEngineVersion")

    //xsd
    xjc("org.jvnet.jaxb2_commons:jaxb2-fluent-api:$jaxbFluentApiVersion")
    xjc("com.sun.xml.bind:jaxb-xjc:$jsxbVersion")
    xjc("com.sun.xml.bind:jaxb-impl:$jsxbVersion")
    implementation("jakarta.xml.bind:jakarta.xml.bind-api:$jakartaBindApi")
    implementation("org.glassfish.jaxb:jaxb-runtime:$jsxbVersion")

    //bouncycastle
    implementation("org.bouncycastle:bcpkix-jdk18on:$bouncycastleVersion")
    implementation("org.bouncycastle:bcprov-jdk18on:$bouncycastleVersion")

    //qr code
    implementation("com.google.zxing:core:$googleZxing")
    implementation("com.google.zxing:javase:$googleZxing")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<JavaCompile>().configureEach {
    options.release.set(11)

    options.encoding = "UTF-8"
}


sourceSets["main"].java.srcDir("${buildDir}/generated/src/main/java")


tasks.named("compileJava") {
    dependsOn("generateJaxb")
}

tasks.register<Test>("unitTest") {
    description = "Runs unit tests."
    group = "Verification"
    useJUnitPlatform()
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
                "package" to "pl.akmf.ksef.sdk.client.model.xml",
                "encoding" to "UTF-8"
            ) {
                "arg"("value" to "-Xfluent-api")
                "schema"(
                    "dir" to "src/main/resources/xsd"
                )
            }
        }
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            groupId = group.toString()
            artifactId = artifactName
            version = project.version.toString()

            pom {
                name.set("KSeF Client")
                description.set("A Java library that simplifies integration with the Polish National e-Invoicing System (KSeF)")
                url.set("https://github.com/$githubRepository")

                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://opensource.org/licenses/MIT")
                    }
                }

                developers {
                    developer {
                        id.set(githubRepositoryOwner)
                        name.set("Centrum Informatyki Resortu Finansów")
                        organization.set("Centrum Informatyki Resortu Finansów")
                    }
                }

                scm {
                    connection.set("scm:git:https://github.com/$githubRepository.git")
                    developerConnection.set("scm:git:ssh://github.com/$githubRepository.git")
                    url.set("https://github.com/$githubRepository")
                }

                withXml {
                    asNode().appendNode("properties").apply {
                        appendNode("tags", "KSeF;Poland;e-Invoicing;Client;API")
                    }
                }
            }
        }
    }
    repositories {
        maven {
            name = "github"
            url = uri("https://maven.pkg.github.com/${githubRepository}")
            credentials {
                username = System.getenv("GITHUB_ACTOR") ?: githubRepositoryOwner
                password = System.getenv("GITHUB_TOKEN") ?: githubRepositoryToken
            }
        }
    }
}
