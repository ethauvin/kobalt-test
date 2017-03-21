import com.beust.kobalt.*
import com.beust.kobalt.plugin.packaging.*
import com.beust.kobalt.plugin.publish.*
import com.beust.kobalt.plugin.application.*
import com.beust.kobalt.plugin.java.*
import org.apache.maven.model.*

val bs = buildScript {
    repos()
}


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
//        compile("com.beust:jcommander:1.48")
    }

    dependenciesTest {
        compile("org.testng:testng:6.10")

    }

    assemble {
        jar {
        }
    }

    application {
        mainClass = "com.example.Main"
    }

    autoGitTag {
        enabled = true
        message = "Version $version"
    }

    bintray {
        publish = true
    }
}
