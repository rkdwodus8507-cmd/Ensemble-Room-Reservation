package org.flab.ensembleroomreservationproject.room.controller

import jakarta.validation.Valid
import org.flab.ensembleroomreservationproject.common.dto.ApiResponse
import org.flab.ensembleroomreservationproject.room.dto.*
import org.flab.ensembleroomreservationproject.room.service.RoomService
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.util.*

@RestController
class RoomController(
    private val roomService: RoomService
) {
    @PostMapping("/api/v1/vendors/{vendorId}/rooms")
    fun createRoom(
        @PathVariable vendorId: UUID,
        @Valid @RequestBody request: RoomCreateRequest
    ): ApiResponse<RoomResponse> =
        ApiResponse.ok(roomService.createRoom(vendorId, request))

    @GetMapping("/api/v1/vendors/{vendorId}/rooms")
    fun getRoomsByVendor(@PathVariable vendorId: UUID): ApiResponse<List<RoomResponse>> =
        ApiResponse.ok(roomService.getRoomsByVendor(vendorId))

    @PatchMapping("/api/v1/rooms/{id}")
    fun updateRoom(@PathVariable id: UUID, @RequestBody request: RoomUpdateRequest): ApiResponse<RoomResponse> =
        ApiResponse.ok(roomService.updateRoom(id, request))

    @DeleteMapping("/api/v1/rooms/{id}")
    fun deleteRoom(@PathVariable id: UUID): ApiResponse<Nothing> {
        roomService.deleteRoom(id)
        return ApiResponse(success = true)
    }

    @GetMapping("/api/v1/rooms/{roomId}/availability")
    fun getAvailability(
        @PathVariable roomId: UUID,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) date: LocalDate
    ): ApiResponse<AvailabilityResponse> =
        ApiResponse.ok(roomService.getAvailability(roomId, date))
}
