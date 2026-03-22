package org.flab.ensembleroomreservationproject.notification.service

import org.flab.ensembleroomreservationproject.common.exception.NotFoundException
import org.flab.ensembleroomreservationproject.notification.dto.NotificationSettingResponse
import org.flab.ensembleroomreservationproject.notification.dto.NotificationSettingUpdateRequest
import org.flab.ensembleroomreservationproject.notification.entity.NotificationSetting
import org.flab.ensembleroomreservationproject.notification.repository.NotificationSettingRepository
import org.flab.ensembleroomreservationproject.user.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional(readOnly = true)
class NotificationSettingService(
    private val notificationSettingRepository: NotificationSettingRepository,
    private val userRepository: UserRepository
) {
    @Transactional
    fun getOrCreate(userId: UUID): NotificationSettingResponse {
        val setting = notificationSettingRepository.findByUserId(userId)
            ?: createDefault(userId)
        return NotificationSettingResponse.from(setting)
    }

    @Transactional
    fun update(userId: UUID, request: NotificationSettingUpdateRequest): NotificationSettingResponse {
        val setting = notificationSettingRepository.findByUserId(userId)
            ?: createDefault(userId)
        request.reservationAlert?.let { setting.reservationAlert = it }
        request.reviewAlert?.let { setting.reviewAlert = it }
        request.marketingAlert?.let { setting.marketingAlert = it }
        return NotificationSettingResponse.from(setting)
    }

    private fun createDefault(userId: UUID): NotificationSetting {
        val user = userRepository.findById(userId)
            .orElseThrow { NotFoundException("유저를 찾을 수 없습니다: $userId") }
        return notificationSettingRepository.save(NotificationSetting(user = user))
    }
}
