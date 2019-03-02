val kotlinVersion = "1.3.21"

plugins {
    kotlin("multiplatform") version "1.3.21"
}

buildscript {
    repositories {
        mavenCentral()
    }
}

kotlin {
    jvm()
    
    jvm {
        val main by compilations.getting {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }

    sourceSets {
        val commonMain by getting {
            repositories {
                mavenCentral()
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

        val jvmMain by getting {
            apply(plugin = "jacoco")

            dependencies {
                dependsOn(commonMain)
                implementation(kotlin("stdlib-jdk8"))
            }
        }

        val jvmTest by getting {
            repositories {
                jcenter()
            }
            
            dependencies {
                implementation(kotlin("reflect", kotlinVersion))
                implementation(kotlin("test", kotlinVersion))
                implementation("org.spekframework.spek2:spek-dsl-jvm:2.0.0") {
                    exclude(group = "org.jetbrains.kotlin")
                }

                implementation("org.spekframework.spek2:spek-runner-junit5:2.0.0") {
                    exclude(group = "org.jetbrains.kotlin")
                    exclude(group = "org.junit.platform")
                }

                implementation("org.junit.platform:junit-platform-launcher:1.4.0") {
                    because("Needed to run tests IDEs that bundle an older version")
                }
            }
            
            
        }
    }
}