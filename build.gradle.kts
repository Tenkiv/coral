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

import org.jetbrains.kotlin.gradle.tasks.*

plugins {
    kotlin("multiplatform") version Vof.kotlinVersion
    jacoco
    `java-library`
    java
    `maven-publish`
}

buildscript {
    repositories {
        jcenter()
        mavenCentral()
    }
}

kotlin {
    jvm("mainJvm") {
        val main by compilations.getting {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }

        mavenPublication {

        }
    }

    sourceSets {
        val commonMain by getting {
            repositories {
                mavenCentral()
                jcenter()
            }

            dependencies {
                implementation(kotlin("stdlib-common"))
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }

        jvm().compilations["main"].defaultSourceSet {
            dependencies {
                dependsOn(commonMain)
                implementation(kotlin("stdlib-jdk8"))
            }
        }

        jvm().compilations["test"].defaultSourceSet {
            apply<JavaLibraryPlugin>()

            repositories {
                mavenCentral()
                jcenter()
            }

            dependencies {
                implementation(kotlin("reflect", Vof.kotlinVersion))
                implementation(kotlin("test", Vof.kotlinVersion))

                implementation("org.spekframework.spek2:spek-dsl-jvm:${Vof.spek}") {
                    exclude(group = "org.jetbrains.kotlin")
                }

                implementation("org.spekframework.spek2:spek-runner-junit5:${Vof.spek}") {
                    exclude(group = "org.jetbrains.kotlin")
                    exclude(group = "org.junit.platform")
                }

                implementation("org.junit.platform:junit-platform-launcher:${Vof.junitLauncher}")
            }

            jacoco {
                toolVersion = Vof.jacocoTool
            }

            tasks {
                val jacocoReport = withType<JacocoReport> {
                    reports {
                        html.isEnabled = true
                        xml.isEnabled = true
                        csv.isEnabled = false
                    }
                }

                withType(Test::class) {
                    outputs.upToDateWhen { false }
                    useJUnitPlatform {
                        includeEngines("spek2")
                    }

                    finalizedBy(jacocoReport)
                }
            }
        }

        all {
            repositories {
                jcenter()
                mavenCentral()
            }
        }
    }
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions.suppressWarnings = true
        kotlinOptions.jvmTarget = "1.8"
    }
}

publishing {
    publications {
        create<MavenPublication>("maven-coral-jvm") {
            val mainJvm by getting {
                groupId = "org.tenkiv.coral"
                artifactId = "coral-jvm"
                version = project.version.toString()

                from(components["java"])

                pom {
                    name.set(project.name)
                    description.set(Info.pomDescription)
                    url.set(System.getenv("CI_PROJECT_URL"))
                    licenses {
                        license {
                            name.set(Info.pomLicense)
                            url.set(Info.pomLicenseUrl)
                        }
                    }
                    organization {
                        name.set(Info.pomOrg)
                    }
                    scm {
                        connection.set(System.getenv("CI_REPOSITORY_URL"))
                        url.set(System.getenv("CI_PROJECT_URL"))
                    }
                }
            }
        }
    }

    repositories {
        maven {
            // change URLs to point to your repos, e.g. http://my.org/repo
            val releasesRepoUrl = uri(Info.sonatypeReleaseRepoUrl)
            val snapshotsRepoUrl = uri(Info.sonatypeSnapshotRepoUrl)
            url = if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl
            credentials {
                username = System.getenv("MAVEN_REPO_USER")
                password = System.getenv("MAVEN_REPO_PASSWORD")
            }
        }
    }
}