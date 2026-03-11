plugins {
	java
	id("org.springframework.boot") version "4.0.3"
	id("io.spring.dependency-management") version "1.1.7"
	id("de.undercouch.download") version "5.4.0"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
description = "Software Processes Project; Manuel, Büsra, Denise"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
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

// Define tool directories
val toolsDir = layout.buildDirectory.dir("tools")
val pandocDir = toolsDir.map { it.dir("pandoc") }
val wkhtmltopdfDir = toolsDir.map { it.dir("wkhtmltopdf") }

// Download Pandoc
tasks.register<de.undercouch.gradle.tasks.download.Download>("downloadPandoc") {
	description = "Download Pandoc for PDF generation"
	group = "build setup"
	
	val osName = System.getProperty("os.name").lowercase()
	val isWindows = osName.contains("win")
	val isMac = osName.contains("mac")
	
	val pandocVersion = "3.1.11.1"
	val downloadUrl = when {
		isWindows -> "https://github.com/jgm/pandoc/releases/download/$pandocVersion/pandoc-$pandocVersion-windows-x86_64.zip"
		isMac -> "https://github.com/jgm/pandoc/releases/download/$pandocVersion/pandoc-$pandocVersion-macOS.zip"
		else -> "https://github.com/jgm/pandoc/releases/download/$pandocVersion/pandoc-$pandocVersion-linux-amd64.tar.gz"
	}
	
	src = downloadUrl
	dest = file("${pandocDir.get()}/pandoc-download")
	overwrite = false
	onlyIfNew = true
}

// Download wkhtmltopdf (lightweight PDF engine, no TeX required)
tasks.register<de.undercouch.gradle.tasks.download.Download>("downloadWkhtmltopdf") {
	description = "Download wkhtmltopdf as PDF rendering engine"
	group = "build setup"
	
	val osName = System.getProperty("os.name").lowercase()
	val isWindows = osName.contains("win")
	
	val downloadUrl = if (isWindows) {
		"https://github.com/wkhtmltopdf/packaging/releases/download/0.12.6-1/wkhtmltox-0.12.6-1.msvc2015-win64.exe"
	} else {
		// For Linux/Mac, can be installed via system package manager
		return@register
	}
	
	src = downloadUrl
	dest = file("${wkhtmltopdfDir.get()}/wkhtmltopdf.exe")
	overwrite = false
	onlyIfNew = true
}

// Extract Pandoc
tasks.register("extractPandoc") {
	dependsOn("downloadPandoc")
	description = "Extract Pandoc"
	group = "build setup"
	
	doLast {
		val pandocDownloadDir = file("${pandocDir.get()}/pandoc-download")
		val osName = System.getProperty("os.name").lowercase()
		val isMac = osName.contains("mac")
		val isLinux = osName.contains("linux")
		
		when {
			System.getProperty("os.name").lowercase().contains("win") -> {
				copy {
					from(zipTree(pandocDownloadDir.listFiles()?.find { it.name.endsWith(".zip") }))
					into(pandocDir)
				}
			}
			isMac || isLinux -> {
				exec {
					commandLine("tar", "-xzf", pandocDownloadDir.listFiles()?.find { it.name.endsWith(".tar.gz") }?.absolutePath, "-C", pandocDir.get().asFile.absolutePath)
				}
			}
		}
	}
}

// Task to prepare PDF dependencies
tasks.register("preparePdfTools") {
	description = "Prepare Pandoc and wkhtmltopdf for PDF generation"
	group = "build setup"
	dependsOn("extractPandoc")
	
	val osName = System.getProperty("os.name").lowercase()
	if (osName.contains("win")) {
		dependsOn("downloadWkhtmltopdf")
	}
}

// Task to generate PDF from README.md using Pandoc
tasks.register("generatePdf") {
	description = "Generate PDF from README.md using Pandoc"
	group = "documentation"
	dependsOn("preparePdfTools")
	
	doLast {
		val isWindows = System.getProperty("os.name").lowercase().contains("win")
		val scriptPath = if (isWindows) {
			"${rootDir}\\generate-pdf.ps1"
		} else {
			"${rootDir}/generate-pdf.sh"
		}
		
		// Set environment variables for tool locations
		val env = mutableMapOf<String, String>()
		env["PANDOC_DIR"] = pandocDir.get().asFile.absolutePath
		env["WKHTMLTOPDF_DIR"] = wkhtmltopdfDir.get().asFile.absolutePath
		
		val processBuilder = if (isWindows) {
			ProcessBuilder("powershell", "-ExecutionPolicy", "Bypass", "-File", scriptPath)
		} else {
			ProcessBuilder("bash", scriptPath)
		}
		
		processBuilder.directory(rootDir)
		processBuilder.environment().putAll(env)
		val process = processBuilder.start()
		var exitCode = process.waitFor()
		
		if (exitCode != 0) {
			throw GradleException("PDF generation failed with exit code $exitCode")
		}
	}
}

// Make build task depend on generatePdf
tasks.named("build") {
	dependsOn("generatePdf")
}
