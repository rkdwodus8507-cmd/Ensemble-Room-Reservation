package org.flab.ensembleroomreservationproject.reservation.service

import org.flab.ensembleroomreservationproject.common.exception.ConflictException
import org.flab.ensembleroomreservationproject.reservation.dto.CancelRequest
import org.flab.ensembleroomreservationproject.reservation.dto.ReservationCreateRequest
import org.flab.ensembleroomreservationproject.reservation.repository.ReservationRepository
import org.flab.ensembleroomreservationproject.room.entity.Room
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
    @Autowired lateinit var databaseCleanup: DatabaseCleanup

    lateinit var user: User
    lateinit var room: Room

    @BeforeEach
    fun setUp() {
        databaseCleanup.execute()
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
        assertEquals(30000, result.totalPrice)
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
