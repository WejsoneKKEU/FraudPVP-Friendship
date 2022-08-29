import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("net.minecrell.plugin-yml.bukkit") version "0.5.2"
}

group = "com.eternalcode"
version = "1.0.0"

repositories {
    gradlePluginPortal()
    mavenCentral()
    mavenLocal()

    maven { url = uri("https://papermc.io/repo/repository/maven-public/")}
    maven { url = uri("https://repo.panda-lang.org/releases") }
}

dependencies {
    // spigot api
    compileOnly("org.spigotmc:spigot-api:1.19-R0.1-SNAPSHOT")

    // kyori adventure
    implementation("net.kyori:adventure-platform-bukkit:4.1.2")
    implementation("net.kyori:adventure-text-minimessage:4.11.0")

    // litecommands
    implementation("dev.rollczi.litecommands:bukkit:2.5.0")
    implementation("dev.rollczi.litecommands:core:2.5.0")

    // cdn
    implementation("net.dzikoysk:cdn:1.14.0")

    // bstats
    implementation("org.bstats:bstats-bukkit:3.0.0")

    // triumph gui
    implementation("dev.triumphteam:triumph-gui:3.1.2")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

bukkit {
    main = "com.eternalcode.friends.EternalFriends"
    apiVersion = "1.13"
    name = "EternalFriends"
    prefix = "EternalFriends"
    authors = listOf("igoyek", "Kamicjusz")
    version = "${project.version}"
    description = "EternalFriends is a plugin that allows you to manage your friends list."
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(11))
}

tasks.withType<ShadowJar> {
    archiveFileName.set("EternalFriends v${project.version}.jar")

    exclude(
        "org/intellij/lang/annotations/**",
        "org/jetbrains/annotations/**",
        "org/checkerframework/**",
        "META-INF/**",
        "javax/**",
    )

    mergeServiceFiles()
    minimize()

    val prefix = "com.eternalcode.friends.libs"

    listOf(
            "panda",
            "org.panda_lang",
            "org.bstats",
            "net.dzikoysk",
            "net.kyori",
            "dev.rollczi",
            "dev.triumphteam",
    ).forEach { pack ->
        relocate(pack, "$prefix.$pack")
    }
}