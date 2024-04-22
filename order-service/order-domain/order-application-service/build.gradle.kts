plugins {
    id("java-library")
    kotlin("jvm") version "1.9.22"
}
group = "org.food.ordering.system"
version = "1.0-SNAPSHOT"
repositories {
    mavenCentral()
}
dependencies {
//    implementation(project(":order-service:order-domain:order-domain-core"))
    implementation(project(":order-service:order-domain:order-domain-core"))
}

