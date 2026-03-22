package org.flab.ensembleroomreservationproject.user.entity

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant
import java.util.*

@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener::class)
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @Column(unique = true, nullable = false)
    val tossUserId: String,

    @Column(nullable = false)
    var nickname: String,

    var phone: String? = null,
    var profileImageUrl: String? = null,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var role: UserRole = UserRole.USER,

    @CreatedDate
    @Column(updatable = false)
    val createdAt: Instant? = null,

    @LastModifiedDate
    var updatedAt: Instant? = null
)
