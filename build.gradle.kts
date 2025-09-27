plugins {
    java
    id("org.springframework.boot") version "3.5.6"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "back"
version = "0.0.1-SNAPSHOT"
description = "Amang_Web_BE"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    // JPA (Hibernate)
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // Security (로그인/회원가입 JWT 인증 시 필요)
    implementation("org.springframework.boot:spring-boot-starter-security")

    // Validation (회원가입 입력 검증)
    implementation("org.springframework.boot:spring-boot-starter-validation")

    // 웹 (REST API)
    implementation("org.springframework.boot:spring-boot-starter-web")

    // Lombok (코드 단축)
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    // 개발 편의 (자동 리스타트 등)
    developmentOnly("org.springframework.boot:spring-boot-devtools")

    // MySQL 드라이버
    runtimeOnly("com.mysql:mysql-connector-j")

    // 테스트
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // spring doc
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.9")

    // jwt
    implementation("io.jsonwebtoken:jjwt-api:0.12.6")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.6")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.6")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
