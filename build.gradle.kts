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

plugins {
    kotlin("multiplatform") version Vof.kotlin
    id("org.jetbrains.dokka") version Vof.dokka
    id("maven-publish")
    signing
    jacoco
}

buildscript {
    repositories {
        jcenter()
        gradlePluginPortal()
        mavenCentral()
    }
}

repositories {
    jcenter()
    mavenCentral()
}

val properties = createPropertiesFromLocal()
setSigningExtrasFromProperties(properties)

kotlin {
    explicitApi()

    jvm {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    js()

    sourceSets {
        all {
            languageSettings.useExperimentalAnnotation("kotlin.Experimental")
        }

        val commonMain by getting

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common", Vof.kotlin))
                implementation(kotlin("test-annotations-common", Vof.kotlin))
            }
        }

        val jvmMain by getting

        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test", Vof.kotlin))
                implementation(kotlin("test-junit", Vof.kotlin))
            }
        }

        val jsMain by getting {
            dependencies {
                implementation(kotlin("stdlib-js", Vof.kotlin))
            }
        }

        val jsTest by getting {
            dependencies {
                implementation(kotlin("test-js", Vof.kotlin))
            }
        }


    }

    publishing {
        publications.withType<MavenPublication>().apply {
            val kotlinMultiplatform by getting {
                artifact(tasks.getByName("metadataSourcesJar")) {
                    classifier = "sources"
                }
            }
        }.all {
            configureMavenPom(project)
            signing { if (isRelease) sign(this@all) }
        }

        setMavenRepositories()
    }
}
