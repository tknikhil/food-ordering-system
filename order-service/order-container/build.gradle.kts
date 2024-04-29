plugins {
    kotlin("jvm")
}

group = "org.food.ordering.system"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":order-service:order-domain:order-application-service"))
    implementation(project(":order-service:order-domain:order-domain-core"))
    implementation(project(":order-service:order-dataaccess"))
    implementation(project(":order-service:order-application"))
    implementation(project(":order-service:order-messaging"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}