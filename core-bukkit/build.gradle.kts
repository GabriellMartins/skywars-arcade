plugins {
    id("java")
}

group = "br.com.skywars.core.bukkit"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {

    compileOnly("com.google.code.gson:gson:2.10.1")
    compileOnly(files("C:/Users/Anderson/Desktop/Prisma Server/lobby1/carbonspigot.jar"))
    implementation("org.jetbrains:annotations:24.1.0")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    compileOnly(project(":core"))
}

tasks.test {
    useJUnitPlatform()
}