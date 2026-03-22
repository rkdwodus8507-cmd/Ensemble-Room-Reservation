package org.flab.ensembleroomreservationproject.notification.controller

import org.flab.ensembleroomreservationproject.common.dto.ApiResponse
import org.flab.ensembleroomreservationproject.notification.dto.NotificationSettingResponse
import org.flab.ensembleroomreservationproject.notification.dto.NotificationSettingUpdateRequest
import org.flab.ensembleroomreservationproject.notification.service.NotificationSettingService
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/notification-settings")
class NotificationSettingController(
    private val notificationSettingService: NotificationSettingService
) {
    @GetMapping
    fun getSettings(@RequestParam userId: UUID): ApiResponse<NotificationSettingResponse> =
        ApiResponse.ok(notificationSettingService.getOrCreate(userId))

    @PatchMapping
    fun updateSettings(
        @RequestParam userId: UUID,
        @RequestBody request: NotificationSettingUpdateRequest
    ): ApiResponse<NotificationSettingResponse> =
        ApiResponse.ok(notificationSettingService.update(userId, request))
}
