package org.flab.ensembleroomreservationproject.user.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.flab.ensembleroomreservationproject.support.TestContainersConfig
import org.flab.ensembleroomreservationproject.user.entity.User
import org.flab.ensembleroomreservationproject.user.repository.UserRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.patch
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@Import(TestContainersConfig::class)
class UserControllerTest {

    @Autowired lateinit var webApplicationContext: WebApplicationContext
    @Autowired lateinit var userRepository: UserRepository

    private lateinit var mockMvc: MockMvc
    private val objectMapper = ObjectMapper()

    @BeforeEach
    fun setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build()
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
