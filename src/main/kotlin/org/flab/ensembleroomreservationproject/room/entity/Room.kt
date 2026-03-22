package org.flab.ensembleroomreservationproject.room.entity

import jakarta.persistence.*
import org.flab.ensembleroomreservationproject.vendor.entity.Vendor
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant
import java.util.*

@Entity
@Table(name = "rooms")
@EntityListeners(AuditingEntityListener::class)
class Room(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id", nullable = false)
    val vendor: Vendor,

    @Column(nullable = false)
    var name: String,

    var description: String? = null,

    @Column(nullable = false)
    var capacity: Int,

    @Column(nullable = false)
    var hourlyPrice: Int,

    @Column(nullable = false)
    var minHours: Int = 1,

    @Column(nullable = false)
    var maxHours: Int = 4,

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    var equipment: List<Equipment> = emptyList(),

    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(columnDefinition = "text[]")
    var images: List<String> = emptyList(),

    var isActive: Boolean = true,
    var sortOrder: Int = 0,

    @CreatedDate
    @Column(updatable = false)
    val createdAt: Instant? = null,

    @LastModifiedDate
    var updatedAt: Instant? = null
)
