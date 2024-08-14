plugins {
    kotlin("jvm") version "1.9.22"
}
group = "org.food.ordering.system"
version = "1.0-SNAPSHOT"
repositories {
    mavenCentral()
}

    dependencies {
//        force springboot to use this version
        implementation (platform("org.springframework.boot:spring-boot-dependencies:3.2.5"))
//    implementation(project(":order-service:order-domain:order-domain-core"))
    implementation(project(":order-service:order-domain:order-domain-core"))
    implementation(project(":common:common-domain"))
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework:spring-tx")
        implementation("org.projectlombok:lombok:1.18.30")
    }
kotlin {
    jvmToolchain(17)
}
