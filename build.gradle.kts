import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.21"
    id("org.ajoberstar.reckon") version "0.16.1"
    id("org.jetbrains.dokka") version "1.7.20"
}

group = "net.bestlinuxgamers.gui-api"

reckon {
    stages("beta", "rc", "final")

    setScopeCalc(calcScopeFromProp().or(calcScopeFromCommitMessages()))
    setStageCalc(calcStageFromProp())
}

repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/") //Spigot
    maven("https://maven.pkg.github.com/bestlinuxgamers/GuiApi") {
        name = "github"
        credentials(PasswordCredentials::class)
    }
}

//dependency version vars
val spigotVersion: String by project

dependencies {
    implementation("net.bestlinuxgamers.guiapi:gui-api:0.3.3")
    //Spigot
    compileOnly("org.spigotmc:spigot-api:$spigotVersion")
    //tests
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

val targetCompatibility = JavaVersion.VERSION_1_8.toString()

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = targetCompatibility
}

tasks.withType<JavaCompile> {
    sourceCompatibility = JavaVersion.current().toString()
    targetCompatibility = targetCompatibility
}

//custom tasks

tasks.register<org.gradle.jvm.tasks.Jar>("javadocJar") {
    group = "documentation"
    archiveClassifier.set("javadoc")
    from(tasks.withType<org.jetbrains.dokka.gradle.DokkaTask>()["dokkaHtml"].outputDirectory)
}

tasks.register<org.gradle.jvm.tasks.Jar>("sourcesJar") {
    group = "documentation"
    archiveClassifier.set("sources")
    from(project.sourceSets["main"].allSource)
}
