plugins {
    kotlin("jvm")
}

group = "org.food.ordering.system"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":common:common-domain"))
    implementation("org.projectlombok:lombok:1.18.30")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}