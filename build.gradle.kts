plugins {
    kotlin("jvm") version "1.9.22"
    id("org.springframework.boot") version "3.2.5"
    id("io.spring.dependency-management") version "1.1.4"

}

group = "org.food.ordering.system"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}
dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    implementation("org.projectlombok:lombok:provided")
    implementation("org.springframework.boot:spring-boot-starter-logging")


}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}


//now all module and sub-module having one version
allprojects {
    group = "org.food.ordering.system"
    version = "1.0-SNAPSHOT"
}