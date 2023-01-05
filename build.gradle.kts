plugins {
    id("java")
    id("maven-publish")
}

group = "com.pokeninjas"
version = "3.0.3"

repositories {
    mavenCentral()
    maven ("https://repo.lightdream.dev/")
}

configurations.all {
    resolutionStrategy.cacheDynamicVersionsFor(10, "seconds")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    // Lombok
    compileOnly("org.projectlombok:lombok:1.18.24")
    annotationProcessor("org.projectlombok:lombok:1.18.24")

}

configurations.all {
    resolutionStrategy.cacheDynamicVersionsFor(10, "seconds")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType<Jar> {
    archiveFileName.set("${rootProject.name}.jar")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
    repositories {
        var gitlabURL = project.findProperty("gitlab.url")
        var gitlabHeaderName = project.findProperty("gitlab.auth.header.name")
        var gitlabHeaderValue = project.findProperty("gitlab.auth.header.value")

        if (gitlabURL == null) {
            gitlabURL = ""
        }
        if (gitlabHeaderName == null) {
            gitlabHeaderName = ""
        }
        if (gitlabHeaderValue == null) {
            gitlabHeaderValue = ""
        }

        maven(url = gitlabURL as String) {
            credentials(HttpHeaderCredentials::class) {
                name = gitlabHeaderName as String
                value = gitlabHeaderValue as String
            }
            authentication {
                create<HttpHeaderAuthentication>("header")
            }
        }
    }
}
