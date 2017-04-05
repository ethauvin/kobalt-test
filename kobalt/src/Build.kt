import com.beust.kobalt.*
import com.beust.kobalt.plugin.apt.*
import com.beust.kobalt.plugin.packaging.*
import com.beust.kobalt.plugin.publish.*
import com.beust.kobalt.plugin.application.*
import com.beust.kobalt.plugin.java.*
import org.apache.maven.model.*

val bs = buildScript {
    repos()
}

val processorJar = file("K:\\java\\semver\\deploy\\semver-0.9.7.jar")


val p = project {

    name = "kobalt-test"
    group = "net.thauvin.erik"
    artifactId = name
    version = "0.1"

    pom = Model().apply {
        licenses = listOf(License().apply {
            name = "BSD 3-Clause"
            url = "https://opensource.org/licenses/BSD-3-Clause"
        })
        scm = Scm().apply {
            url = "https://github.com/ethauvin/kobalt-test"
            connection = "https://github.com/ethauvin/kobalt-test.git"
            developerConnection = "git@github.com:ethauvin/kobalt-test.git"
        }
        developers = listOf(Developer().apply {
            id = "ethauvin"
            name = "Erik C. Thauvin"
            email = "erik@thauvin.net"
        })
    }

    sourceDirectories {
        path("src/main/java")
    }

    sourceDirectoriesTest {
        path("src/test/java")
    }

    dependencies {
       apt(processorJar)
       compile("org.apache.velocity:velocity:1.7", processorJar)
    }

    dependenciesTest {
        compile("org.testng:testng:6.11")

    }

    assemble {
        jar {
        }
    }

    install {
        libDir = "deploy"
        collect(compileDependencies).map { include(it) }
    }

    application {
        mainClass = "com.example.Main"
    }

    autoGitTag {
        enabled = true
        message = "Version $version"
        annotated = true
    }

    bintray {
        publish = false
    }
}