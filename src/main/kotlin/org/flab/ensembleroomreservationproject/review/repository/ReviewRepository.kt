package org.flab.ensembleroomreservationproject.review.repository

import org.flab.ensembleroomreservationproject.review.entity.Review
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ReviewRepository : JpaRepository<Review, UUID> {

    fun findByVendorIdOrderByCreatedAtDesc(vendorId: UUID): List<Review>

    fun findByUserId(userId: UUID): List<Review>

    fun findByReservationId(reservationId: UUID): Optional<Review>

    fun existsByUserIdAndReservationId(userId: UUID, reservationId: UUID): Boolean
}
