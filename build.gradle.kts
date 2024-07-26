plugins {
    id("java")
}

group = "br.com.skywars"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()

    maven {
        url = uri("https://libraries.minecraft.net/")
    }
}

dependencies {
    annotationProcessor("org.projectlombok:lombok:1.18.32")

    compileOnly("org.projectlombok:lombok:1.18.32")
    compileOnly("com.google.code.gson:gson:2.10.1")
    compileOnly("com.mojang:authlib:1.5.25")
    compileOnly("mysql:mysql-connector-java:8.0.33")

    implementation("redis.clients:jedis:5.1.2")
    implementation("org.mongodb:mongo-java-driver:3.4.1")
    implementation("org.jetbrains:annotations:24.1.0")
}



tasks.test {
    useJUnitPlatform()
}