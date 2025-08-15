plugins {
	id("java")
	id("application")
	id("org.springframework.boot") version "3.3.4"
	id("io.spring.dependency-management") version "1.1.6"
}

application {
	mainClass.set("kdg.programming3.RestaurantApp.RestaurantApplication")
}

group = "kdg.programming3"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

repositories {
	mavenCentral()
}

dependencies {
//	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-jdbc")
	runtimeOnly("com.h2database:h2")
	runtimeOnly("org.postgresql:postgresql")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	implementation("org.springframework.boot:spring-boot-starter-integration")
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.webjars:bootstrap:5.3.2")
	implementation("org.webjars.npm:bootstrap-icons:1.11.3")
	implementation("org.springframework.integration:spring-integration-http")
	implementation("org.springframework.integration:spring-integration-jdbc")
	implementation("org.springframework.integration:spring-integration-jpa")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.integration:spring-integration-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
tasks.getByName("run", JavaExec::class) {
	standardInput = System.`in`
}