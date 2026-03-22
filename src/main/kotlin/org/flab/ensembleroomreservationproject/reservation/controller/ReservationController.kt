package org.flab.ensembleroomreservationproject.reservation.controller

import jakarta.validation.Valid
import org.flab.ensembleroomreservationproject.common.dto.ApiResponse
import org.flab.ensembleroomreservationproject.reservation.dto.CancelRequest
import org.flab.ensembleroomreservationproject.reservation.dto.ReservationCreateRequest
import org.flab.ensembleroomreservationproject.reservation.dto.ReservationResponse
import org.flab.ensembleroomreservationproject.reservation.entity.ReservationStatus
import org.flab.ensembleroomreservationproject.reservation.service.ReservationService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/reservations")
class ReservationController(
    private val reservationService: ReservationService
) {
    @GetMapping("/{id}")
    fun getReservation(@PathVariable id: UUID): ApiResponse<ReservationResponse> =
        ApiResponse.ok(reservationService.getReservation(id))

    @PostMapping
    fun createReservation(@Valid @RequestBody request: ReservationCreateRequest): ApiResponse<ReservationResponse> =
        ApiResponse.ok(reservationService.createReservation(request))

    @GetMapping
    fun getUserReservations(
        @RequestParam userId: UUID,
        @PageableDefault(size = 20) pageable: Pageable
    ): ApiResponse<Page<ReservationResponse>> =
        ApiResponse.ok(reservationService.getUserReservations(userId, pageable))

    @PostMapping("/{id}/cancel")
    fun cancelReservation(
        @PathVariable id: UUID,
        @RequestBody request: CancelRequest
    ): ApiResponse<ReservationResponse> =
        ApiResponse.ok(reservationService.cancelReservation(id, request))

    @PatchMapping("/{id}/status")
    fun updateStatus(
        @PathVariable id: UUID,
        @RequestParam status: ReservationStatus
    ): ApiResponse<ReservationResponse> =
        ApiResponse.ok(reservationService.updateStatus(id, status))
}
