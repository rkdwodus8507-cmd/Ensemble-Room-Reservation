package org.flab.ensembleroomreservationproject.room.service

import org.flab.ensembleroomreservationproject.reservation.dto.ReservationCreateRequest
import org.flab.ensembleroomreservationproject.reservation.service.ReservationService
import org.flab.ensembleroomreservationproject.room.dto.RoomCreateRequest
import org.flab.ensembleroomreservationproject.room.entity.Equipment
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
import java.time.LocalDate
import java.time.LocalTime
import kotlin.test.assertEquals

@SpringBootTest
@Import(TestContainersConfig::class)
class RoomServiceTest {

    @Autowired lateinit var roomService: RoomService
    @Autowired lateinit var reservationService: ReservationService
    @Autowired lateinit var roomRepository: RoomRepository
    @Autowired lateinit var vendorRepository: VendorRepository
    @Autowired lateinit var userRepository: UserRepository
    @Autowired lateinit var databaseCleanup: DatabaseCleanup

    lateinit var vendor: Vendor

    @BeforeEach
    fun setUp() {
        databaseCleanup.execute()
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
    fun `예약 가능 시간 조회 - 월요일 빈 슬롯`() {
        val room = roomService.createRoom(vendor.id!!, RoomCreateRequest(name = "A룸", capacity = 5, hourlyPrice = 15000))
        // 2026-03-16 is Monday. Default operating hours: mon 09:00-23:00 = 14 slots
        val availability = roomService.getAvailability(room.id, LocalDate.of(2026, 3, 16))
        assertEquals(true, availability.slots.isNotEmpty())
        assertEquals(14, availability.slots.size)
        assertEquals(true, availability.slots.all { it.available })
    }

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
}
