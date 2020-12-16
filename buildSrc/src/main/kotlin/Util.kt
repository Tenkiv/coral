import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.extra
import java.io.File
import java.io.FileInputStream
import java.net.URI
import java.util.*

/**
 * Check if the project is release based on version name. If it has "SNAPSHOT", it is not a release version.
 */
val Project.isRelease: Boolean
    get() = !version.toString().contains("dev")

/**
 * Creates a properties object from local.properties file.
 */
fun Project.createPropertiesFromLocal(): Properties {
    val properties = Properties()
    val propertiesFile = File(rootDir, "local.properties")

    if (propertiesFile.canRead()) {
        properties.load(FileInputStream(propertiesFile))
    }
    return properties
}

/**
 * Sets Maven OpenPGP signing properties from properties file.
 *
 * These properties **must** be set in every project in order to correctly sign a Maven publication:
 *
 * `signing.keyId` : Public key ID (last 8 symbols). Use `gpg -K` to get it.
 *
 * `signing.password` : Passphrase used to protect the key.
 *
 * `signing.secretKeyRingFile` : The absolute path to the secret key ring file containing your private key.
 *
 * @see <a href="https://docs.gradle.org/current/userguide/signing_plugin.html#sec:signatory_credentials">Signatory Credentials</a>
 */
fun Project.setSigningExtrasFromProperties(properties: Properties) {
    project.apply {
        extra["signing.keyId"] = properties.getProperty("SIGNING_KEYID")
        extra["signing.secretKeyRingFile"] = properties.getProperty("SIGNING_SECRETKEYRINGFILE")
        extra["signing.password"] = properties.getProperty("SIGNING_KEYPASSWORD")
    }
}

/**
 * Sets up POM for Tenkiv specific projects.
 */
fun MavenPublication.configureMavenPom(project: Project) {
    version = project.version.toString()

    pom {
        name.set(project.name)
        description.set(Info.pomDescription)
        url.set(Info.projectUrl)

        licenses {
            license {
                name.set(Info.pomLicense)
                url.set(Info.pomLicenseUrl)
            }
        }
        developers {
            developer {
                email.set(Info.projectDevEmail)
            }
        }
        organization {
            name.set(Info.pomOrg)
        }
        scm {
            connection.set(Info.projectCloneUrl)
            url.set(Info.projectUrl)
        }
    }
}

/**
 * Sets Maven repositories for Tenkiv organization projects.
 */
fun PublishingExtension.setMavenRepositories() {
    repositories {
        maven {
            url = URI("https://maven.pkg.jetbrains.space/tenkiv/p/coral/coral-maven-repo")

            credentials {
                username = System.getenv("JB_SPACE_CLIENT_ID")
                password = System.getenv("JB_SPACE_CLIENT_SECRET")
            }
        }
    }
}