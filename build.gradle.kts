

group = "dev.httpmarco"
version = "1.0.0"

subprojects {

    group = "dev.httpmarco.polocloud"
    version = rootProject.version

    repositories {
        mavenCentral()
        maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
    }
}