plugins {
	java
	id("org.springframework.boot") version "4.0.3"
	id("io.spring.dependency-management") version "1.1.7"
	jacoco
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
description = "Software Processes Project; Manuel, Büsra, Denise"

java {
	sourceCompatibility = JavaVersion.VERSION_21
	targetCompatibility = JavaVersion.VERSION_21
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-webmvc")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	testImplementation("org.springframework.boot:spring-boot-starter-webmvc-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

// run with .gradlew exportDependencies
tasks.register("exportDependencies") {
	group = "reporting"
	description = "Exports project dependencies to a file"

	doLast {
		val output = file("build/dependencies.txt")
		output.writeText(configurations.runtimeClasspath.get()
			.files.joinToString("\n"))
	}
}

// run with .gradlew projectInfo
tasks.register("projectInfo") {
	group = "help"
	description = "Displays project information"

	doLast {
		println("Project: ${project.name}")
		println("Version: ${project.version}")
		println("Java version: ${System.getProperty("java.version")}")
	}
}

// run with .gradlew printVersion
tasks.register("printVersion") {
	doLast {
		println("Project version: ${project.version}")
	}
}

// JaCoCo
tasks.test {
	finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
	dependsOn(tasks.test)
}

jacoco {
	toolVersion = "0.8.14"
	reportsDirectory = layout.buildDirectory.dir("customJacocoReportDir")
}

tasks.jacocoTestReport {
	reports {
		xml.required = true
		xml.outputLocation = layout.buildDirectory.file("customJacocoReportDir/test/jacocoTestReport.xml")
		html.required = true
	}
}
