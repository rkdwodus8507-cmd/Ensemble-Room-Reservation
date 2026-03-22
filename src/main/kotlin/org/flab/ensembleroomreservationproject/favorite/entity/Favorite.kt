package org.flab.ensembleroomreservationproject.favorite.entity

import jakarta.persistence.*
import org.flab.ensembleroomreservationproject.user.entity.User
import org.flab.ensembleroomreservationproject.vendor.entity.Vendor
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant
import java.util.*

@Entity
@Table(
    name = "favorites",
    uniqueConstraints = [UniqueConstraint(columnNames = ["user_id", "vendor_id"])]
)
@EntityListeners(AuditingEntityListener::class)
class Favorite(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id", nullable = false)
    val vendor: Vendor,

    @CreatedDate
    @Column(updatable = false)
    val createdAt: Instant? = null
)
