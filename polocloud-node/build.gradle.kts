plugins {
    id("java")
}

dependencies {
    compileOnly(libs.bundles.utils)
    compileOnly(libs.bundles.grpc)
    compileOnly(project(":polocloud-api"))
}

