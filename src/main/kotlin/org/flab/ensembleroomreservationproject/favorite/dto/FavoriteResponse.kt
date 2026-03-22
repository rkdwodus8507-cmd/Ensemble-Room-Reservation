package org.flab.ensembleroomreservationproject.favorite.dto

import org.flab.ensembleroomreservationproject.favorite.entity.Favorite
import java.time.Instant
import java.util.*

data class FavoriteResponse(
    val id: UUID,
    val vendorId: UUID,
    val vendorName: String,
    val vendorAddress: String,
    val vendorRating: Double,
    val vendorReviewCount: Int,
    val vendorImageUrl: String?,
    val createdAt: Instant?
) {
    companion object {
        fun from(favorite: Favorite) = FavoriteResponse(
            id = favorite.id!!,
            vendorId = favorite.vendor.id!!,
            vendorName = favorite.vendor.name,
            vendorAddress = favorite.vendor.address,
            vendorRating = 0.0,
            vendorReviewCount = 0,
            vendorImageUrl = favorite.vendor.thumbnailUrl,
            createdAt = favorite.createdAt
        )
    }
}
