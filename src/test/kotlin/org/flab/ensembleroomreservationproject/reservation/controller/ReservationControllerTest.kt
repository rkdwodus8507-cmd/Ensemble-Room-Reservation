package org.flab.ensembleroomreservationproject.reservation.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.flab.ensembleroomreservationproject.reservation.repository.ReservationRepository
import org.flab.ensembleroomreservationproject.room.entity.Room
import org.flab.ensembleroomreservationproject.room.repository.RoomRepository
import org.flab.ensembleroomreservationproject.support.DatabaseCleanup
import org.flab.ensembleroomreservationproject.support.TestContainersConfig
import org.flab.ensembleroomreservationproject.user.entity.User
import org.flab.ensembleroomreservationproject.user.repository.UserRepository
import org.flab.ensembleroomreservationproject.vendor.entity.Vendor
import org.flab.ensembleroomreservationproject.vendor.entity.VendorStatus
import org.flab.ensembleroomreservationproject.vendor.repository.VendorRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@Import(TestContainersConfig::class)
class ReservationControllerTest {

    @Autowired lateinit var webApplicationContext: WebApplicationContext
    @Autowired lateinit var reservationRepository: ReservationRepository
    @Autowired lateinit var roomRepository: RoomRepository
    @Autowired lateinit var vendorRepository: VendorRepository
    @Autowired lateinit var userRepository: UserRepository
    @Autowired lateinit var databaseCleanup: DatabaseCleanup

    private lateinit var mockMvc: MockMvc
    private val objectMapper = ObjectMapper()
    lateinit var user: User
    lateinit var room: Room

    @BeforeEach
    fun setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build()
        databaseCleanup.execute()
        user = userRepository.save(User(tossUserId = "user_1", nickname = "유저"))
        val owner = userRepository.save(User(tossUserId = "owner_1", nickname = "사장님"))
        val vendor = vendorRepository.save(
            Vendor(owner = owner, name = "스튜디오", phone = "02-0000", address = "서울", businessNumber = "111-11-11111", status = VendorStatus.APPROVED)
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
                "date" to "2026-04-06",
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
        mockMvc.post("/api/v1/reservations") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(mapOf(
                "user_id" to user.id,
                "room_id" to room.id,
                "date" to "2026-04-06",
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
                "date" to "2026-04-06",
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
