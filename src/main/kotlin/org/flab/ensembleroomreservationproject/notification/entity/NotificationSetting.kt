package org.flab.ensembleroomreservationproject.notification.entity

import jakarta.persistence.*
import org.flab.ensembleroomreservationproject.user.entity.User
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.util.*

@Entity
@Table(name = "notification_settings")
@EntityListeners(AuditingEntityListener::class)
class NotificationSetting(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    val user: User,

    @Column(nullable = false)
    var reservationAlert: Boolean = true,

    @Column(nullable = false)
    var reviewAlert: Boolean = true,

    @Column(nullable = false)
    var marketingAlert: Boolean = false
)
