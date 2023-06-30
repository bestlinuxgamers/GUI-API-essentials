import org.ajoberstar.reckon.core.Scope
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.8.22"
    id("org.ajoberstar.reckon") version "0.18.0"
    id("org.jetbrains.dokka") version "1.8.20"
}

group = "net.bestlinuxgamers.gui-api"

reckon {
    setDefaultInferredScope(Scope.PATCH)
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
    compileOnly("net.bestlinuxgamers.guiapi:gui-api:0.3.4")
    //Spigot
    compileOnly("org.spigotmc:spigot-api:$spigotVersion")
    //tests
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

val targetProjectCompatibility = JavaVersion.VERSION_1_8.toString()

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = targetProjectCompatibility
}

tasks.withType<JavaCompile> {
    sourceCompatibility = JavaVersion.current().toString()
    targetCompatibility = targetProjectCompatibility
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
