# 합주실 예약 시스템 Spring Boot 백엔드 구현 계획

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Spring Boot + Kotlin 백엔드 API 서버의 기본 세팅과 핵심 4개 도메인(user, vendor, room, reservation) CRUD 구현

**Architecture:** 레이어드 아키텍처 (Controller → Service → Repository → Entity). 도메인 기반 패키지 구조. PostgreSQL + JPA + Testcontainers 기반 통합 테스트.

**Tech Stack:** Spring Boot 4.0.3, Kotlin 2.2.21, Java 21, Spring Data JPA, Hibernate 7, PostgreSQL 15+, Testcontainers, Gradle Kotlin DSL

**Spec:** `docs/superpowers/specs/2026-03-22-spring-boot-backend-design.md`

---

## File Map

### 빌드/설정
- Modify: `build.gradle.kts` — plugin.jpa 추가, 의존성 추가
- Create: `src/main/resources/application.yml` — DB, JPA, Jackson 설정
- Delete: `src/main/resources/application.properties` — yml로 대체

### common 패키지
- Create: `src/main/kotlin/org/flab/ensembleroomreservationproject/common/dto/ApiResponse.kt`
- Create: `src/main/kotlin/org/flab/ensembleroomreservationproject/common/exception/BusinessException.kt`
- Create: `src/main/kotlin/org/flab/ensembleroomreservationproject/common/exception/GlobalExceptionHandler.kt`
- Create: `src/main/kotlin/org/flab/ensembleroomreservationproject/common/config/JpaAuditingConfig.kt`

### user 도메인
- Create: `src/main/kotlin/org/flab/ensembleroomreservationproject/user/entity/User.kt`
- Create: `src/main/kotlin/org/flab/ensembleroomreservationproject/user/entity/UserRole.kt`
- Create: `src/main/kotlin/org/flab/ensembleroomreservationproject/user/repository/UserRepository.kt`
- Create: `src/main/kotlin/org/flab/ensembleroomreservationproject/user/service/UserService.kt`
- Create: `src/main/kotlin/org/flab/ensembleroomreservationproject/user/controller/UserController.kt`
- Create: `src/main/kotlin/org/flab/ensembleroomreservationproject/user/dto/UserResponse.kt`
- Create: `src/main/kotlin/org/flab/ensembleroomreservationproject/user/dto/UserUpdateRequest.kt`
- Create: `src/test/kotlin/org/flab/ensembleroomreservationproject/user/service/UserServiceTest.kt`
- Create: `src/test/kotlin/org/flab/ensembleroomreservationproject/user/controller/UserControllerTest.kt`

### vendor 도메인
- Create: `src/main/kotlin/org/flab/ensembleroomreservationproject/vendor/entity/Vendor.kt`
- Create: `src/main/kotlin/org/flab/ensembleroomreservationproject/vendor/entity/VendorStatus.kt`
- Create: `src/main/kotlin/org/flab/ensembleroomreservationproject/vendor/entity/OperatingHour.kt`
- Create: `src/main/kotlin/org/flab/ensembleroomreservationproject/vendor/repository/VendorRepository.kt`
- Create: `src/main/kotlin/org/flab/ensembleroomreservationproject/vendor/service/VendorService.kt`
- Create: `src/main/kotlin/org/flab/ensembleroomreservationproject/vendor/controller/VendorController.kt`
- Create: `src/main/kotlin/org/flab/ensembleroomreservationproject/vendor/controller/AdminVendorController.kt`
- Create: `src/main/kotlin/org/flab/ensembleroomreservationproject/vendor/dto/VendorCreateRequest.kt`
- Create: `src/main/kotlin/org/flab/ensembleroomreservationproject/vendor/dto/VendorUpdateRequest.kt`
- Create: `src/main/kotlin/org/flab/ensembleroomreservationproject/vendor/dto/VendorResponse.kt`
- Create: `src/test/kotlin/org/flab/ensembleroomreservationproject/vendor/service/VendorServiceTest.kt`
- Create: `src/test/kotlin/org/flab/ensembleroomreservationproject/vendor/controller/VendorControllerTest.kt`

### room 도메인
- Create: `src/main/kotlin/org/flab/ensembleroomreservationproject/room/entity/Room.kt`
- Create: `src/main/kotlin/org/flab/ensembleroomreservationproject/room/entity/Equipment.kt`
- Create: `src/main/kotlin/org/flab/ensembleroomreservationproject/room/entity/TimeSlotOverride.kt`
- Create: `src/main/kotlin/org/flab/ensembleroomreservationproject/room/repository/RoomRepository.kt`
- Create: `src/main/kotlin/org/flab/ensembleroomreservationproject/room/repository/TimeSlotOverrideRepository.kt`
- Create: `src/main/kotlin/org/flab/ensembleroomreservationproject/room/service/RoomService.kt`
- Create: `src/main/kotlin/org/flab/ensembleroomreservationproject/room/controller/RoomController.kt`
- Create: `src/main/kotlin/org/flab/ensembleroomreservationproject/room/dto/RoomCreateRequest.kt`
- Create: `src/main/kotlin/org/flab/ensembleroomreservationproject/room/dto/RoomUpdateRequest.kt`
- Create: `src/main/kotlin/org/flab/ensembleroomreservationproject/room/dto/RoomResponse.kt`
- Create: `src/main/kotlin/org/flab/ensembleroomreservationproject/room/dto/AvailabilityResponse.kt`
- Create: `src/test/kotlin/org/flab/ensembleroomreservationproject/room/service/RoomServiceTest.kt`
- Create: `src/test/kotlin/org/flab/ensembleroomreservationproject/room/controller/RoomControllerTest.kt`

### reservation 도메인
- Create: `src/main/kotlin/org/flab/ensembleroomreservationproject/reservation/entity/Reservation.kt`
- Create: `src/main/kotlin/org/flab/ensembleroomreservationproject/reservation/entity/ReservationStatus.kt`
- Create: `src/main/kotlin/org/flab/ensembleroomreservationproject/reservation/repository/ReservationRepository.kt`
- Create: `src/main/kotlin/org/flab/ensembleroomreservationproject/reservation/service/ReservationService.kt`
- Create: `src/main/kotlin/org/flab/ensembleroomreservationproject/reservation/service/ReservationNumberGenerator.kt`
- Create: `src/main/kotlin/org/flab/ensembleroomreservationproject/reservation/controller/ReservationController.kt`
- Create: `src/main/kotlin/org/flab/ensembleroomreservationproject/reservation/dto/ReservationCreateRequest.kt`
- Create: `src/main/kotlin/org/flab/ensembleroomreservationproject/reservation/dto/ReservationResponse.kt`
- Create: `src/main/kotlin/org/flab/ensembleroomreservationproject/reservation/dto/CancelRequest.kt`
- Create: `src/test/kotlin/org/flab/ensembleroomreservationproject/reservation/service/ReservationServiceTest.kt`
- Create: `src/test/kotlin/org/flab/ensembleroomreservationproject/reservation/service/ReservationNumberGeneratorTest.kt`
- Create: `src/test/kotlin/org/flab/ensembleroomreservationproject/reservation/controller/ReservationControllerTest.kt`

### 테스트 인프라
- Create: `src/test/kotlin/org/flab/ensembleroomreservationproject/support/TestContainersConfig.kt`

---

## Task 1: 빌드 설정 + 의존성 추가

**Files:**
- Modify: `build.gradle.kts`
- Create: `src/main/resources/application.yml`
- Delete: `src/main/resources/application.properties`

- [ ] **Step 1: build.gradle.kts에 plugin.jpa + 의존성 추가**

```kotlin
plugins {
    kotlin("jvm") version "2.2.21"
    kotlin("plugin.spring") version "2.2.21"
    kotlin("plugin.jpa") version "2.2.21"
    id("org.springframework.boot") version "4.0.3"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "org.flab"
version = "0.0.1-SNAPSHOT"
description = "Ensemble Room Reservation Project"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    runtimeOnly("org.postgresql:postgresql")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("org.testcontainers:postgresql")
    testImplementation("org.testcontainers:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict", "-Xannotation-default-target=param-property")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
```

- [ ] **Step 2: application.yml 생성**

```yaml
spring:
  application:
    name: ensemble-room-reservation

  datasource:
    url: jdbc:postgresql://localhost:5432/ensemble
    username: ensemble
    password: ensemble

  jpa:
    hibernate:
      ddl-auto: create-drop
    properties:
      hibernate:
        format_sql: true
    open-in-view: false

  jackson:
    property-naming-strategy: SNAKE_CASE

server:
  port: 8080
```

> Note: MVP 단계에서는 `ddl-auto: create-drop`으로 시작. Flyway는 스키마 안정화 후 추가.

- [ ] **Step 3: application.properties 삭제**

```bash
rm src/main/resources/application.properties
```

- [ ] **Step 4: 빌드 확인**

Run: `./gradlew compileKotlin`
Expected: BUILD SUCCESSFUL

- [ ] **Step 5: 커밋**

```bash
git add build.gradle.kts src/main/resources/
git rm src/main/resources/application.properties
git commit -m "빌드 설정: web, jpa, validation, postgresql, testcontainers 의존성 추가"
```

---

## Task 2: 테스트 인프라 + 공통 모듈

**Files:**
- Create: `src/test/kotlin/org/flab/ensembleroomreservationproject/support/TestContainersConfig.kt`
- Create: `src/main/kotlin/org/flab/ensembleroomreservationproject/common/config/JpaAuditingConfig.kt`
- Create: `src/main/kotlin/org/flab/ensembleroomreservationproject/common/dto/ApiResponse.kt`
- Create: `src/main/kotlin/org/flab/ensembleroomreservationproject/common/exception/BusinessException.kt`
- Create: `src/main/kotlin/org/flab/ensembleroomreservationproject/common/exception/GlobalExceptionHandler.kt`

- [ ] **Step 1: Testcontainers 설정**

```kotlin
// src/test/kotlin/.../support/TestContainersConfig.kt
package org.flab.ensembleroomreservationproject.support

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.context.annotation.Bean
import org.testcontainers.containers.PostgreSQLContainer

@TestConfiguration(proxyBeanMethods = false)
class TestContainersConfig {

    @Bean
    @ServiceConnection
    fun postgresContainer(): PostgreSQLContainer<*> =
        PostgreSQLContainer("postgres:15-alpine")
            .withDatabaseName("ensemble_test")
            .withUsername("test")
            .withPassword("test")
}
```

> Note: Testcontainers의 `@ServiceConnection`이 datasource를 자동으로 오버라이드하므로 별도 test profile 불필요.

- [ ] **Step 2: JPA Auditing 설정**

```kotlin
// src/main/kotlin/.../common/config/JpaAuditingConfig.kt
package org.flab.ensembleroomreservationproject.common.config

import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@Configuration
@EnableJpaAuditing
class JpaAuditingConfig
```

- [ ] **Step 3: 공통 응답 DTO**

```kotlin
// src/main/kotlin/.../common/dto/ApiResponse.kt
package org.flab.ensembleroomreservationproject.common.dto

data class ApiResponse<T>(
    val success: Boolean,
    val data: T? = null,
    val error: String? = null
) {
    companion object {
        fun <T> ok(data: T): ApiResponse<T> = ApiResponse(success = true, data = data)
        fun error(message: String): ApiResponse<Nothing> = ApiResponse(success = false, error = message)
    }
}
```

- [ ] **Step 4: 예외 처리**

```kotlin
// src/main/kotlin/.../common/exception/BusinessException.kt
package org.flab.ensembleroomreservationproject.common.exception

import org.springframework.http.HttpStatus

open class BusinessException(
    val status: HttpStatus,
    override val message: String
) : RuntimeException(message)

class NotFoundException(message: String) : BusinessException(HttpStatus.NOT_FOUND, message)
class ConflictException(message: String) : BusinessException(HttpStatus.CONFLICT, message)
class BadRequestException(message: String) : BusinessException(HttpStatus.BAD_REQUEST, message)
```

```kotlin
// src/main/kotlin/.../common/exception/GlobalExceptionHandler.kt
package org.flab.ensembleroomreservationproject.common.exception

import org.flab.ensembleroomreservationproject.common.dto.ApiResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException::class)
    fun handleBusinessException(e: BusinessException): ResponseEntity<ApiResponse<Nothing>> =
        ResponseEntity.status(e.status).body(ApiResponse.error(e.message))

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationException(e: MethodArgumentNotValidException): ResponseEntity<ApiResponse<Nothing>> {
        val message = e.bindingResult.fieldErrors
            .joinToString(", ") { "${it.field}: ${it.defaultMessage}" }
        return ResponseEntity.badRequest().body(ApiResponse.error(message))
    }
}
```

- [ ] **Step 5: 기존 테스트 수정 (Testcontainers 연동)**

```kotlin
// src/test/kotlin/.../EnsembleRoomReservationProjectApplicationTests.kt
package org.flab.ensembleroomreservationproject

import org.flab.ensembleroomreservationproject.support.TestContainersConfig
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import

@SpringBootTest
@Import(TestContainersConfig::class)
class EnsembleRoomReservationProjectApplicationTests {

    @Test
    fun contextLoads() {
    }
}
```

- [ ] **Step 6: 테스트 실행**

Run: `./gradlew test`
Expected: BUILD SUCCESSFUL (contextLoads 통과)

- [ ] **Step 7: 커밋**

```bash
git add -A
git commit -m "공통 모듈: ApiResponse, 예외 처리, JPA Auditing, Testcontainers 설정"
```

---

## Task 3: User 도메인 (Entity + Repository + Service + Controller)

**Files:**
- Create: `src/main/kotlin/.../user/entity/User.kt`
- Create: `src/main/kotlin/.../user/entity/UserRole.kt`
- Create: `src/main/kotlin/.../user/repository/UserRepository.kt`
- Create: `src/main/kotlin/.../user/dto/UserResponse.kt`
- Create: `src/main/kotlin/.../user/dto/UserUpdateRequest.kt`
- Create: `src/main/kotlin/.../user/service/UserService.kt`
- Create: `src/main/kotlin/.../user/controller/UserController.kt`
- Create: `src/test/kotlin/.../user/service/UserServiceTest.kt`
- Create: `src/test/kotlin/.../user/controller/UserControllerTest.kt`

- [ ] **Step 1: Entity + Enum**

```kotlin
// src/main/kotlin/.../user/entity/UserRole.kt
package org.flab.ensembleroomreservationproject.user.entity

enum class UserRole { USER, VENDOR, ADMIN }
```

```kotlin
// src/main/kotlin/.../user/entity/User.kt
package org.flab.ensembleroomreservationproject.user.entity

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant
import java.util.*

@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener::class)
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @Column(unique = true, nullable = false)
    val tossUserId: String,

    @Column(nullable = false)
    var nickname: String,

    var phone: String? = null,
    var profileImageUrl: String? = null,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var role: UserRole = UserRole.USER,

    @CreatedDate
    @Column(updatable = false)
    val createdAt: Instant? = null,

    @LastModifiedDate
    var updatedAt: Instant? = null
)
```

- [ ] **Step 2: Repository**

```kotlin
// src/main/kotlin/.../user/repository/UserRepository.kt
package org.flab.ensembleroomreservationproject.user.repository

import org.flab.ensembleroomreservationproject.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository : JpaRepository<User, UUID> {
    fun findByTossUserId(tossUserId: String): User?
}
```

- [ ] **Step 3: DTO**

```kotlin
// src/main/kotlin/.../user/dto/UserResponse.kt
package org.flab.ensembleroomreservationproject.user.dto

import org.flab.ensembleroomreservationproject.user.entity.User
import java.util.*

data class UserResponse(
    val id: UUID,
    val tossUserId: String,
    val nickname: String,
    val phone: String?,
    val profileImageUrl: String?,
    val role: String
) {
    companion object {
        fun from(user: User) = UserResponse(
            id = user.id!!,
            tossUserId = user.tossUserId,
            nickname = user.nickname,
            phone = user.phone,
            profileImageUrl = user.profileImageUrl,
            role = user.role.name
        )
    }
}
```

```kotlin
// src/main/kotlin/.../user/dto/UserUpdateRequest.kt
package org.flab.ensembleroomreservationproject.user.dto

data class UserUpdateRequest(
    val nickname: String?,
    val phone: String?,
    val profileImageUrl: String?
)
```

- [ ] **Step 4: Service 테스트 작성 (실패)**

```kotlin
// src/test/kotlin/.../user/service/UserServiceTest.kt
package org.flab.ensembleroomreservationproject.user.service

import org.flab.ensembleroomreservationproject.common.exception.NotFoundException
import org.flab.ensembleroomreservationproject.support.TestContainersConfig
import org.flab.ensembleroomreservationproject.user.entity.User
import org.flab.ensembleroomreservationproject.user.repository.UserRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

@SpringBootTest
@Import(TestContainersConfig::class)
class UserServiceTest {

    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var userRepository: UserRepository

    @BeforeEach
    fun setUp() {
        userRepository.deleteAll()
    }

    @Test
    fun `유저 조회 - 존재하는 유저`() {
        val saved = userRepository.save(User(tossUserId = "toss_123", nickname = "테스터"))
        val result = userService.getUser(saved.id!!)
        assertEquals("테스터", result.nickname)
    }

    @Test
    fun `유저 조회 - 존재하지 않는 유저`() {
        assertFailsWith<NotFoundException> {
            userService.getUser(java.util.UUID.randomUUID())
        }
    }

    @Test
    fun `유저 프로필 수정`() {
        val saved = userRepository.save(User(tossUserId = "toss_123", nickname = "기존이름"))
        val result = userService.updateUser(
            saved.id!!,
            org.flab.ensembleroomreservationproject.user.dto.UserUpdateRequest(
                nickname = "새이름", phone = "010-1234-5678", profileImageUrl = null
            )
        )
        assertEquals("새이름", result.nickname)
        assertEquals("010-1234-5678", result.phone)
    }
}
```

Run: `./gradlew test --tests "*.user.service.UserServiceTest"`
Expected: FAIL (UserService 클래스 없음)

- [ ] **Step 5: Service 구현**

```kotlin
// src/main/kotlin/.../user/service/UserService.kt
package org.flab.ensembleroomreservationproject.user.service

import org.flab.ensembleroomreservationproject.common.exception.NotFoundException
import org.flab.ensembleroomreservationproject.user.dto.UserResponse
import org.flab.ensembleroomreservationproject.user.dto.UserUpdateRequest
import org.flab.ensembleroomreservationproject.user.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional(readOnly = true)
class UserService(
    private val userRepository: UserRepository
) {
    fun getUser(id: UUID): UserResponse {
        val user = userRepository.findById(id)
            .orElseThrow { NotFoundException("유저를 찾을 수 없습니다: $id") }
        return UserResponse.from(user)
    }

    @Transactional
    fun updateUser(id: UUID, request: UserUpdateRequest): UserResponse {
        val user = userRepository.findById(id)
            .orElseThrow { NotFoundException("유저를 찾을 수 없습니다: $id") }
        request.nickname?.let { user.nickname = it }
        request.phone?.let { user.phone = it }
        request.profileImageUrl?.let { user.profileImageUrl = it }
        return UserResponse.from(user)
    }
}
```

- [ ] **Step 6: Service 테스트 통과 확인**

Run: `./gradlew test --tests "*.user.service.UserServiceTest"`
Expected: PASS (3 tests)

- [ ] **Step 7: Controller 테스트 작성 (실패)**

```kotlin
// src/test/kotlin/.../user/controller/UserControllerTest.kt
package org.flab.ensembleroomreservationproject.user.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.flab.ensembleroomreservationproject.support.TestContainersConfig
import org.flab.ensembleroomreservationproject.user.entity.User
import org.flab.ensembleroomreservationproject.user.repository.UserRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.patch

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestContainersConfig::class)
class UserControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Autowired
    lateinit var userRepository: UserRepository

    @BeforeEach
    fun setUp() {
        userRepository.deleteAll()
    }

    @Test
    fun `GET api v1 users id - 유저 조회 성공`() {
        val user = userRepository.save(User(tossUserId = "toss_1", nickname = "테스터"))

        mockMvc.get("/api/v1/users/${user.id}")
            .andExpect {
                status { isOk() }
                jsonPath("$.success") { value(true) }
                jsonPath("$.data.nickname") { value("테스터") }
            }
    }

    @Test
    fun `GET api v1 users id - 존재하지 않는 유저 404`() {
        mockMvc.get("/api/v1/users/${java.util.UUID.randomUUID()}")
            .andExpect {
                status { isNotFound() }
                jsonPath("$.success") { value(false) }
            }
    }

    @Test
    fun `PATCH api v1 users id - 프로필 수정`() {
        val user = userRepository.save(User(tossUserId = "toss_1", nickname = "기존"))

        mockMvc.patch("/api/v1/users/${user.id}") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(mapOf("nickname" to "수정됨"))
        }.andExpect {
            status { isOk() }
            jsonPath("$.data.nickname") { value("수정됨") }
        }
    }
}
```

Run: `./gradlew test --tests "*.user.controller.UserControllerTest"`
Expected: FAIL (UserController 없음)

- [ ] **Step 8: Controller 구현**

```kotlin
// src/main/kotlin/.../user/controller/UserController.kt
package org.flab.ensembleroomreservationproject.user.controller

import org.flab.ensembleroomreservationproject.common.dto.ApiResponse
import org.flab.ensembleroomreservationproject.user.dto.UserResponse
import org.flab.ensembleroomreservationproject.user.dto.UserUpdateRequest
import org.flab.ensembleroomreservationproject.user.service.UserService
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/users")
class UserController(
    private val userService: UserService
) {
    @GetMapping("/{id}")
    fun getUser(@PathVariable id: UUID): ApiResponse<UserResponse> =
        ApiResponse.ok(userService.getUser(id))

    @PatchMapping("/{id}")
    fun updateUser(
        @PathVariable id: UUID,
        @RequestBody request: UserUpdateRequest
    ): ApiResponse<UserResponse> =
        ApiResponse.ok(userService.updateUser(id, request))
}
```

- [ ] **Step 9: 전체 User 테스트 통과 확인**

Run: `./gradlew test --tests "*.user.*"`
Expected: PASS (6 tests)

- [ ] **Step 10: 커밋**

```bash
git add -A
git commit -m "User 도메인: Entity, Repository, Service, Controller + 테스트"
```

---

## Task 4: Vendor 도메인

**Files:**
- Create: `src/main/kotlin/.../vendor/entity/Vendor.kt`
- Create: `src/main/kotlin/.../vendor/entity/VendorStatus.kt`
- Create: `src/main/kotlin/.../vendor/entity/OperatingHour.kt`
- Create: `src/main/kotlin/.../vendor/repository/VendorRepository.kt`
- Create: `src/main/kotlin/.../vendor/dto/VendorCreateRequest.kt`
- Create: `src/main/kotlin/.../vendor/dto/VendorUpdateRequest.kt`
- Create: `src/main/kotlin/.../vendor/dto/VendorResponse.kt`
- Create: `src/main/kotlin/.../vendor/service/VendorService.kt`
- Create: `src/main/kotlin/.../vendor/controller/VendorController.kt`
- Create: `src/main/kotlin/.../vendor/controller/AdminVendorController.kt`
- Create: `src/test/kotlin/.../vendor/service/VendorServiceTest.kt`
- Create: `src/test/kotlin/.../vendor/controller/VendorControllerTest.kt`

- [ ] **Step 1: Entity + Enum + Value Object**

```kotlin
// src/main/kotlin/.../vendor/entity/VendorStatus.kt
package org.flab.ensembleroomreservationproject.vendor.entity

enum class VendorStatus { PENDING, APPROVED, REJECTED, SUSPENDED }
```

```kotlin
// src/main/kotlin/.../vendor/entity/OperatingHour.kt
package org.flab.ensembleroomreservationproject.vendor.entity

data class OperatingHour(
    val open: String,
    val close: String,
    val closed: Boolean = false
)
```

```kotlin
// src/main/kotlin/.../vendor/entity/Vendor.kt
package org.flab.ensembleroomreservationproject.vendor.entity

import jakarta.persistence.*
import org.flab.ensembleroomreservationproject.user.entity.User
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant
import java.util.*

@Entity
@Table(name = "vendors")
@EntityListeners(AuditingEntityListener::class)
class Vendor(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    val owner: User,

    @Column(nullable = false)
    var name: String,

    var description: String? = null,

    @Column(nullable = false)
    var phone: String,

    @Column(nullable = false)
    var address: String,

    var addressDetail: String? = null,
    var latitude: Double? = null,
    var longitude: Double? = null,

    @Column(nullable = false)
    var businessNumber: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: VendorStatus = VendorStatus.PENDING,

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    var operatingHours: Map<String, OperatingHour> = defaultOperatingHours(),

    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(columnDefinition = "text[]")
    var amenities: List<String> = emptyList(),

    var thumbnailUrl: String? = null,

    @CreatedDate
    @Column(updatable = false)
    val createdAt: Instant? = null,

    @LastModifiedDate
    var updatedAt: Instant? = null
) {
    companion object {
        fun defaultOperatingHours() = mapOf(
            "mon" to OperatingHour("09:00", "23:00"),
            "tue" to OperatingHour("09:00", "23:00"),
            "wed" to OperatingHour("09:00", "23:00"),
            "thu" to OperatingHour("09:00", "23:00"),
            "fri" to OperatingHour("09:00", "23:00"),
            "sat" to OperatingHour("10:00", "24:00"),
            "sun" to OperatingHour("10:00", "22:00")
        )
    }
}
```

- [ ] **Step 2: Repository**

```kotlin
// src/main/kotlin/.../vendor/repository/VendorRepository.kt
package org.flab.ensembleroomreservationproject.vendor.repository

import org.flab.ensembleroomreservationproject.vendor.entity.Vendor
import org.flab.ensembleroomreservationproject.vendor.entity.VendorStatus
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface VendorRepository : JpaRepository<Vendor, UUID> {
    fun findByStatus(status: VendorStatus, pageable: Pageable): Page<Vendor>
    fun findByOwnerId(ownerId: UUID): List<Vendor>
}
```

- [ ] **Step 3: DTO**

```kotlin
// src/main/kotlin/.../vendor/dto/VendorCreateRequest.kt
package org.flab.ensembleroomreservationproject.vendor.dto

import jakarta.validation.constraints.NotBlank
import org.flab.ensembleroomreservationproject.vendor.entity.OperatingHour
import java.util.*

data class VendorCreateRequest(
    val ownerId: UUID,
    @field:NotBlank(message = "업체명은 필수입니다")
    val name: String,
    val description: String? = null,
    @field:NotBlank(message = "연락처는 필수입니다")
    val phone: String,
    @field:NotBlank(message = "주소는 필수입니다")
    val address: String,
    val addressDetail: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
    @field:NotBlank(message = "사업자등록번호는 필수입니다")
    val businessNumber: String,
    val operatingHours: Map<String, OperatingHour>? = null,
    val thumbnailUrl: String? = null
)
```

```kotlin
// src/main/kotlin/.../vendor/dto/VendorUpdateRequest.kt
package org.flab.ensembleroomreservationproject.vendor.dto

import org.flab.ensembleroomreservationproject.vendor.entity.OperatingHour

data class VendorUpdateRequest(
    val name: String? = null,
    val description: String? = null,
    val phone: String? = null,
    val address: String? = null,
    val addressDetail: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val operatingHours: Map<String, OperatingHour>? = null,
    val thumbnailUrl: String? = null
)
```

```kotlin
// src/main/kotlin/.../vendor/dto/VendorResponse.kt
package org.flab.ensembleroomreservationproject.vendor.dto

import org.flab.ensembleroomreservationproject.vendor.entity.OperatingHour
import org.flab.ensembleroomreservationproject.vendor.entity.Vendor
import java.util.*

data class VendorResponse(
    val id: UUID,
    val ownerId: UUID,
    val name: String,
    val description: String?,
    val phone: String,
    val address: String,
    val addressDetail: String?,
    val latitude: Double?,
    val longitude: Double?,
    val businessNumber: String,
    val status: String,
    val operatingHours: Map<String, OperatingHour>,
    val thumbnailUrl: String?
) {
    companion object {
        fun from(vendor: Vendor) = VendorResponse(
            id = vendor.id!!,
            ownerId = vendor.owner.id!!,
            name = vendor.name,
            description = vendor.description,
            phone = vendor.phone,
            address = vendor.address,
            addressDetail = vendor.addressDetail,
            latitude = vendor.latitude,
            longitude = vendor.longitude,
            businessNumber = vendor.businessNumber,
            status = vendor.status.name,
            operatingHours = vendor.operatingHours,
            thumbnailUrl = vendor.thumbnailUrl
        )
    }
}
```

- [ ] **Step 4: Service 테스트 작성 (실패)**

```kotlin
// src/test/kotlin/.../vendor/service/VendorServiceTest.kt
package org.flab.ensembleroomreservationproject.vendor.service

import org.flab.ensembleroomreservationproject.common.exception.NotFoundException
import org.flab.ensembleroomreservationproject.support.TestContainersConfig
import org.flab.ensembleroomreservationproject.user.entity.User
import org.flab.ensembleroomreservationproject.user.repository.UserRepository
import org.flab.ensembleroomreservationproject.vendor.dto.VendorCreateRequest
import org.flab.ensembleroomreservationproject.vendor.entity.VendorStatus
import org.flab.ensembleroomreservationproject.vendor.repository.VendorRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.data.domain.PageRequest
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

@SpringBootTest
@Import(TestContainersConfig::class)
class VendorServiceTest {

    @Autowired lateinit var vendorService: VendorService
    @Autowired lateinit var vendorRepository: VendorRepository
    @Autowired lateinit var userRepository: UserRepository

    lateinit var owner: User

    @BeforeEach
    fun setUp() {
        vendorRepository.deleteAll()
        userRepository.deleteAll()
        owner = userRepository.save(User(tossUserId = "owner_1", nickname = "사장님"))
    }

    @Test
    fun `업체 등록 신청`() {
        val request = VendorCreateRequest(
            ownerId = owner.id!!,
            name = "뮤직스튜디오",
            phone = "02-1234-5678",
            address = "서울시 강남구",
            businessNumber = "123-45-67890"
        )
        val result = vendorService.createVendor(request)
        assertEquals("뮤직스튜디오", result.name)
        assertEquals("PENDING", result.status)
    }

    @Test
    fun `승인된 업체 목록 조회`() {
        val request = VendorCreateRequest(
            ownerId = owner.id!!, name = "스튜디오A", phone = "02-0000-0000",
            address = "서울", businessNumber = "111-11-11111"
        )
        vendorService.createVendor(request)
        // 아직 PENDING이므로 승인 목록에 없어야 함
        val result = vendorService.getApprovedVendors(PageRequest.of(0, 20))
        assertEquals(0, result.totalElements)
    }

    @Test
    fun `업체 상세 조회 - 존재하지 않는 업체`() {
        assertFailsWith<NotFoundException> {
            vendorService.getVendor(java.util.UUID.randomUUID())
        }
    }

    @Test
    fun `업체 승인`() {
        val request = VendorCreateRequest(
            ownerId = owner.id!!, name = "스튜디오B", phone = "02-0000-0000",
            address = "서울", businessNumber = "222-22-22222"
        )
        val created = vendorService.createVendor(request)
        val approved = vendorService.approveVendor(created.id)
        assertEquals("APPROVED", approved.status)
    }
}
```

Run: `./gradlew test --tests "*.vendor.service.VendorServiceTest"`
Expected: FAIL (VendorService 없음)

- [ ] **Step 5: Service 구현**

```kotlin
// src/main/kotlin/.../vendor/service/VendorService.kt
package org.flab.ensembleroomreservationproject.vendor.service

import org.flab.ensembleroomreservationproject.common.exception.NotFoundException
import org.flab.ensembleroomreservationproject.user.repository.UserRepository
import org.flab.ensembleroomreservationproject.vendor.dto.VendorCreateRequest
import org.flab.ensembleroomreservationproject.vendor.dto.VendorResponse
import org.flab.ensembleroomreservationproject.vendor.dto.VendorUpdateRequest
import org.flab.ensembleroomreservationproject.vendor.entity.Vendor
import org.flab.ensembleroomreservationproject.vendor.entity.VendorStatus
import org.flab.ensembleroomreservationproject.vendor.repository.VendorRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional(readOnly = true)
class VendorService(
    private val vendorRepository: VendorRepository,
    private val userRepository: UserRepository
) {
    @Transactional
    fun createVendor(request: VendorCreateRequest): VendorResponse {
        val owner = userRepository.findById(request.ownerId)
            .orElseThrow { NotFoundException("유저를 찾을 수 없습니다: ${request.ownerId}") }

        val vendor = vendorRepository.save(
            Vendor(
                owner = owner,
                name = request.name,
                description = request.description,
                phone = request.phone,
                address = request.address,
                addressDetail = request.addressDetail,
                latitude = request.latitude,
                longitude = request.longitude,
                businessNumber = request.businessNumber,
                operatingHours = request.operatingHours ?: Vendor.defaultOperatingHours(),
                thumbnailUrl = request.thumbnailUrl
            )
        )
        return VendorResponse.from(vendor)
    }

    fun getApprovedVendors(pageable: Pageable): Page<VendorResponse> =
        vendorRepository.findByStatus(VendorStatus.APPROVED, pageable)
            .map { VendorResponse.from(it) }

    fun getVendor(id: UUID): VendorResponse {
        val vendor = vendorRepository.findById(id)
            .orElseThrow { NotFoundException("업체를 찾을 수 없습니다: $id") }
        return VendorResponse.from(vendor)
    }

    @Transactional
    fun updateVendor(id: UUID, request: VendorUpdateRequest): VendorResponse {
        val vendor = vendorRepository.findById(id)
            .orElseThrow { NotFoundException("업체를 찾을 수 없습니다: $id") }
        request.name?.let { vendor.name = it }
        request.description?.let { vendor.description = it }
        request.phone?.let { vendor.phone = it }
        request.address?.let { vendor.address = it }
        request.addressDetail?.let { vendor.addressDetail = it }
        request.latitude?.let { vendor.latitude = it }
        request.longitude?.let { vendor.longitude = it }
        request.operatingHours?.let { vendor.operatingHours = it }
        request.thumbnailUrl?.let { vendor.thumbnailUrl = it }
        return VendorResponse.from(vendor)
    }

    @Transactional
    fun approveVendor(id: UUID): VendorResponse {
        val vendor = vendorRepository.findById(id)
            .orElseThrow { NotFoundException("업체를 찾을 수 없습니다: $id") }
        vendor.status = VendorStatus.APPROVED
        return VendorResponse.from(vendor)
    }

    @Transactional
    fun rejectVendor(id: UUID): VendorResponse {
        val vendor = vendorRepository.findById(id)
            .orElseThrow { NotFoundException("업체를 찾을 수 없습니다: $id") }
        vendor.status = VendorStatus.REJECTED
        return VendorResponse.from(vendor)
    }
}
```

- [ ] **Step 6: Service 테스트 통과 확인**

Run: `./gradlew test --tests "*.vendor.service.VendorServiceTest"`
Expected: PASS (4 tests)

- [ ] **Step 7: Controller 테스트 작성 (실패)**

```kotlin
// src/test/kotlin/.../vendor/controller/VendorControllerTest.kt
package org.flab.ensembleroomreservationproject.vendor.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.flab.ensembleroomreservationproject.support.TestContainersConfig
import org.flab.ensembleroomreservationproject.user.entity.User
import org.flab.ensembleroomreservationproject.user.repository.UserRepository
import org.flab.ensembleroomreservationproject.vendor.repository.VendorRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.patch
import org.springframework.test.web.servlet.post

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestContainersConfig::class)
class VendorControllerTest {

    @Autowired lateinit var mockMvc: MockMvc
    @Autowired lateinit var objectMapper: ObjectMapper
    @Autowired lateinit var userRepository: UserRepository
    @Autowired lateinit var vendorRepository: VendorRepository

    lateinit var owner: User

    @BeforeEach
    fun setUp() {
        vendorRepository.deleteAll()
        userRepository.deleteAll()
        owner = userRepository.save(User(tossUserId = "owner_1", nickname = "사장님"))
    }

    @Test
    fun `POST api v1 vendors - 업체 등록`() {
        mockMvc.post("/api/v1/vendors") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(mapOf(
                "owner_id" to owner.id,
                "name" to "테스트스튜디오",
                "phone" to "02-1111-2222",
                "address" to "서울시 마포구",
                "business_number" to "123-45-67890"
            ))
        }.andExpect {
            status { isOk() }
            jsonPath("$.data.name") { value("테스트스튜디오") }
            jsonPath("$.data.status") { value("PENDING") }
        }
    }

    @Test
    fun `GET api v1 vendors - 승인된 업체 목록 (빈 목록)`() {
        mockMvc.get("/api/v1/vendors")
            .andExpect {
                status { isOk() }
                jsonPath("$.data.content") { isArray() }
            }
    }

    @Test
    fun `POST api v1 admin vendors approve - 업체 승인`() {
        // 먼저 업체 생성
        val result = mockMvc.post("/api/v1/vendors") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(mapOf(
                "owner_id" to owner.id,
                "name" to "승인대기스튜디오",
                "phone" to "02-3333-4444",
                "address" to "서울시 송파구",
                "business_number" to "999-99-99999"
            ))
        }.andReturn()

        val vendorId = objectMapper.readTree(result.response.contentAsString)
            .at("/data/id").asText()

        mockMvc.post("/api/v1/admin/vendors/$vendorId/approve")
            .andExpect {
                status { isOk() }
                jsonPath("$.data.status") { value("APPROVED") }
            }
    }
}
```

Run: `./gradlew test --tests "*.vendor.controller.VendorControllerTest"`
Expected: FAIL (Controller 없음)

- [ ] **Step 8: Controller 구현**

```kotlin
// src/main/kotlin/.../vendor/controller/VendorController.kt
package org.flab.ensembleroomreservationproject.vendor.controller

import jakarta.validation.Valid
import org.flab.ensembleroomreservationproject.common.dto.ApiResponse
import org.flab.ensembleroomreservationproject.vendor.dto.VendorCreateRequest
import org.flab.ensembleroomreservationproject.vendor.dto.VendorResponse
import org.flab.ensembleroomreservationproject.vendor.dto.VendorUpdateRequest
import org.flab.ensembleroomreservationproject.vendor.service.VendorService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/vendors")
class VendorController(
    private val vendorService: VendorService
) {
    @PostMapping
    fun createVendor(@Valid @RequestBody request: VendorCreateRequest): ApiResponse<VendorResponse> =
        ApiResponse.ok(vendorService.createVendor(request))

    @GetMapping
    fun getApprovedVendors(@PageableDefault(size = 20) pageable: Pageable): ApiResponse<Page<VendorResponse>> =
        ApiResponse.ok(vendorService.getApprovedVendors(pageable))

    @GetMapping("/{id}")
    fun getVendor(@PathVariable id: UUID): ApiResponse<VendorResponse> =
        ApiResponse.ok(vendorService.getVendor(id))

    @PatchMapping("/{id}")
    fun updateVendor(
        @PathVariable id: UUID,
        @RequestBody request: VendorUpdateRequest
    ): ApiResponse<VendorResponse> =
        ApiResponse.ok(vendorService.updateVendor(id, request))
}
```

```kotlin
// src/main/kotlin/.../vendor/controller/AdminVendorController.kt
package org.flab.ensembleroomreservationproject.vendor.controller

import org.flab.ensembleroomreservationproject.common.dto.ApiResponse
import org.flab.ensembleroomreservationproject.vendor.dto.VendorResponse
import org.flab.ensembleroomreservationproject.vendor.service.VendorService
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/admin/vendors")
class AdminVendorController(
    private val vendorService: VendorService
) {
    @PostMapping("/{id}/approve")
    fun approveVendor(@PathVariable id: UUID): ApiResponse<VendorResponse> =
        ApiResponse.ok(vendorService.approveVendor(id))

    @PostMapping("/{id}/reject")
    fun rejectVendor(@PathVariable id: UUID): ApiResponse<VendorResponse> =
        ApiResponse.ok(vendorService.rejectVendor(id))
}
```

- [ ] **Step 9: 전체 Vendor 테스트 통과 확인**

Run: `./gradlew test --tests "*.vendor.*"`
Expected: PASS (7 tests)

- [ ] **Step 10: 커밋**

```bash
git add -A
git commit -m "Vendor 도메인: 업체 등록/조회/수정/승인/거절 + 테스트"
```

---

## Task 5: Room 도메인

**Files:**
- Create: `src/main/kotlin/.../room/entity/Room.kt`
- Create: `src/main/kotlin/.../room/entity/Equipment.kt`
- Create: `src/main/kotlin/.../room/entity/TimeSlotOverride.kt`
- Create: `src/main/kotlin/.../room/repository/RoomRepository.kt`
- Create: `src/main/kotlin/.../room/repository/TimeSlotOverrideRepository.kt`
- Create: `src/main/kotlin/.../room/dto/RoomCreateRequest.kt`
- Create: `src/main/kotlin/.../room/dto/RoomUpdateRequest.kt`
- Create: `src/main/kotlin/.../room/dto/RoomResponse.kt`
- Create: `src/main/kotlin/.../room/dto/AvailabilityResponse.kt`
- Create: `src/main/kotlin/.../room/service/RoomService.kt`
- Create: `src/main/kotlin/.../room/controller/RoomController.kt`
- Create: `src/test/kotlin/.../room/service/RoomServiceTest.kt`
- Create: `src/test/kotlin/.../room/controller/RoomControllerTest.kt`

- [ ] **Step 1: Entity + Value Object**

```kotlin
// src/main/kotlin/.../room/entity/Equipment.kt
package org.flab.ensembleroomreservationproject.room.entity

data class Equipment(
    val name: String,
    val brand: String? = null
)
```

```kotlin
// src/main/kotlin/.../room/entity/Room.kt
package org.flab.ensembleroomreservationproject.room.entity

import jakarta.persistence.*
import org.flab.ensembleroomreservationproject.vendor.entity.Vendor
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant
import java.util.*

@Entity
@Table(name = "rooms")
@EntityListeners(AuditingEntityListener::class)
class Room(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id", nullable = false)
    val vendor: Vendor,

    @Column(nullable = false)
    var name: String,

    var description: String? = null,

    @Column(nullable = false)
    var capacity: Int,

    @Column(nullable = false)
    var hourlyPrice: Int,

    @Column(nullable = false)
    var minHours: Int = 1,

    @Column(nullable = false)
    var maxHours: Int = 4,

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    var equipment: List<Equipment> = emptyList(),

    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(columnDefinition = "text[]")
    var images: List<String> = emptyList(),

    var isActive: Boolean = true,
    var sortOrder: Int = 0,

    @CreatedDate
    @Column(updatable = false)
    val createdAt: Instant? = null,

    @LastModifiedDate
    var updatedAt: Instant? = null
)
```

```kotlin
// src/main/kotlin/.../room/entity/TimeSlotOverride.kt
package org.flab.ensembleroomreservationproject.room.entity

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

@Entity
@Table(
    name = "time_slot_overrides",
    uniqueConstraints = [UniqueConstraint(columnNames = ["room_id", "date", "start_time"])]
)
@EntityListeners(AuditingEntityListener::class)
class TimeSlotOverride(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    val room: Room,

    @Column(nullable = false)
    val date: LocalDate,

    @Column(nullable = false)
    val startTime: LocalTime,

    @Column(nullable = false)
    val endTime: LocalTime,

    var overridePrice: Int? = null,
    var isBlocked: Boolean = false,
    var reason: String? = null,

    @CreatedDate
    @Column(updatable = false)
    val createdAt: Instant? = null
)
```

- [ ] **Step 2: Repository**

```kotlin
// src/main/kotlin/.../room/repository/RoomRepository.kt
package org.flab.ensembleroomreservationproject.room.repository

import org.flab.ensembleroomreservationproject.room.entity.Room
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface RoomRepository : JpaRepository<Room, UUID> {
    fun findByVendorIdAndIsActiveTrue(vendorId: UUID): List<Room>
}
```

```kotlin
// src/main/kotlin/.../room/repository/TimeSlotOverrideRepository.kt
package org.flab.ensembleroomreservationproject.room.repository

import org.flab.ensembleroomreservationproject.room.entity.TimeSlotOverride
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate
import java.util.*

interface TimeSlotOverrideRepository : JpaRepository<TimeSlotOverride, UUID> {
    fun findByRoomIdAndDate(roomId: UUID, date: LocalDate): List<TimeSlotOverride>
}
```

- [ ] **Step 3: DTO**

```kotlin
// src/main/kotlin/.../room/dto/RoomCreateRequest.kt
package org.flab.ensembleroomreservationproject.room.dto

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import org.flab.ensembleroomreservationproject.room.entity.Equipment

data class RoomCreateRequest(
    @field:NotBlank(message = "룸 이름은 필수입니다")
    val name: String,
    val description: String? = null,
    @field:Min(1, message = "수용 인원은 1명 이상이어야 합니다")
    val capacity: Int,
    @field:Min(0, message = "가격은 0 이상이어야 합니다")
    val hourlyPrice: Int,
    val minHours: Int = 1,
    val maxHours: Int = 4,
    val equipment: List<Equipment> = emptyList()
)
```

```kotlin
// src/main/kotlin/.../room/dto/RoomUpdateRequest.kt
package org.flab.ensembleroomreservationproject.room.dto

import org.flab.ensembleroomreservationproject.room.entity.Equipment

data class RoomUpdateRequest(
    val name: String? = null,
    val description: String? = null,
    val capacity: Int? = null,
    val hourlyPrice: Int? = null,
    val minHours: Int? = null,
    val maxHours: Int? = null,
    val equipment: List<Equipment>? = null
)
```

```kotlin
// src/main/kotlin/.../room/dto/RoomResponse.kt
package org.flab.ensembleroomreservationproject.room.dto

import org.flab.ensembleroomreservationproject.room.entity.Equipment
import org.flab.ensembleroomreservationproject.room.entity.Room
import java.util.*

data class RoomResponse(
    val id: UUID,
    val vendorId: UUID,
    val name: String,
    val description: String?,
    val capacity: Int,
    val hourlyPrice: Int,
    val minHours: Int,
    val maxHours: Int,
    val equipment: List<Equipment>,
    val isActive: Boolean
) {
    companion object {
        fun from(room: Room) = RoomResponse(
            id = room.id!!,
            vendorId = room.vendor.id!!,
            name = room.name,
            description = room.description,
            capacity = room.capacity,
            hourlyPrice = room.hourlyPrice,
            minHours = room.minHours,
            maxHours = room.maxHours,
            equipment = room.equipment,
            isActive = room.isActive
        )
    }
}
```

```kotlin
// src/main/kotlin/.../room/dto/AvailabilityResponse.kt
package org.flab.ensembleroomreservationproject.room.dto

import java.time.LocalDate
import java.util.*

data class AvailabilityResponse(
    val roomId: UUID,
    val date: LocalDate,
    val operatingHours: Map<String, String>,
    val hourlyPrice: Int,
    val slots: List<TimeSlot>
)

data class TimeSlot(
    val start: String,
    val end: String,
    val available: Boolean,
    val price: Int
)
```

- [ ] **Step 4: Service 테스트 작성 (실패)**

```kotlin
// src/test/kotlin/.../room/service/RoomServiceTest.kt
package org.flab.ensembleroomreservationproject.room.service

import org.flab.ensembleroomreservationproject.common.exception.NotFoundException
import org.flab.ensembleroomreservationproject.room.dto.RoomCreateRequest
import org.flab.ensembleroomreservationproject.room.entity.Equipment
import org.flab.ensembleroomreservationproject.room.repository.RoomRepository
import org.flab.ensembleroomreservationproject.support.TestContainersConfig
import org.flab.ensembleroomreservationproject.user.entity.User
import org.flab.ensembleroomreservationproject.user.repository.UserRepository
import org.flab.ensembleroomreservationproject.vendor.entity.Vendor
import org.flab.ensembleroomreservationproject.vendor.repository.VendorRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import java.time.LocalDate
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

@SpringBootTest
@Import(TestContainersConfig::class)
class RoomServiceTest {

    @Autowired lateinit var roomService: RoomService
    @Autowired lateinit var roomRepository: RoomRepository
    @Autowired lateinit var vendorRepository: VendorRepository
    @Autowired lateinit var userRepository: UserRepository

    lateinit var vendor: Vendor

    @BeforeEach
    fun setUp() {
        roomRepository.deleteAll()
        vendorRepository.deleteAll()
        userRepository.deleteAll()
        val owner = userRepository.save(User(tossUserId = "owner_1", nickname = "사장님"))
        vendor = vendorRepository.save(
            Vendor(owner = owner, name = "스튜디오", phone = "02-0000", address = "서울", businessNumber = "111-11-11111")
        )
    }

    @Test
    fun `룸 생성`() {
        val request = RoomCreateRequest(
            name = "A룸", capacity = 5, hourlyPrice = 15000,
            equipment = listOf(Equipment("드럼", "Pearl"))
        )
        val result = roomService.createRoom(vendor.id!!, request)
        assertEquals("A룸", result.name)
        assertEquals(15000, result.hourlyPrice)
        assertEquals(1, result.equipment.size)
    }

    @Test
    fun `업체별 룸 목록 조회`() {
        roomService.createRoom(vendor.id!!, RoomCreateRequest(name = "A룸", capacity = 5, hourlyPrice = 15000))
        roomService.createRoom(vendor.id!!, RoomCreateRequest(name = "B룸", capacity = 3, hourlyPrice = 10000))
        val rooms = roomService.getRoomsByVendor(vendor.id!!)
        assertEquals(2, rooms.size)
    }

    @Test
    fun `룸 soft 삭제`() {
        val room = roomService.createRoom(vendor.id!!, RoomCreateRequest(name = "삭제룸", capacity = 2, hourlyPrice = 8000))
        roomService.deleteRoom(room.id)
        val rooms = roomService.getRoomsByVendor(vendor.id!!)
        assertEquals(0, rooms.size)
    }

    @Test
    fun `예약 가능 시간 조회 - 빈 슬롯`() {
        val room = roomService.createRoom(vendor.id!!, RoomCreateRequest(name = "A룸", capacity = 5, hourlyPrice = 15000))
        val availability = roomService.getAvailability(room.id, LocalDate.of(2026, 3, 16)) // 월요일
        assertEquals(true, availability.slots.isNotEmpty())
        assertEquals(true, availability.slots.all { it.available })
    }
}
```

Run: `./gradlew test --tests "*.room.service.RoomServiceTest"`
Expected: FAIL (RoomService 없음)

- [ ] **Step 5: Service 구현**

```kotlin
// src/main/kotlin/.../room/service/RoomService.kt
package org.flab.ensembleroomreservationproject.room.service

import org.flab.ensembleroomreservationproject.common.exception.NotFoundException
import org.flab.ensembleroomreservationproject.room.dto.*
import org.flab.ensembleroomreservationproject.room.entity.Room
import org.flab.ensembleroomreservationproject.room.repository.RoomRepository
import org.flab.ensembleroomreservationproject.room.repository.TimeSlotOverrideRepository
import org.flab.ensembleroomreservationproject.vendor.repository.VendorRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.TextStyle
import java.util.*

@Service
@Transactional(readOnly = true)
class RoomService(
    private val roomRepository: RoomRepository,
    private val vendorRepository: VendorRepository,
    private val timeSlotOverrideRepository: TimeSlotOverrideRepository
) {
    @Transactional
    fun createRoom(vendorId: UUID, request: RoomCreateRequest): RoomResponse {
        val vendor = vendorRepository.findById(vendorId)
            .orElseThrow { NotFoundException("업체를 찾을 수 없습니다: $vendorId") }

        val room = roomRepository.save(
            Room(
                vendor = vendor,
                name = request.name,
                description = request.description,
                capacity = request.capacity,
                hourlyPrice = request.hourlyPrice,
                minHours = request.minHours,
                maxHours = request.maxHours,
                equipment = request.equipment
            )
        )
        return RoomResponse.from(room)
    }

    fun getRoomsByVendor(vendorId: UUID): List<RoomResponse> =
        roomRepository.findByVendorIdAndIsActiveTrue(vendorId)
            .map { RoomResponse.from(it) }

    @Transactional
    fun updateRoom(id: UUID, request: RoomUpdateRequest): RoomResponse {
        val room = roomRepository.findById(id)
            .orElseThrow { NotFoundException("룸을 찾을 수 없습니다: $id") }
        request.name?.let { room.name = it }
        request.description?.let { room.description = it }
        request.capacity?.let { room.capacity = it }
        request.hourlyPrice?.let { room.hourlyPrice = it }
        request.minHours?.let { room.minHours = it }
        request.maxHours?.let { room.maxHours = it }
        request.equipment?.let { room.equipment = it }
        return RoomResponse.from(room)
    }

    @Transactional
    fun deleteRoom(id: UUID) {
        val room = roomRepository.findById(id)
            .orElseThrow { NotFoundException("룸을 찾을 수 없습니다: $id") }
        room.isActive = false
    }

    fun getAvailability(roomId: UUID, date: LocalDate): AvailabilityResponse {
        val room = roomRepository.findById(roomId)
            .orElseThrow { NotFoundException("룸을 찾을 수 없습니다: $roomId") }

        val dayKey = date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.ENGLISH).lowercase()
        val hours = room.vendor.operatingHours[dayKey]
            ?: throw NotFoundException("해당 요일의 운영시간 정보가 없습니다")

        if (hours.closed) {
            return AvailabilityResponse(
                roomId = roomId, date = date,
                operatingHours = mapOf("open" to hours.open, "close" to hours.close),
                hourlyPrice = room.hourlyPrice, slots = emptyList()
            )
        }

        val overrides = timeSlotOverrideRepository.findByRoomIdAndDate(roomId, date)
        val overrideMap = overrides.associateBy { it.startTime }

        val openTime = LocalTime.parse(hours.open)
        val isMidnightClose = hours.close == "24:00"
        val closeHour = if (isMidnightClose) 24 else LocalTime.parse(hours.close).hour

        val slots = mutableListOf<TimeSlot>()
        var currentHour = openTime.hour
        while (currentHour < closeHour) {
            val current = LocalTime.of(currentHour, 0)
            val nextHour = currentHour + 1
            val nextDisplay = if (nextHour == 24) "24:00" else LocalTime.of(nextHour, 0).toString()
            val override = overrideMap[current]
            val blocked = override?.isBlocked ?: false
            val price = override?.overridePrice ?: room.hourlyPrice

            slots.add(TimeSlot(
                start = current.toString(),
                end = nextDisplay,
                available = !blocked,
                price = price
            ))
            currentHour = nextHour
        }

        return AvailabilityResponse(
            roomId = roomId, date = date,
            operatingHours = mapOf("open" to hours.open, "close" to hours.close),
            hourlyPrice = room.hourlyPrice, slots = slots
        )
    }
}
```

> Note: 예약된 시간대 필터링은 Task 6 (Reservation)에서 추가합니다.

- [ ] **Step 6: Service 테스트 통과 확인**

Run: `./gradlew test --tests "*.room.service.RoomServiceTest"`
Expected: PASS (4 tests)

- [ ] **Step 7: Controller 테스트 작성 (실패)**

```kotlin
// src/test/kotlin/.../room/controller/RoomControllerTest.kt
package org.flab.ensembleroomreservationproject.room.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.flab.ensembleroomreservationproject.room.repository.RoomRepository
import org.flab.ensembleroomreservationproject.support.TestContainersConfig
import org.flab.ensembleroomreservationproject.user.entity.User
import org.flab.ensembleroomreservationproject.user.repository.UserRepository
import org.flab.ensembleroomreservationproject.vendor.entity.Vendor
import org.flab.ensembleroomreservationproject.vendor.repository.VendorRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestContainersConfig::class)
class RoomControllerTest {

    @Autowired lateinit var mockMvc: MockMvc
    @Autowired lateinit var objectMapper: ObjectMapper
    @Autowired lateinit var roomRepository: RoomRepository
    @Autowired lateinit var vendorRepository: VendorRepository
    @Autowired lateinit var userRepository: UserRepository

    lateinit var vendor: Vendor

    @BeforeEach
    fun setUp() {
        roomRepository.deleteAll()
        vendorRepository.deleteAll()
        userRepository.deleteAll()
        val owner = userRepository.save(User(tossUserId = "owner_1", nickname = "사장님"))
        vendor = vendorRepository.save(
            Vendor(owner = owner, name = "스튜디오", phone = "02-0000", address = "서울", businessNumber = "111-11-11111")
        )
    }

    @Test
    fun `POST api v1 vendors vendorId rooms - 룸 생성`() {
        mockMvc.post("/api/v1/vendors/${vendor.id}/rooms") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(mapOf(
                "name" to "A룸",
                "capacity" to 5,
                "hourly_price" to 15000
            ))
        }.andExpect {
            status { isOk() }
            jsonPath("$.data.name") { value("A룸") }
        }
    }

    @Test
    fun `GET api v1 vendors vendorId rooms - 룸 목록`() {
        mockMvc.post("/api/v1/vendors/${vendor.id}/rooms") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(mapOf("name" to "A룸", "capacity" to 5, "hourly_price" to 15000))
        }
        mockMvc.get("/api/v1/vendors/${vendor.id}/rooms")
            .andExpect {
                status { isOk() }
                jsonPath("$.data.length()") { value(1) }
            }
    }
}
```

Run: `./gradlew test --tests "*.room.controller.RoomControllerTest"`
Expected: FAIL (RoomController 없음)

- [ ] **Step 8: Controller 구현**

```kotlin
// src/main/kotlin/.../room/controller/RoomController.kt
package org.flab.ensembleroomreservationproject.room.controller

import jakarta.validation.Valid
import org.flab.ensembleroomreservationproject.common.dto.ApiResponse
import org.flab.ensembleroomreservationproject.room.dto.*
import org.flab.ensembleroomreservationproject.room.service.RoomService
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.util.*

@RestController
class RoomController(
    private val roomService: RoomService
) {
    @PostMapping("/api/v1/vendors/{vendorId}/rooms")
    fun createRoom(
        @PathVariable vendorId: UUID,
        @Valid @RequestBody request: RoomCreateRequest
    ): ApiResponse<RoomResponse> =
        ApiResponse.ok(roomService.createRoom(vendorId, request))

    @GetMapping("/api/v1/vendors/{vendorId}/rooms")
    fun getRoomsByVendor(@PathVariable vendorId: UUID): ApiResponse<List<RoomResponse>> =
        ApiResponse.ok(roomService.getRoomsByVendor(vendorId))

    @PatchMapping("/api/v1/rooms/{id}")
    fun updateRoom(
        @PathVariable id: UUID,
        @RequestBody request: RoomUpdateRequest
    ): ApiResponse<RoomResponse> =
        ApiResponse.ok(roomService.updateRoom(id, request))

    @DeleteMapping("/api/v1/rooms/{id}")
    fun deleteRoom(@PathVariable id: UUID): ApiResponse<Nothing> {
        roomService.deleteRoom(id)
        return ApiResponse(success = true)
    }

    @GetMapping("/api/v1/rooms/{roomId}/availability")
    fun getAvailability(
        @PathVariable roomId: UUID,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) date: LocalDate
    ): ApiResponse<AvailabilityResponse> =
        ApiResponse.ok(roomService.getAvailability(roomId, date))
}
```

- [ ] **Step 9: 전체 Room 테스트 통과 확인**

Run: `./gradlew test --tests "*.room.*"`
Expected: PASS (6 tests)

- [ ] **Step 10: 커밋**

```bash
git add -A
git commit -m "Room 도메인: 룸 CRUD, 시간 슬롯 오버라이드, 예약 가능 시간 조회 + 테스트"
```

---

## Task 6: Reservation 도메인

**Files:**
- Create: `src/main/kotlin/.../reservation/entity/Reservation.kt`
- Create: `src/main/kotlin/.../reservation/entity/ReservationStatus.kt`
- Create: `src/main/kotlin/.../reservation/repository/ReservationRepository.kt`
- Create: `src/main/kotlin/.../reservation/service/ReservationNumberGenerator.kt`
- Create: `src/main/kotlin/.../reservation/dto/ReservationCreateRequest.kt`
- Create: `src/main/kotlin/.../reservation/dto/ReservationResponse.kt`
- Create: `src/main/kotlin/.../reservation/dto/CancelRequest.kt`
- Create: `src/main/kotlin/.../reservation/service/ReservationService.kt`
- Create: `src/main/kotlin/.../reservation/controller/ReservationController.kt`
- Create: `src/test/kotlin/.../reservation/service/ReservationNumberGeneratorTest.kt`
- Create: `src/test/kotlin/.../reservation/service/ReservationServiceTest.kt`
- Create: `src/test/kotlin/.../reservation/controller/ReservationControllerTest.kt`

- [ ] **Step 1: Entity + Enum**

```kotlin
// src/main/kotlin/.../reservation/entity/ReservationStatus.kt
package org.flab.ensembleroomreservationproject.reservation.entity

enum class ReservationStatus {
    PENDING, CONFIRMED, CANCELLED, COMPLETED, NO_SHOW
}
```

```kotlin
// src/main/kotlin/.../reservation/entity/Reservation.kt
package org.flab.ensembleroomreservationproject.reservation.entity

import jakarta.persistence.*
import org.flab.ensembleroomreservationproject.room.entity.Room
import org.flab.ensembleroomreservationproject.user.entity.User
import org.flab.ensembleroomreservationproject.vendor.entity.Vendor
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

@Entity
@Table(name = "reservations")
@EntityListeners(AuditingEntityListener::class)
class Reservation(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @Column(unique = true, nullable = false)
    val reservationNumber: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    val room: Room,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id", nullable = false)
    val vendor: Vendor,

    @Column(nullable = false)
    val date: LocalDate,

    @Column(nullable = false)
    val startTime: LocalTime,

    @Column(nullable = false)
    val endTime: LocalTime,

    @Column(nullable = false)
    val durationHours: Int,

    @Column(nullable = false)
    val totalPrice: Int,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: ReservationStatus = ReservationStatus.PENDING,

    var userMemo: String? = null,
    var vendorMemo: String? = null,
    var cancelledAt: Instant? = null,
    var cancelledBy: String? = null, // "USER" | "VENDOR" | "ADMIN"
    var cancelReason: String? = null,

    @CreatedDate
    @Column(updatable = false)
    val createdAt: Instant? = null,

    @LastModifiedDate
    var updatedAt: Instant? = null
)
```

- [ ] **Step 2: Repository (비관적 락 포함)**

```kotlin
// src/main/kotlin/.../reservation/repository/ReservationRepository.kt
package org.flab.ensembleroomreservationproject.reservation.repository

import jakarta.persistence.LockModeType
import org.flab.ensembleroomreservationproject.reservation.entity.Reservation
import org.flab.ensembleroomreservationproject.reservation.entity.ReservationStatus
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

interface ReservationRepository : JpaRepository<Reservation, UUID> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
        SELECT r FROM Reservation r
        WHERE r.room.id = :roomId
          AND r.date = :date
          AND r.startTime < :endTime
          AND r.endTime > :startTime
          AND r.status IN (:statuses)
    """)
    fun findConflictingReservations(
        roomId: UUID,
        date: LocalDate,
        startTime: LocalTime,
        endTime: LocalTime,
        statuses: List<ReservationStatus>
    ): List<Reservation>

    fun findByUserId(userId: UUID, pageable: Pageable): Page<Reservation>

    @Query("""
        SELECT r FROM Reservation r
        WHERE r.room.id = :roomId
          AND r.date = :date
          AND r.status IN (:statuses)
    """)
    fun findByRoomIdAndDate(
        roomId: UUID,
        date: LocalDate,
        statuses: List<ReservationStatus>
    ): List<Reservation>
}
```

- [ ] **Step 3: 예약번호 생성기 테스트 작성 (실패)**

```kotlin
// src/test/kotlin/.../reservation/service/ReservationNumberGeneratorTest.kt
package org.flab.ensembleroomreservationproject.reservation.service

import org.junit.jupiter.api.Test
import java.time.LocalDate
import kotlin.test.assertTrue

class ReservationNumberGeneratorTest {

    @Test
    fun `예약번호 형식 검증`() {
        val generator = ReservationNumberGenerator()
        val number = generator.generate(LocalDate.of(2026, 3, 15))
        assertTrue(number.matches(Regex("R-20260315-[A-Z0-9]{4}")))
    }

    @Test
    fun `예약번호 고유성 검증`() {
        val generator = ReservationNumberGenerator()
        val date = LocalDate.of(2026, 3, 15)
        val numbers = (1..100).map { generator.generate(date) }.toSet()
        assertTrue(numbers.size >= 95) // 충돌 가능성 매우 낮음
    }
}
```

Run: `./gradlew test --tests "*.reservation.service.ReservationNumberGeneratorTest"`
Expected: FAIL

- [ ] **Step 4: 예약번호 생성기 구현**

```kotlin
// src/main/kotlin/.../reservation/service/ReservationNumberGenerator.kt
package org.flab.ensembleroomreservationproject.reservation.service

import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Component
class ReservationNumberGenerator {

    private val dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd")
    private val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"

    fun generate(date: LocalDate): String {
        val datePart = date.format(dateFormatter)
        val randomPart = (1..4).map { chars.random() }.joinToString("")
        return "R-$datePart-$randomPart"
    }
}
```

- [ ] **Step 5: 예약번호 생성기 테스트 통과 확인**

Run: `./gradlew test --tests "*.reservation.service.ReservationNumberGeneratorTest"`
Expected: PASS (2 tests)

- [ ] **Step 6: DTO**

```kotlin
// src/main/kotlin/.../reservation/dto/ReservationCreateRequest.kt
package org.flab.ensembleroomreservationproject.reservation.dto

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

data class ReservationCreateRequest(
    val userId: UUID,
    val roomId: UUID,
    @field:NotNull(message = "예약 날짜는 필수입니다")
    val date: LocalDate,
    @field:NotNull(message = "시작 시간은 필수입니다")
    val startTime: LocalTime,
    @field:Min(1, message = "예약 시간은 1시간 이상이어야 합니다")
    val durationHours: Int,
    val userMemo: String? = null
)
```

```kotlin
// src/main/kotlin/.../reservation/dto/ReservationResponse.kt
package org.flab.ensembleroomreservationproject.reservation.dto

import org.flab.ensembleroomreservationproject.reservation.entity.Reservation
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

data class ReservationResponse(
    val id: UUID,
    val reservationNumber: String,
    val userId: UUID,
    val roomId: UUID,
    val vendorId: UUID,
    val date: LocalDate,
    val startTime: LocalTime,
    val endTime: LocalTime,
    val durationHours: Int,
    val totalPrice: Int,
    val status: String,
    val userMemo: String?
) {
    companion object {
        fun from(reservation: Reservation) = ReservationResponse(
            id = reservation.id!!,
            reservationNumber = reservation.reservationNumber,
            userId = reservation.user.id!!,
            roomId = reservation.room.id!!,
            vendorId = reservation.vendor.id!!,
            date = reservation.date,
            startTime = reservation.startTime,
            endTime = reservation.endTime,
            durationHours = reservation.durationHours,
            totalPrice = reservation.totalPrice,
            status = reservation.status.name,
            userMemo = reservation.userMemo
        )
    }
}
```

```kotlin
// src/main/kotlin/.../reservation/dto/CancelRequest.kt
package org.flab.ensembleroomreservationproject.reservation.dto

data class CancelRequest(
    val cancelledBy: String, // "USER" | "VENDOR" | "ADMIN"
    val cancelReason: String? = null
)
```

- [ ] **Step 7: Service 테스트 작성 (실패)**

```kotlin
// src/test/kotlin/.../reservation/service/ReservationServiceTest.kt
package org.flab.ensembleroomreservationproject.reservation.service

import org.flab.ensembleroomreservationproject.common.exception.ConflictException
import org.flab.ensembleroomreservationproject.reservation.dto.CancelRequest
import org.flab.ensembleroomreservationproject.reservation.dto.ReservationCreateRequest
import org.flab.ensembleroomreservationproject.reservation.entity.ReservationStatus
import org.flab.ensembleroomreservationproject.reservation.repository.ReservationRepository
import org.flab.ensembleroomreservationproject.room.entity.Room
import org.flab.ensembleroomreservationproject.room.repository.RoomRepository
import org.flab.ensembleroomreservationproject.support.TestContainersConfig
import org.flab.ensembleroomreservationproject.user.entity.User
import org.flab.ensembleroomreservationproject.user.repository.UserRepository
import org.flab.ensembleroomreservationproject.vendor.entity.Vendor
import org.flab.ensembleroomreservationproject.vendor.repository.VendorRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.data.domain.PageRequest
import java.time.LocalDate
import java.time.LocalTime
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

@SpringBootTest
@Import(TestContainersConfig::class)
class ReservationServiceTest {

    @Autowired lateinit var reservationService: ReservationService
    @Autowired lateinit var reservationRepository: ReservationRepository
    @Autowired lateinit var roomRepository: RoomRepository
    @Autowired lateinit var vendorRepository: VendorRepository
    @Autowired lateinit var userRepository: UserRepository

    lateinit var user: User
    lateinit var room: Room

    @BeforeEach
    fun setUp() {
        reservationRepository.deleteAll()
        roomRepository.deleteAll()
        vendorRepository.deleteAll()
        userRepository.deleteAll()
        user = userRepository.save(User(tossUserId = "user_1", nickname = "유저"))
        val owner = userRepository.save(User(tossUserId = "owner_1", nickname = "사장님"))
        val vendor = vendorRepository.save(
            Vendor(owner = owner, name = "스튜디오", phone = "02-0000", address = "서울", businessNumber = "111-11-11111")
        )
        room = roomRepository.save(Room(vendor = vendor, name = "A룸", capacity = 5, hourlyPrice = 15000))
    }

    @Test
    fun `예약 생성`() {
        val request = ReservationCreateRequest(
            userId = user.id!!, roomId = room.id!!,
            date = LocalDate.of(2026, 3, 16),
            startTime = LocalTime.of(14, 0),
            durationHours = 2
        )
        val result = reservationService.createReservation(request)
        assertEquals("PENDING", result.status)
        assertEquals(30000, result.totalPrice) // 15000 * 2시간
        assertEquals(LocalTime.of(16, 0), result.endTime)
        assertEquals(true, result.reservationNumber.startsWith("R-20260316-"))
    }

    @Test
    fun `중복 예약 방지`() {
        val request = ReservationCreateRequest(
            userId = user.id!!, roomId = room.id!!,
            date = LocalDate.of(2026, 3, 16),
            startTime = LocalTime.of(14, 0),
            durationHours = 2
        )
        reservationService.createReservation(request)

        // 겹치는 시간대로 다시 예약 시도
        val overlapping = ReservationCreateRequest(
            userId = user.id!!, roomId = room.id!!,
            date = LocalDate.of(2026, 3, 16),
            startTime = LocalTime.of(15, 0),
            durationHours = 2
        )
        assertFailsWith<ConflictException> {
            reservationService.createReservation(overlapping)
        }
    }

    @Test
    fun `내 예약 목록 조회`() {
        val request = ReservationCreateRequest(
            userId = user.id!!, roomId = room.id!!,
            date = LocalDate.of(2026, 3, 16),
            startTime = LocalTime.of(14, 0),
            durationHours = 2
        )
        reservationService.createReservation(request)
        val list = reservationService.getUserReservations(user.id!!, PageRequest.of(0, 20))
        assertEquals(1, list.totalElements)
    }

    @Test
    fun `예약 취소`() {
        val request = ReservationCreateRequest(
            userId = user.id!!, roomId = room.id!!,
            date = LocalDate.of(2026, 3, 16),
            startTime = LocalTime.of(14, 0),
            durationHours = 2
        )
        val created = reservationService.createReservation(request)
        val cancelled = reservationService.cancelReservation(
            created.id, CancelRequest(cancelledBy = "USER", cancelReason = "일정 변경")
        )
        assertEquals("CANCELLED", cancelled.status)
    }
}
```

Run: `./gradlew test --tests "*.reservation.service.ReservationServiceTest"`
Expected: FAIL (ReservationService 없음)

- [ ] **Step 8: Service 구현**

```kotlin
// src/main/kotlin/.../reservation/service/ReservationService.kt
package org.flab.ensembleroomreservationproject.reservation.service

import org.flab.ensembleroomreservationproject.common.exception.BadRequestException
import org.flab.ensembleroomreservationproject.common.exception.ConflictException
import org.flab.ensembleroomreservationproject.common.exception.NotFoundException
import org.flab.ensembleroomreservationproject.reservation.dto.CancelRequest
import org.flab.ensembleroomreservationproject.reservation.dto.ReservationCreateRequest
import org.flab.ensembleroomreservationproject.reservation.dto.ReservationResponse
import org.flab.ensembleroomreservationproject.reservation.entity.Reservation
import org.flab.ensembleroomreservationproject.reservation.entity.ReservationStatus
import org.flab.ensembleroomreservationproject.reservation.repository.ReservationRepository
import org.flab.ensembleroomreservationproject.room.repository.RoomRepository
import org.flab.ensembleroomreservationproject.user.repository.UserRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.util.*

@Service
@Transactional(readOnly = true)
class ReservationService(
    private val reservationRepository: ReservationRepository,
    private val roomRepository: RoomRepository,
    private val userRepository: UserRepository,
    private val reservationNumberGenerator: ReservationNumberGenerator
) {
    @Transactional
    fun createReservation(request: ReservationCreateRequest): ReservationResponse {
        val user = userRepository.findById(request.userId)
            .orElseThrow { NotFoundException("유저를 찾을 수 없습니다: ${request.userId}") }
        val room = roomRepository.findById(request.roomId)
            .orElseThrow { NotFoundException("룸을 찾을 수 없습니다: ${request.roomId}") }

        if (request.durationHours < room.minHours || request.durationHours > room.maxHours) {
            throw BadRequestException("예약 시간은 ${room.minHours}~${room.maxHours}시간이어야 합니다")
        }

        val endTime = request.startTime.plusHours(request.durationHours.toLong())

        // 비관적 락으로 중복 체크
        val activeStatuses = listOf(ReservationStatus.PENDING, ReservationStatus.CONFIRMED)
        val conflicts = reservationRepository.findConflictingReservations(
            roomId = room.id!!,
            date = request.date,
            startTime = request.startTime,
            endTime = endTime,
            statuses = activeStatuses
        )
        if (conflicts.isNotEmpty()) {
            throw ConflictException("해당 시간대에 이미 예약이 존재합니다")
        }

        val reservation = reservationRepository.save(
            Reservation(
                reservationNumber = reservationNumberGenerator.generate(request.date),
                user = user,
                room = room,
                vendor = room.vendor,
                date = request.date,
                startTime = request.startTime,
                endTime = endTime,
                durationHours = request.durationHours,
                totalPrice = room.hourlyPrice * request.durationHours,
                userMemo = request.userMemo
            )
        )
        return ReservationResponse.from(reservation)
    }

    fun getUserReservations(userId: UUID, pageable: Pageable): Page<ReservationResponse> =
        reservationRepository.findByUserId(userId, pageable)
            .map { ReservationResponse.from(it) }

    @Transactional
    fun cancelReservation(id: UUID, request: CancelRequest): ReservationResponse {
        val reservation = reservationRepository.findById(id)
            .orElseThrow { NotFoundException("예약을 찾을 수 없습니다: $id") }

        if (reservation.status == ReservationStatus.CANCELLED) {
            throw BadRequestException("이미 취소된 예약입니다")
        }

        reservation.status = ReservationStatus.CANCELLED
        reservation.cancelledAt = Instant.now()
        reservation.cancelledBy = request.cancelledBy
        reservation.cancelReason = request.cancelReason

        return ReservationResponse.from(reservation)
    }

    @Transactional
    fun updateStatus(id: UUID, status: ReservationStatus): ReservationResponse {
        val reservation = reservationRepository.findById(id)
            .orElseThrow { NotFoundException("예약을 찾을 수 없습니다: $id") }
        reservation.status = status
        return ReservationResponse.from(reservation)
    }
}
```

- [ ] **Step 9: Service 테스트 통과 확인**

Run: `./gradlew test --tests "*.reservation.service.ReservationServiceTest"`
Expected: PASS (4 tests)

- [ ] **Step 10: Controller 테스트 작성 (실패)**

```kotlin
// src/test/kotlin/.../reservation/controller/ReservationControllerTest.kt
package org.flab.ensembleroomreservationproject.reservation.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.flab.ensembleroomreservationproject.reservation.repository.ReservationRepository
import org.flab.ensembleroomreservationproject.room.entity.Room
import org.flab.ensembleroomreservationproject.room.repository.RoomRepository
import org.flab.ensembleroomreservationproject.support.TestContainersConfig
import org.flab.ensembleroomreservationproject.user.entity.User
import org.flab.ensembleroomreservationproject.user.repository.UserRepository
import org.flab.ensembleroomreservationproject.vendor.entity.Vendor
import org.flab.ensembleroomreservationproject.vendor.repository.VendorRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestContainersConfig::class)
class ReservationControllerTest {

    @Autowired lateinit var mockMvc: MockMvc
    @Autowired lateinit var objectMapper: ObjectMapper
    @Autowired lateinit var reservationRepository: ReservationRepository
    @Autowired lateinit var roomRepository: RoomRepository
    @Autowired lateinit var vendorRepository: VendorRepository
    @Autowired lateinit var userRepository: UserRepository

    lateinit var user: User
    lateinit var room: Room

    @BeforeEach
    fun setUp() {
        reservationRepository.deleteAll()
        roomRepository.deleteAll()
        vendorRepository.deleteAll()
        userRepository.deleteAll()
        user = userRepository.save(User(tossUserId = "user_1", nickname = "유저"))
        val owner = userRepository.save(User(tossUserId = "owner_1", nickname = "사장님"))
        val vendor = vendorRepository.save(
            Vendor(owner = owner, name = "스튜디오", phone = "02-0000", address = "서울", businessNumber = "111-11-11111")
        )
        room = roomRepository.save(Room(vendor = vendor, name = "A룸", capacity = 5, hourlyPrice = 15000))
    }

    @Test
    fun `POST api v1 reservations - 예약 생성`() {
        mockMvc.post("/api/v1/reservations") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(mapOf(
                "user_id" to user.id,
                "room_id" to room.id,
                "date" to "2026-03-16",
                "start_time" to "14:00",
                "duration_hours" to 2
            ))
        }.andExpect {
            status { isOk() }
            jsonPath("$.data.status") { value("PENDING") }
            jsonPath("$.data.total_price") { value(30000) }
        }
    }

    @Test
    fun `GET api v1 reservations - 내 예약 목록`() {
        // 먼저 예약 생성
        mockMvc.post("/api/v1/reservations") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(mapOf(
                "user_id" to user.id,
                "room_id" to room.id,
                "date" to "2026-03-16",
                "start_time" to "14:00",
                "duration_hours" to 2
            ))
        }

        mockMvc.get("/api/v1/reservations?userId=${user.id}")
            .andExpect {
                status { isOk() }
                jsonPath("$.data.content.length()") { value(1) }
            }
    }

    @Test
    fun `POST api v1 reservations id cancel - 예약 취소`() {
        val result = mockMvc.post("/api/v1/reservations") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(mapOf(
                "user_id" to user.id,
                "room_id" to room.id,
                "date" to "2026-03-16",
                "start_time" to "14:00",
                "duration_hours" to 2
            ))
        }.andReturn()

        val reservationId = objectMapper.readTree(result.response.contentAsString)
            .at("/data/id").asText()

        mockMvc.post("/api/v1/reservations/$reservationId/cancel") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(mapOf(
                "cancelled_by" to "USER",
                "cancel_reason" to "일정 변경"
            ))
        }.andExpect {
            status { isOk() }
            jsonPath("$.data.status") { value("CANCELLED") }
        }
    }
}
```

Run: `./gradlew test --tests "*.reservation.controller.ReservationControllerTest"`
Expected: FAIL (ReservationController 없음)

- [ ] **Step 11: Controller 구현**

```kotlin
// src/main/kotlin/.../reservation/controller/ReservationController.kt
package org.flab.ensembleroomreservationproject.reservation.controller

import jakarta.validation.Valid
import org.flab.ensembleroomreservationproject.common.dto.ApiResponse
import org.flab.ensembleroomreservationproject.reservation.dto.CancelRequest
import org.flab.ensembleroomreservationproject.reservation.dto.ReservationCreateRequest
import org.flab.ensembleroomreservationproject.reservation.dto.ReservationResponse
import org.flab.ensembleroomreservationproject.reservation.entity.ReservationStatus
import org.flab.ensembleroomreservationproject.reservation.service.ReservationService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/reservations")
class ReservationController(
    private val reservationService: ReservationService
) {
    @PostMapping
    fun createReservation(@Valid @RequestBody request: ReservationCreateRequest): ApiResponse<ReservationResponse> =
        ApiResponse.ok(reservationService.createReservation(request))

    @GetMapping
    fun getUserReservations(
        @RequestParam userId: UUID,
        @PageableDefault(size = 20) pageable: Pageable
    ): ApiResponse<Page<ReservationResponse>> =
        ApiResponse.ok(reservationService.getUserReservations(userId, pageable))

    @PostMapping("/{id}/cancel")
    fun cancelReservation(
        @PathVariable id: UUID,
        @RequestBody request: CancelRequest
    ): ApiResponse<ReservationResponse> =
        ApiResponse.ok(reservationService.cancelReservation(id, request))

    @PatchMapping("/{id}/status")
    fun updateStatus(
        @PathVariable id: UUID,
        @RequestParam status: ReservationStatus
    ): ApiResponse<ReservationResponse> =
        ApiResponse.ok(reservationService.updateStatus(id, status))
}
```

- [ ] **Step 12: 전체 Reservation 테스트 통과 확인**

Run: `./gradlew test --tests "*.reservation.*"`
Expected: PASS (9 tests)

- [ ] **Step 13: 커밋**

```bash
git add -A
git commit -m "Reservation 도메인: 예약 생성/조회/취소, 중복 방지(비관적 락), 예약번호 생성 + 테스트"
```

---

## Task 7: 예약 가능 시간 조회에 기존 예약 반영 + 전체 통합 테스트

**Files:**
- Modify: `src/main/kotlin/.../room/service/RoomService.kt` — getAvailability()에 예약 필터링 추가
- Modify: `src/test/kotlin/.../room/service/RoomServiceTest.kt` — 예약 반영 테스트 추가

- [ ] **Step 1: RoomService.getAvailability() 수정 — 예약된 슬롯 제외**

RoomService에 ReservationRepository 의존성 추가하고 getAvailability() 메서드를 수정:

```kotlin
// RoomService 생성자에 추가
private val reservationRepository: ReservationRepository

// getAvailability() 메서드 내부, slots 생성 후 반환 전에 추가:
val existingReservations = reservationRepository.findByRoomIdAndDate(roomId, date)
// 기존 예약이 차지하는 시간대 체크하여 available = false로 변경
```

슬롯 생성 로직에서 예약 충돌 여부를 체크:

```kotlin
val reservedSlots = existingReservations.flatMap { r ->
    generateSequence(r.startTime) { it.plusHours(1) }
        .takeWhile { it.isBefore(r.endTime) }
        .toList()
}.toSet()

// slots 생성 시:
val isReserved = reservedSlots.contains(current)
slots.add(TimeSlot(
    start = current.toString(),
    end = next.toString(),
    available = !blocked && !isReserved,
    price = price
))
```

- [ ] **Step 2: 테스트 추가**

```kotlin
// RoomServiceTest에 추가
@Autowired lateinit var reservationRepository: ReservationRepository

// BeforeEach에 추가
reservationRepository.deleteAll()

@Test
fun `예약 가능 시간 조회 - 기존 예약 반영`() {
    val room = roomService.createRoom(vendor.id!!, RoomCreateRequest(name = "A룸", capacity = 5, hourlyPrice = 15000))
    val testUser = userRepository.save(User(tossUserId = "test_user", nickname = "테스트유저"))

    // 14:00~16:00 예약 생성
    reservationService.createReservation(ReservationCreateRequest(
        userId = testUser.id!!, roomId = room.id,
        date = LocalDate.of(2026, 3, 16), startTime = LocalTime.of(14, 0), durationHours = 2
    ))

    val availability = roomService.getAvailability(room.id, LocalDate.of(2026, 3, 16))
    val slot14 = availability.slots.find { it.start == "14:00" }!!
    val slot15 = availability.slots.find { it.start == "15:00" }!!
    val slot13 = availability.slots.find { it.start == "13:00" }!!

    assertEquals(false, slot14.available)
    assertEquals(false, slot15.available)
    assertEquals(true, slot13.available)
}
```

- [ ] **Step 3: 수정된 코드 테스트 통과 확인**

Run: `./gradlew test`
Expected: ALL PASS

- [ ] **Step 4: 커밋**

```bash
git add -A
git commit -m "예약 가능 시간 조회에 기존 예약 반영 + 통합 테스트"
```

---

## 요약

| Task | 내용 | 파일 수 | 테스트 |
|------|------|---------|--------|
| 1 | 빌드 설정 + 의존성 | 3 | 빌드 확인 |
| 2 | 테스트 인프라 + 공통 모듈 | 6 | contextLoads |
| 3 | User 도메인 | 9 | 6 tests |
| 4 | Vendor 도메인 | 11 | 7 tests |
| 5 | Room 도메인 | 13 | 6 tests |
| 6 | Reservation 도메인 | 12 | 9 tests |
| 7 | 예약-시간 통합 | 2 (수정) | +1 test |

**총**: ~56 파일, ~29 테스트
