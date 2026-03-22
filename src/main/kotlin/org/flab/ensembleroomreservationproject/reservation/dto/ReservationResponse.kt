package org.flab.ensembleroomreservationproject.reservation.dto

import org.flab.ensembleroomreservationproject.reservation.entity.Reservation
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

data class ReservationResponse(
    val id: UUID,
    val reservationNumber: String,
    val userId: UUID,
    val roomId: UUID,
    val vendorId: UUID,
    val vendorName: String,
    val roomName: String,
    val date: LocalDate,
    val startTime: LocalTime,
    val endTime: LocalTime,
    val durationHours: Int,
    val totalPrice: Int,
    val status: String,
    val userMemo: String?,
    val hasReview: Boolean = false
) {
    companion object {
        fun from(reservation: Reservation, hasReview: Boolean = false) = ReservationResponse(
            id = reservation.id!!,
            reservationNumber = reservation.reservationNumber,
            userId = reservation.user.id!!,
            roomId = reservation.room.id!!,
            vendorId = reservation.vendor.id!!,
            vendorName = reservation.vendor.name,
            roomName = reservation.room.name,
            date = reservation.date,
            startTime = reservation.startTime,
            endTime = reservation.endTime,
            durationHours = reservation.durationHours,
            totalPrice = reservation.totalPrice,
            status = reservation.status.name,
            userMemo = reservation.userMemo,
            hasReview = hasReview
        )
    }
}
