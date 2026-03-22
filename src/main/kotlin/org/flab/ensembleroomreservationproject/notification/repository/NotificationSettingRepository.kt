package org.flab.ensembleroomreservationproject.notification.repository

import org.flab.ensembleroomreservationproject.notification.entity.NotificationSetting
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface NotificationSettingRepository : JpaRepository<NotificationSetting, UUID> {
    fun findByUserId(userId: UUID): NotificationSetting?
}
