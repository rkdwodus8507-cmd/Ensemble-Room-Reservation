package org.flab.ensembleroomreservationproject.review.entity

import jakarta.persistence.*
import org.flab.ensembleroomreservationproject.reservation.entity.Reservation
import org.flab.ensembleroomreservationproject.user.entity.User
import org.flab.ensembleroomreservationproject.vendor.entity.Vendor
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant
import java.util.*

@Entity
@Table(
    name = "reviews",
    uniqueConstraints = [
        UniqueConstraint(
            name = "uk_reviews_user_reservation",
            columnNames = ["user_id", "reservation_id"]
        )
    ]
)
@EntityListeners(AuditingEntityListener::class)
class Review(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id", nullable = false)
    val vendor: Vendor,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id")
    val reservation: Reservation? = null,

    @Column(nullable = false)
    val rating: Int,

    @Column(nullable = false, length = 2000)
    val content: String,

    @CreatedDate
    @Column(updatable = false)
    val createdAt: Instant? = null,

    @LastModifiedDate
    var updatedAt: Instant? = null
)
