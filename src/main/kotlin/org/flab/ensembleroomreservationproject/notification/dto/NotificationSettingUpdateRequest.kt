package org.flab.ensembleroomreservationproject.notification.dto

data class NotificationSettingUpdateRequest(
    val reservationAlert: Boolean?,
    val reviewAlert: Boolean?,
    val marketingAlert: Boolean?
)
