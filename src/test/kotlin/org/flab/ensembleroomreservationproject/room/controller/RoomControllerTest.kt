package org.flab.ensembleroomreservationproject.room.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.flab.ensembleroomreservationproject.room.repository.RoomRepository
import org.flab.ensembleroomreservationproject.support.DatabaseCleanup
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
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@Import(TestContainersConfig::class)
class RoomControllerTest {

    @Autowired lateinit var webApplicationContext: WebApplicationContext
    @Autowired lateinit var roomRepository: RoomRepository
    @Autowired lateinit var vendorRepository: VendorRepository
    @Autowired lateinit var userRepository: UserRepository
    @Autowired lateinit var databaseCleanup: DatabaseCleanup

    private lateinit var mockMvc: MockMvc
    private val objectMapper = ObjectMapper()
    lateinit var vendor: Vendor

    @BeforeEach
    fun setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build()
        databaseCleanup.execute()
        val owner = userRepository.save(User(tossUserId = "owner_1", nickname = "사장님"))
        vendor = vendorRepository.save(
            Vendor(owner = owner, name = "스튜디오", phone = "02-0000", address = "서울", businessNumber = "111-11-11111")
        )
    }

    @Test
    fun `POST api v1 vendors vendorId rooms - 룸 생성`() {
        mockMvc.post("/api/v1/vendors/${vendor.id}/rooms") {
            contentType = MediaType.APPLICATION_JSON
            content = """{"name":"A룸","capacity":5,"hourly_price":15000,"min_hours":1,"max_hours":4}"""
        }.andExpect {
            status { isOk() }
            jsonPath("$.data.name") { value("A룸") }
        }
    }

    @Test
    fun `GET api v1 vendors vendorId rooms - 룸 목록`() {
        mockMvc.post("/api/v1/vendors/${vendor.id}/rooms") {
            contentType = MediaType.APPLICATION_JSON
            content = """{"name":"A룸","capacity":5,"hourly_price":15000,"min_hours":1,"max_hours":4}"""
        }
        mockMvc.get("/api/v1/vendors/${vendor.id}/rooms")
            .andExpect {
                status { isOk() }
                jsonPath("$.data.length()") { value(1) }
            }
    }
}
