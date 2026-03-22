package org.flab.ensembleroomreservationproject.user.service

import org.flab.ensembleroomreservationproject.common.exception.NotFoundException
import org.flab.ensembleroomreservationproject.support.TestContainersConfig
import org.flab.ensembleroomreservationproject.user.entity.User
import org.flab.ensembleroomreservationproject.user.repository.UserRepository
import org.flab.ensembleroomreservationproject.user.dto.UserUpdateRequest
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

    @Autowired lateinit var userService: UserService
    @Autowired lateinit var userRepository: UserRepository

    @BeforeEach
    fun setUp() { userRepository.deleteAll() }

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
            UserUpdateRequest(nickname = "새이름", phone = "010-1234-5678", profileImageUrl = null)
        )
        assertEquals("새이름", result.nickname)
        assertEquals("010-1234-5678", result.phone)
    }
}
