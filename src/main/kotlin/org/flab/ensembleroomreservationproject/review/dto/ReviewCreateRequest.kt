package org.flab.ensembleroomreservationproject.review.dto

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import java.util.*

data class ReviewCreateRequest(
    val userId: UUID,
    val vendorId: UUID,
    val reservationId: UUID? = null,
    @field:Min(1, message = "평점은 1 이상이어야 합니다")
    @field:Max(5, message = "평점은 5 이하여야 합니다")
    val rating: Int,
    @field:NotBlank(message = "리뷰 내용은 필수입니다")
    val content: String
)
