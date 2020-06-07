plugins {
    java
}

group = "io.github.karlatemp"

repositories {
    mavenLocal()
    mavenCentral()
    jcenter()
    // SpigotMC
    maven(url = "https://hub.spigotmc.org/nexus/content/groups/public")
    // Code MC
    maven(url = "https://repo.codemc.io/repository/maven-public/")
}

tasks.withType(Jar::class.java) {
    this.archiveFileName.set("PersistentAPI.jar")
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.15.2-R0.1-SNAPSHOT")
    compileOnly(fileTree("libs").include("*.jar"))
    // https://mvnrepository.com/artifact/com.google.guava/guava
    implementation("com.google.guava:guava:29.0-jre")
    implementation("org.jetbrains:annotations:19.0.0")
    testImplementation("junit", "junit", "4.12")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}