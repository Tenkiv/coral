/*
 * Copyright 2019 Tenkiv, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 * Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

import org.jetbrains.dokka.gradle.DokkaTask
import org.jetbrains.kotlin.gradle.tasks.*
import java.io.*
import java.util.*

plugins {
    kotlin("multiplatform") version Vof.kotlinVersion
    id("org.jetbrains.dokka") version Vof.dokka
    id("maven-publish")
    signing
    jacoco
}

buildscript {
    repositories {
        jcenter()
        mavenCentral()
    }
}

repositories {
    jcenter()
    mavenCentral()
}

val isRelease = isRelease()
val properties = createPropertiesFromLocal()
setSigningExtrasFromProperties(properties)

kotlin {
    jvm {
        val main by compilations.getting {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
        val test by compilations.getting {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    js()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
                implementation("org.spekframework.spek2:spek-dsl-metadata:${Vof.spek}")
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation(kotlin("stdlib-jdk8"))
            }
        }

        val jvmTest by getting {
            dependencies {
                implementation(kotlin("reflect", Vof.kotlinVersion))
                implementation(kotlin("test", Vof.kotlinVersion))


                implementation("org.spekframework.spek2:spek-dsl-jvm:${Vof.spek}") {
                    exclude("org.jetbrains.kotlin")
                }

                implementation("org.spekframework.spek2:spek-runner-junit5:${Vof.spek}") {
                    exclude("org.jetbrains.kotlin")
                    exclude("org.junit.platform")
                }

                implementation("org.junit.platform:junit-platform-launcher:${Vof.junitPlatform}")
            }

            jacoco {
                toolVersion = Vof.jacocoTool
            }
        }
        val jsMain by getting {
            dependencies {
                implementation(kotlin("stdlib-js"))
            }
        }

        val jsTest by getting {
            dependencies {
                implementation(kotlin("test-js"))
                implementation("org.spekframework.spek2:spek-dsl-js:${Vof.spek}")
            }
        }

        tasks {
            val jacocoReport = withType<JacocoReport> {
                reports {
                    html.isEnabled = true
                    xml.isEnabled = true
                    csv.isEnabled = false
                }
            }

            named<Test>("jvmTest") {
                outputs.upToDateWhen { false }
                useJUnitPlatform {
                    includeEngines("spek2")
                }

                maxHeapSize = "1g"
                finalizedBy(jacocoReport)
            }

            val dokka by getting(DokkaTask::class) {
                outputDirectory = "$buildDir/docs"
                outputFormat = "html"

                multiplatform {
                    val jvm by creating {
                        targets = listOf("Jvm")
                        platform = "jvm"
                    }

                    val js by creating {
                        targets = listOf("Js")
                        platform = "js"
                    }
                }
            }

            register<Jar>("javadocJar") {
                from(getByName("dokka"))
                archiveClassifier.set("javadoc")
            }
        }
    }

    publishing {
        publications.withType<MavenPublication>().apply {
            val jvm by getting {
                artifact(tasks.getByName("javadocJar"))
            }

            val js by getting {
                artifact(tasks.getByName("javadocJar"))
            }
        }.forEach {
            it.configureMavenPom(isRelease, project)
            signing { if (isRelease) sign(it) }
        }

        setMavenRepositories(isRelease, properties)
    }
}
