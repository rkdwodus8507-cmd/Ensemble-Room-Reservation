package org.flab.ensembleroomreservationproject.notification.dto

import org.flab.ensembleroomreservationproject.notification.entity.NotificationSetting
import java.util.*

data class NotificationSettingResponse(
    val id: UUID,
    val userId: UUID,
    val reservationAlert: Boolean,
    val reviewAlert: Boolean,
    val marketingAlert: Boolean
) {
    companion object {
        fun from(setting: NotificationSetting) = NotificationSettingResponse(
            id = setting.id!!,
            userId = setting.user.id!!,
            reservationAlert = setting.reservationAlert,
            reviewAlert = setting.reviewAlert,
            marketingAlert = setting.marketingAlert
        )
    }
}
