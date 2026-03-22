package org.flab.ensembleroomreservationproject.room.service

import org.flab.ensembleroomreservationproject.common.exception.NotFoundException
import org.flab.ensembleroomreservationproject.room.dto.*
import org.flab.ensembleroomreservationproject.room.entity.Room
import org.flab.ensembleroomreservationproject.reservation.entity.ReservationStatus
import org.flab.ensembleroomreservationproject.reservation.repository.ReservationRepository
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
    private val timeSlotOverrideRepository: TimeSlotOverrideRepository,
    private val reservationRepository: ReservationRepository
) {
    @Transactional
    fun createRoom(vendorId: UUID, request: RoomCreateRequest): RoomResponse {
        val vendor = vendorRepository.findById(vendorId)
            .orElseThrow { NotFoundException("업체를 찾을 수 없습니다: $vendorId") }
        val room = roomRepository.save(
            Room(
                vendor = vendor, name = request.name, description = request.description,
                capacity = request.capacity, hourlyPrice = request.hourlyPrice,
                minHours = request.minHours, maxHours = request.maxHours,
                equipment = request.equipment ?: emptyList()
            )
        )
        return RoomResponse.from(room)
    }

    fun getRoomsByVendor(vendorId: UUID): List<RoomResponse> =
        roomRepository.findByVendorIdAndIsActiveTrue(vendorId).map { RoomResponse.from(it) }

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

    @Transactional
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

        val activeStatuses = listOf(ReservationStatus.PENDING, ReservationStatus.CONFIRMED)
        val existingReservations = reservationRepository.findByRoomIdAndDate(roomId, date, activeStatuses)
        val reservedSlots = existingReservations.flatMap { r ->
            generateSequence(r.startTime) { it.plusHours(1) }
                .takeWhile { it.isBefore(r.endTime) }
                .toList()
        }.toSet()

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

            val isReserved = reservedSlots.contains(current)
            slots.add(TimeSlot(
                start = current.toString(),
                end = nextDisplay,
                available = !blocked && !isReserved,
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
