package org.flab.ensembleroomreservationproject.review.service

import org.flab.ensembleroomreservationproject.common.exception.BadRequestException
import org.flab.ensembleroomreservationproject.common.exception.ConflictException
import org.flab.ensembleroomreservationproject.common.exception.NotFoundException
import org.flab.ensembleroomreservationproject.reservation.entity.ReservationStatus
import org.flab.ensembleroomreservationproject.reservation.repository.ReservationRepository
import org.flab.ensembleroomreservationproject.review.dto.ReviewCreateRequest
import org.flab.ensembleroomreservationproject.review.dto.ReviewResponse
import org.flab.ensembleroomreservationproject.review.entity.Review
import org.flab.ensembleroomreservationproject.review.repository.ReviewRepository
import org.flab.ensembleroomreservationproject.user.repository.UserRepository
import org.flab.ensembleroomreservationproject.vendor.repository.VendorRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional(readOnly = true)
class ReviewService(
    private val reviewRepository: ReviewRepository,
    private val userRepository: UserRepository,
    private val vendorRepository: VendorRepository,
    private val reservationRepository: ReservationRepository
) {
    @Transactional
    fun createReview(request: ReviewCreateRequest): ReviewResponse {
        if (request.rating !in 1..5) {
            throw BadRequestException("평점은 1~5 사이여야 합니다")
        }

        val user = userRepository.findById(request.userId)
            .orElseThrow { NotFoundException("유저를 찾을 수 없습니다: ${request.userId}") }
        val vendor = vendorRepository.findById(request.vendorId)
            .orElseThrow { NotFoundException("업체를 찾을 수 없습니다: ${request.vendorId}") }

        val reservation = request.reservationId?.let { reservationId ->
            if (reviewRepository.existsByUserIdAndReservationId(request.userId, reservationId)) {
                throw ConflictException("해당 예약에 대한 리뷰가 이미 존재합니다")
            }
            val res = reservationRepository.findById(reservationId)
                .orElseThrow { NotFoundException("예약을 찾을 수 없습니다: $reservationId") }
            if (res.user.id != user.id) {
                throw BadRequestException("본인의 예약에만 리뷰를 작성할 수 있습니다")
            }
            if (res.vendor.id != vendor.id) {
                throw BadRequestException("해당 업체의 예약이 아닙니다")
            }
            if (res.status != ReservationStatus.COMPLETED) {
                throw BadRequestException("완료된 예약에만 리뷰를 작성할 수 있습니다")
            }
            res
        }

        val review = reviewRepository.save(
            Review(
                user = user,
                vendor = vendor,
                reservation = reservation,
                rating = request.rating,
                content = request.content
            )
        )
        return ReviewResponse.from(review)
    }

    fun getVendorReviews(vendorId: UUID): List<ReviewResponse> =
        reviewRepository.findByVendorIdWithDetails(vendorId)
            .map { ReviewResponse.from(it) }

    fun getUserReviews(userId: UUID): List<ReviewResponse> =
        reviewRepository.findByUserIdWithDetails(userId)
            .map { ReviewResponse.from(it) }

    @Transactional
    fun deleteReview(reviewId: UUID, userId: UUID) {
        val review = reviewRepository.findById(reviewId)
            .orElseThrow { NotFoundException("리뷰를 찾을 수 없습니다: $reviewId") }

        if (review.user.id != userId) {
            throw BadRequestException("본인의 리뷰만 삭제할 수 있습니다")
        }

        reviewRepository.delete(review)
    }
}
