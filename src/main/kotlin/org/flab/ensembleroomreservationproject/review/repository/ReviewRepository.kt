package org.flab.ensembleroomreservationproject.review.repository

import org.flab.ensembleroomreservationproject.review.entity.Review
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface ReviewRepository : JpaRepository<Review, UUID> {

    fun findByVendorIdOrderByCreatedAtDesc(vendorId: UUID): List<Review>

    fun findByUserId(userId: UUID): List<Review>

    @Query("SELECT r FROM Review r JOIN FETCH r.user JOIN FETCH r.vendor LEFT JOIN FETCH r.reservation WHERE r.vendor.id = :vendorId ORDER BY r.createdAt DESC")
    fun findByVendorIdWithDetails(vendorId: UUID): List<Review>

    @Query("SELECT r FROM Review r JOIN FETCH r.user JOIN FETCH r.vendor LEFT JOIN FETCH r.reservation WHERE r.user.id = :userId")
    fun findByUserIdWithDetails(userId: UUID): List<Review>

    fun findByReservationId(reservationId: UUID): Optional<Review>

    fun existsByUserIdAndReservationId(userId: UUID, reservationId: UUID): Boolean

    @Query("SELECT r.reservation.id FROM Review r WHERE r.reservation.id IN :reservationIds")
    fun findReservationIdsWithReviews(reservationIds: List<UUID>): List<UUID>

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.vendor.id = :vendorId")
    fun findAverageRatingByVendorId(vendorId: UUID): Double?

    @Query("SELECT COUNT(r) FROM Review r WHERE r.vendor.id = :vendorId")
    fun countByVendorId(vendorId: UUID): Long
}
