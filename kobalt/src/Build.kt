import com.beust.kobalt.*
import com.beust.kobalt.plugin.application.application
import com.beust.kobalt.plugin.apt.apt
import com.beust.kobalt.plugin.packaging.assemble
import com.beust.kobalt.plugin.packaging.install
import com.beust.kobalt.plugin.publish.autoGitTag
import com.beust.kobalt.plugin.publish.bintray
import org.apache.maven.model.*

val bs = buildScript {
    repos()
}

val processorJar = "net.thauvin.erik:semver:0.9.6-beta"

val mainClassName = "com.example.Main"
val lib = "lib"

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

    dependencies {
        apt(processorJar)
        compile("org.twitter4j:twitter4j-core:4.0.6")
        compileOnly(processorJar)
    }

    dependenciesTest {
        compile("org.testng:testng:6.11")

    }

    assemble {
        jar {
            manifest {
                attributes("Main-Class", mainClassName)
                attributes("Class-Path",
                        collect(compileDependencies)
                                .map { it.file.name }
                                .joinToString(" ./$lib/", prefix = ". ./$lib/"))
            }
        }
    }

    application {
        mainClass = "com.example.Main"
    }

    install {
        target = "deploy"
        include(from("kobaltBuild/libs"), to(target), glob("**/*"))
        collect(compileDependencies).forEach {
            copy(from(it.file.absolutePath), to("$target/$lib"))
        }
    }

    apt {
        outputDir = "src/generated/java/"
    }

    application {
        mainClass = mainClassName
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