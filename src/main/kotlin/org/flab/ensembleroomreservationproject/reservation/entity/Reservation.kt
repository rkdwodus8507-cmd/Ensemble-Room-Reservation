package org.flab.ensembleroomreservationproject.reservation.entity

import jakarta.persistence.*
import org.flab.ensembleroomreservationproject.room.entity.Room
import org.flab.ensembleroomreservationproject.user.entity.User
import org.flab.ensembleroomreservationproject.vendor.entity.Vendor
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

@Entity
@Table(name = "reservations")
@EntityListeners(AuditingEntityListener::class)
class Reservation(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @Column(unique = true, nullable = false)
    val reservationNumber: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    val room: Room,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id", nullable = false)
    val vendor: Vendor,

    @Column(nullable = false)
    val date: LocalDate,

    @Column(nullable = false)
    val startTime: LocalTime,

    @Column(nullable = false)
    val endTime: LocalTime,

    @Column(nullable = false)
    val durationHours: Int,

    @Column(nullable = false)
    val totalPrice: Int,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: ReservationStatus = ReservationStatus.PENDING,

    var userMemo: String? = null,
    var vendorMemo: String? = null,
    var cancelledAt: Instant? = null,
    var cancelledBy: String? = null,
    var cancelReason: String? = null,

    @CreatedDate
    @Column(updatable = false)
    val createdAt: Instant? = null,

    @LastModifiedDate
    var updatedAt: Instant? = null
)
