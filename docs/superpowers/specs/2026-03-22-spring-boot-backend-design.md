# 합주실 예약 시스템 — Spring Boot 백엔드 설계

## 개요

앱인토스(토스 미니앱) 합주실 예약 플랫폼의 백엔드 API 서버.
원본 Supabase 기반 설계를 Spring Boot + Kotlin으로 전환한 설계.

### 범위 (MVP)

- **포함**: user, vendor, room, reservation 도메인 (1~3단계)
- **제외**: 결제(토스페이먼츠 SDK 대기), 인증(토스 로그인 SDK 대기), 리뷰, 찜, 알림, 검색

## 1. 기술 스택

| 영역 | 기술 |
|------|------|
| 프레임워크 | Spring Boot 4.0.3 + Kotlin 2.2.21 |
| DB | PostgreSQL 15+ |
| ORM | Spring Data JPA + Hibernate 7 |
| DB 마이그레이션 | Flyway (추후 설정) |
| 검증 | Spring Validation (Jakarta) |
| 빌드 | Gradle Kotlin DSL, Java 21 |
| 테스트 DB | Testcontainers (PostgreSQL) |
| 인증 | 추후 구현 (토스 SDK 대기) |
| 결제 | 추후 구현 (토스페이먼츠 대기) |

## 2. 패키지 구조

```
org.flab.ensembleroomreservationproject/
├── user/
│   ├── entity/User.kt
│   ├── repository/UserRepository.kt
│   ├── service/UserService.kt
│   ├── controller/UserController.kt
│   └── dto/
├── vendor/
│   ├── entity/Vendor.kt
│   ├── repository/VendorRepository.kt
│   ├── service/VendorService.kt
│   ├── controller/VendorController.kt, AdminVendorController.kt
│   └── dto/
├── room/
│   ├── entity/Room.kt, TimeSlotOverride.kt
│   ├── repository/RoomRepository.kt, TimeSlotOverrideRepository.kt
│   ├── service/RoomService.kt
│   ├── controller/RoomController.kt
│   └── dto/
├── reservation/
│   ├── entity/Reservation.kt
│   ├── repository/ReservationRepository.kt
│   ├── service/ReservationService.kt
│   ├── controller/ReservationController.kt
│   └── dto/
└── common/
    ├── exception/GlobalExceptionHandler.kt, BusinessException.kt
    └── dto/ApiResponse.kt
```

## 3. Entity 설계

> **공통 사항**:
> - ID는 Hibernate UUID 생성 (`@GeneratedValue(strategy = GenerationType.UUID)`)
> - 감사 필드는 Spring Data JPA Auditing 사용 (`@CreatedDate`, `@LastModifiedDate` + `@EnableJpaAuditing`)
> - `kotlin("plugin.jpa")` 플러그인으로 no-arg 생성자 자동 생성
> - JSONB 매핑: Jackson이 classpath에 있어야 함 (spring-boot-starter-web이 제공)
> - Reservation.vendor는 의도적 비정규화 (쿼리 편의, 서비스 레이어에서 room.vendor == vendor 일관성 보장)

### 3.1 User

```kotlin
@Entity
@Table(name = "users")
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
    val createdAt: Instant? = null,

    @LastModifiedDate
    var updatedAt: Instant? = null
)

enum class UserRole { USER, VENDOR, ADMIN }
```

### 3.2 Vendor

```kotlin
@Entity
@Table(name = "vendors")
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
    var operatingHours: Map<String, OperatingHour> = mapOf(
        "mon" to OperatingHour("09:00", "23:00"),
        "tue" to OperatingHour("09:00", "23:00"),
        "wed" to OperatingHour("09:00", "23:00"),
        "thu" to OperatingHour("09:00", "23:00"),
        "fri" to OperatingHour("09:00", "23:00"),
        "sat" to OperatingHour("10:00", "24:00"),
        "sun" to OperatingHour("10:00", "22:00")
    ),

    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(columnDefinition = "text[]")
    var amenities: List<String> = emptyList(),

    var thumbnailUrl: String? = null,

    @CreatedDate
    val createdAt: Instant? = null,

    @LastModifiedDate
    var updatedAt: Instant? = null
)

enum class VendorStatus { PENDING, APPROVED, REJECTED, SUSPENDED }

data class OperatingHour(
    val open: String,
    val close: String,
    val closed: Boolean = false
)
```

### 3.3 Room

```kotlin
@Entity
@Table(name = "rooms")
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
    val createdAt: Instant? = null,

    @LastModifiedDate
    var updatedAt: Instant? = null
)

data class Equipment(
    val name: String,
    val brand: String? = null
)
```

### 3.4 TimeSlotOverride

```kotlin
@Entity
@Table(
    name = "time_slot_overrides",
    uniqueConstraints = [UniqueConstraint(columnNames = ["room_id", "date", "start_time"])]
)
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
    val createdAt: Instant? = null
)
```

### 3.5 Reservation

```kotlin
@Entity
@Table(name = "reservations")
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
    val createdAt: Instant? = null,

    @LastModifiedDate
    var updatedAt: Instant? = null
)

enum class ReservationStatus {
    PENDING, CONFIRMED, CANCELLED, COMPLETED, NO_SHOW
}
```

## 4. API 엔드포인트

### 4.1 유저

| 메서드 | 경로 | 설명 |
|--------|------|------|
| GET | `/api/v1/users/{id}` | 프로필 조회 |
| PATCH | `/api/v1/users/{id}` | 프로필 수정 |

### 4.2 업체

| 메서드 | 경로 | 설명 | 권한 |
|--------|------|------|------|
| POST | `/api/v1/vendors` | 업체 등록 신청 | user |
| GET | `/api/v1/vendors?page=0&size=20` | 승인된 업체 목록 (페이지네이션) | public |
| GET | `/api/v1/vendors/{id}` | 업체 상세 | public |
| PATCH | `/api/v1/vendors/{id}` | 업체 정보 수정 | vendor(owner) |
| POST | `/api/v1/admin/vendors/{id}/approve` | 업체 승인 | admin |
| POST | `/api/v1/admin/vendors/{id}/reject` | 업체 거절 | admin |

### 4.3 룸

| 메서드 | 경로 | 설명 | 권한 |
|--------|------|------|------|
| POST | `/api/v1/vendors/{vendorId}/rooms` | 룸 생성 | vendor |
| GET | `/api/v1/vendors/{vendorId}/rooms` | 업체별 룸 목록 | public |
| PATCH | `/api/v1/rooms/{id}` | 룸 수정 | vendor(owner) |
| DELETE | `/api/v1/rooms/{id}` | 룸 삭제 (soft) | vendor(owner) |

### 4.4 예약

| 메서드 | 경로 | 설명 | 권한 |
|--------|------|------|------|
| GET | `/api/v1/rooms/{roomId}/availability?date=YYYY-MM-DD` | 예약 가능 시간 조회 | public |
| POST | `/api/v1/reservations` | 예약 생성 | user |
| GET | `/api/v1/reservations?userId={id}&page=0&size=20` | 내 예약 목록 (페이지네이션) | user |
| POST | `/api/v1/reservations/{id}/cancel` | 예약 취소 | user/vendor |
| PATCH | `/api/v1/reservations/{id}/status` | 예약 상태 변경 | vendor/admin |

## 5. 핵심 비즈니스 로직

### 5.1 예약 충돌 방지

JPA 비관적 락으로 동시 예약 방지:

```kotlin
@Lock(LockModeType.PESSIMISTIC_WRITE)
@Query("""
    SELECT r FROM Reservation r
    WHERE r.room.id = :roomId
      AND r.date = :date
      AND r.startTime < :endTime
      AND r.endTime > :startTime
      AND r.status IN (
        org.flab.ensembleroomreservationproject.reservation.entity.ReservationStatus.PENDING,
        org.flab.ensembleroomreservationproject.reservation.entity.ReservationStatus.CONFIRMED
      )
""")
fun findConflictingReservations(
    roomId: UUID, date: LocalDate, startTime: LocalTime, endTime: LocalTime
): List<Reservation>
```

### 5.2 취소/환불 정책

```
- 24시간 전: 전액 환불
- 12~24시간 전: 50% 환불
- 12시간 이내: 환불 불가
- 업체 취소: 항상 전액 환불
```

`ReservationService.cancel()`에서 정책 판단, 실제 환불은 결제 연동 후.

### 5.3 예약번호 생성

형식: `R-{YYYYMMDD}-{4자리 영숫자 랜덤}`
예: `R-20260315-A7K2`

### 5.4 예약 가능 시간 조회

1. Room의 vendor에서 해당 요일 운영시간 조회
2. 1시간 단위 슬롯 생성
3. TimeSlotOverride에서 특별 가격/차단 반영
4. 기존 예약(PENDING/CONFIRMED)에서 예약된 슬롯 제외
5. 슬롯별 available, price 반환

## 6. 공통 응답 형식

```kotlin
data class ApiResponse<T>(
    val success: Boolean,
    val data: T? = null,
    val error: String? = null
)
```

## 7. 의존성 (build.gradle.kts 추가분)

```kotlin
plugins {
    // 기존
    kotlin("jvm")
    kotlin("plugin.spring")
    // 추가 필수
    kotlin("plugin.jpa") version "2.2.21"  // JPA Entity no-arg 생성자
}

dependencies {
    // 기존
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    // 추가
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    runtimeOnly("org.postgresql:postgresql")

    // 테스트
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("org.testcontainers:postgresql")
    testImplementation("org.testcontainers:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}
```

## 8. application.yml 설정

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
      ddl-auto: validate
    properties:
      hibernate:
        format_sql: true
    open-in-view: false

  jackson:
    property-naming-strategy: SNAKE_CASE

server:
  port: 8080
```

## 9. 제외 사항 (향후 구현)

- VendorImage 엔티티 (업체 갤러리)
- Payment 엔티티 + 토스페이먼츠 연동
- Review, Favorite, Notification 도메인
- 토스 로그인 인증 + Spring Security
- 위치 기반 검색 (PostGIS)
- 업체 대시보드/통계 API
- Supabase Storage → 자체 파일 저장소 (S3 등)