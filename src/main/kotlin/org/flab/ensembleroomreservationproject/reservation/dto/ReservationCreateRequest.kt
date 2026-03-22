package org.flab.ensembleroomreservationproject.reservation.dto

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

data class ReservationCreateRequest(
    val userId: UUID,
    val roomId: UUID,
    @field:NotNull(message = "예약 날짜는 필수입니다")
    val date: LocalDate,
    @field:NotNull(message = "시작 시간은 필수입니다")
    val startTime: LocalTime,
    @field:Min(1, message = "예약 시간은 1시간 이상이어야 합니다")
    val durationHours: Int,
    val userMemo: String? = null
)
