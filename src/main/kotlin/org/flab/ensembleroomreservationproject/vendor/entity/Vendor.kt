package org.flab.ensembleroomreservationproject.vendor.entity

import jakarta.persistence.*
import org.flab.ensembleroomreservationproject.user.entity.User
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant
import java.util.*

@Entity
@Table(name = "vendors")
@EntityListeners(AuditingEntityListener::class)
class Vendor(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    val owner: User,

    @Column(nullable = false)
    var name: String,

    var description: String? = null,

    @Column(nullable = false)
    var phone: String,

    @Column(nullable = false)
    var address: String,

    var addressDetail: String? = null,
    var latitude: Double? = null,
    var longitude: Double? = null,

    @Column(nullable = false)
    var businessNumber: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: VendorStatus = VendorStatus.PENDING,

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    var operatingHours: Map<String, OperatingHour> = defaultOperatingHours(),

    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(columnDefinition = "text[]")
    var amenities: List<String> = emptyList(),

    var thumbnailUrl: String? = null,

    @CreatedDate
    @Column(updatable = false)
    val createdAt: Instant? = null,

    @LastModifiedDate
    var updatedAt: Instant? = null
) {
    companion object {
        fun defaultOperatingHours() = mapOf(
            "mon" to OperatingHour("09:00", "23:00"),
            "tue" to OperatingHour("09:00", "23:00"),
            "wed" to OperatingHour("09:00", "23:00"),
            "thu" to OperatingHour("09:00", "23:00"),
            "fri" to OperatingHour("09:00", "23:00"),
            "sat" to OperatingHour("10:00", "24:00"),
            "sun" to OperatingHour("10:00", "22:00")
        )
    }
}
