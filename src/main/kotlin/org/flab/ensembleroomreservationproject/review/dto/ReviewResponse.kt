package org.flab.ensembleroomreservationproject.review.dto

import org.flab.ensembleroomreservationproject.review.entity.Review
import java.time.Instant
import java.util.*

data class ReviewResponse(
    val id: UUID,
    val userId: UUID,
    val userNickname: String,
    val vendorId: UUID,
    val vendorName: String,
    val reservationId: UUID?,
    val rating: Int,
    val content: String,
    val createdAt: Instant
) {
    companion object {
        fun from(review: Review) = ReviewResponse(
            id = review.id!!,
            userId = review.user.id!!,
            userNickname = review.user.nickname,
            vendorId = review.vendor.id!!,
            vendorName = review.vendor.name,
            reservationId = review.reservation?.id,
            rating = review.rating,
            content = review.content,
            createdAt = review.createdAt!!
        )
    }
}
