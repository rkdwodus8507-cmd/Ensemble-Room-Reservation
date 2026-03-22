package org.flab.ensembleroomreservationproject.vendor.service

import org.flab.ensembleroomreservationproject.common.exception.NotFoundException
import org.flab.ensembleroomreservationproject.support.TestContainersConfig
import org.flab.ensembleroomreservationproject.user.entity.User
import org.flab.ensembleroomreservationproject.user.repository.UserRepository
import org.flab.ensembleroomreservationproject.vendor.dto.VendorCreateRequest
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
            ownerId = owner.id!!, name = "뮤직스튜디오",
            phone = "02-1234-5678", address = "서울시 강남구",
            businessNumber = "123-45-67890"
        )
        val result = vendorService.createVendor(request)
        assertEquals("뮤직스튜디오", result.name)
        assertEquals("PENDING", result.status)
    }

    @Test
    fun `승인된 업체 목록 조회 - PENDING은 포함 안됨`() {
        vendorService.createVendor(VendorCreateRequest(
            ownerId = owner.id!!, name = "스튜디오A", phone = "02-0000-0000",
            address = "서울", businessNumber = "111-11-11111"
        ))
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
        val created = vendorService.createVendor(VendorCreateRequest(
            ownerId = owner.id!!, name = "스튜디오B", phone = "02-0000-0000",
            address = "서울", businessNumber = "222-22-22222"
        ))
        val approved = vendorService.approveVendor(created.id)
        assertEquals("APPROVED", approved.status)
    }
}
