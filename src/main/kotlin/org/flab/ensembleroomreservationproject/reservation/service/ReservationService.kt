package org.flab.ensembleroomreservationproject.reservation.service

import org.flab.ensembleroomreservationproject.common.exception.BadRequestException
import org.flab.ensembleroomreservationproject.common.exception.ConflictException
import org.flab.ensembleroomreservationproject.common.exception.NotFoundException
import org.flab.ensembleroomreservationproject.reservation.dto.CancelRequest
import org.flab.ensembleroomreservationproject.reservation.dto.ReservationCreateRequest
import org.flab.ensembleroomreservationproject.reservation.dto.ReservationResponse
import org.flab.ensembleroomreservationproject.reservation.entity.Reservation
import org.flab.ensembleroomreservationproject.reservation.entity.ReservationStatus
import org.flab.ensembleroomreservationproject.reservation.repository.ReservationRepository
import org.flab.ensembleroomreservationproject.room.repository.RoomRepository
import org.flab.ensembleroomreservationproject.user.repository.UserRepository
import org.flab.ensembleroomreservationproject.vendor.entity.VendorStatus
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.time.LocalDate
import java.util.*

@Service
@Transactional(readOnly = true)
class ReservationService(
    private val reservationRepository: ReservationRepository,
    private val roomRepository: RoomRepository,
    private val userRepository: UserRepository,
    private val reservationNumberGenerator: ReservationNumberGenerator
) {
    @Transactional
    fun createReservation(request: ReservationCreateRequest): ReservationResponse {
        val user = userRepository.findById(request.userId)
            .orElseThrow { NotFoundException("유저를 찾을 수 없습니다: ${request.userId}") }
        val room = roomRepository.findById(request.roomId)
            .orElseThrow { NotFoundException("룸을 찾을 수 없습니다: ${request.roomId}") }

        if (request.date.isBefore(LocalDate.now())) {
            throw BadRequestException("과거 날짜에는 예약할 수 없습니다")
        }

        if (room.vendor.status != VendorStatus.APPROVED) {
            throw BadRequestException("승인되지 않은 업체에는 예약할 수 없습니다")
        }

        if (request.durationHours < room.minHours || request.durationHours > room.maxHours) {
            throw BadRequestException("예약 시간은 ${room.minHours}~${room.maxHours}시간이어야 합니다")
        }

        val endTime = request.startTime.plusHours(request.durationHours.toLong())

        val activeStatuses = listOf(ReservationStatus.PENDING, ReservationStatus.CONFIRMED)
        val conflicts = reservationRepository.findConflictingReservations(
            roomId = room.id!!,
            date = request.date,
            startTime = request.startTime,
            endTime = endTime,
            statuses = activeStatuses
        )
        if (conflicts.isNotEmpty()) {
            throw ConflictException("해당 시간대에 이미 예약이 존재합니다")
        }

        val reservation = reservationRepository.save(
            Reservation(
                reservationNumber = reservationNumberGenerator.generate(request.date),
                user = user,
                room = room,
                vendor = room.vendor,
                date = request.date,
                startTime = request.startTime,
                endTime = endTime,
                durationHours = request.durationHours,
                totalPrice = room.hourlyPrice * request.durationHours,
                userMemo = request.userMemo
            )
        )
        return ReservationResponse.from(reservation)
    }

    fun getReservation(id: UUID): ReservationResponse =
        reservationRepository.findById(id)
            .map { ReservationResponse.from(it) }
            .orElseThrow { NotFoundException("예약을 찾을 수 없습니다: $id") }

    fun getUserReservations(userId: UUID, pageable: Pageable): Page<ReservationResponse> =
        reservationRepository.findByUserIdWithDetails(userId, pageable)
            .map { ReservationResponse.from(it) }

    @Transactional
    fun cancelReservation(id: UUID, request: CancelRequest): ReservationResponse {
        val reservation = reservationRepository.findById(id)
            .orElseThrow { NotFoundException("예약을 찾을 수 없습니다: $id") }

        if (reservation.status != ReservationStatus.PENDING && reservation.status != ReservationStatus.CONFIRMED) {
            throw BadRequestException("취소할 수 없는 예약 상태입니다")
        }

        reservation.status = ReservationStatus.CANCELLED
        reservation.cancelledAt = Instant.now()
        reservation.cancelledBy = request.cancelledBy
        reservation.cancelReason = request.cancelReason

        return ReservationResponse.from(reservation)
    }

    @Transactional
    fun updateStatus(id: UUID, status: ReservationStatus): ReservationResponse {
        val reservation = reservationRepository.findById(id)
            .orElseThrow { NotFoundException("예약을 찾을 수 없습니다: $id") }

        val allowedTransitions = mapOf(
            ReservationStatus.PENDING to setOf(ReservationStatus.CONFIRMED, ReservationStatus.CANCELLED),
            ReservationStatus.CONFIRMED to setOf(ReservationStatus.COMPLETED, ReservationStatus.CANCELLED, ReservationStatus.NO_SHOW)
        )
        if (status !in (allowedTransitions[reservation.status] ?: emptySet())) {
            throw BadRequestException("${reservation.status}에서 ${status}(으)로 변경할 수 없습니다")
        }

        reservation.status = status
        return ReservationResponse.from(reservation)
    }
}
