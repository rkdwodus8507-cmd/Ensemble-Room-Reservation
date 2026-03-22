package org.flab.ensembleroomreservationproject.room.entity

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

@Entity
@Table(
    name = "time_slot_overrides",
    uniqueConstraints = [UniqueConstraint(columnNames = ["room_id", "date", "start_time"])]
)
@EntityListeners(AuditingEntityListener::class)
class TimeSlotOverride(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    val room: Room,

    @Column(nullable = false)
    val date: LocalDate,

    @Column(nullable = false)
    val startTime: LocalTime,

    @Column(nullable = false)
    val endTime: LocalTime,

    var overridePrice: Int? = null,
    var isBlocked: Boolean = false,
    var reason: String? = null,

    @CreatedDate
    @Column(updatable = false)
    val createdAt: Instant? = null
)
