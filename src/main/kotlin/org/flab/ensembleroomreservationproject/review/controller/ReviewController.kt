package org.flab.ensembleroomreservationproject.review.controller

import jakarta.validation.Valid
import org.flab.ensembleroomreservationproject.common.dto.ApiResponse
import org.flab.ensembleroomreservationproject.review.dto.ReviewCreateRequest
import org.flab.ensembleroomreservationproject.review.dto.ReviewResponse
import org.flab.ensembleroomreservationproject.review.service.ReviewService
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/reviews")
class ReviewController(
    private val reviewService: ReviewService
) {
    @PostMapping
    fun createReview(@Valid @RequestBody request: ReviewCreateRequest): ApiResponse<ReviewResponse> =
        ApiResponse.ok(reviewService.createReview(request))

    @GetMapping("/vendor/{vendorId}")
    fun getVendorReviews(@PathVariable vendorId: UUID): ApiResponse<List<ReviewResponse>> =
        ApiResponse.ok(reviewService.getVendorReviews(vendorId))

    @GetMapping("/user/{userId}")
    fun getUserReviews(@PathVariable userId: UUID): ApiResponse<List<ReviewResponse>> =
        ApiResponse.ok(reviewService.getUserReviews(userId))

    @DeleteMapping("/{id}")
    fun deleteReview(@PathVariable id: UUID, @RequestParam userId: UUID): ApiResponse<Unit> {
        reviewService.deleteReview(id, userId)
        return ApiResponse.ok(Unit)
    }
}
