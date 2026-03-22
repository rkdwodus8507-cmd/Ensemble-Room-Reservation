package org.flab.ensembleroomreservationproject.vendor.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.flab.ensembleroomreservationproject.support.DatabaseCleanup
import org.flab.ensembleroomreservationproject.support.TestContainersConfig
import org.flab.ensembleroomreservationproject.user.entity.User
import org.flab.ensembleroomreservationproject.user.repository.UserRepository
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
class VendorControllerTest {

    @Autowired lateinit var webApplicationContext: WebApplicationContext
    @Autowired lateinit var userRepository: UserRepository
    @Autowired lateinit var vendorRepository: VendorRepository
    @Autowired lateinit var databaseCleanup: DatabaseCleanup

    private lateinit var mockMvc: MockMvc
    private val objectMapper = ObjectMapper()
    lateinit var owner: User

    @BeforeEach
    fun setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build()
        databaseCleanup.execute()
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
    fun `GET api v1 vendors - 승인된 업체 목록`() {
        mockMvc.get("/api/v1/vendors")
            .andExpect {
                status { isOk() }
                jsonPath("$.success") { value(true) }
            }
    }

    @Test
    fun `POST api v1 admin vendors approve - 업체 승인`() {
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
