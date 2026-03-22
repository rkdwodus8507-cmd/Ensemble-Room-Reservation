package org.flab.ensembleroomreservationproject.vendor.service

import org.flab.ensembleroomreservationproject.common.exception.NotFoundException
import org.flab.ensembleroomreservationproject.review.repository.ReviewRepository
import org.flab.ensembleroomreservationproject.user.repository.UserRepository
import org.flab.ensembleroomreservationproject.vendor.dto.VendorCreateRequest
import org.flab.ensembleroomreservationproject.vendor.dto.VendorResponse
import org.flab.ensembleroomreservationproject.vendor.dto.VendorUpdateRequest
import org.flab.ensembleroomreservationproject.vendor.entity.Vendor
import org.flab.ensembleroomreservationproject.vendor.entity.VendorStatus
import org.flab.ensembleroomreservationproject.vendor.repository.VendorRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional(readOnly = true)
class VendorService(
    private val vendorRepository: VendorRepository,
    private val userRepository: UserRepository,
    private val reviewRepository: ReviewRepository
) {
    @Transactional
    fun createVendor(request: VendorCreateRequest): VendorResponse {
        val owner = userRepository.findById(request.ownerId)
            .orElseThrow { NotFoundException("유저를 찾을 수 없습니다: ${request.ownerId}") }
        val vendor = vendorRepository.save(
            Vendor(
                owner = owner, name = request.name, description = request.description,
                phone = request.phone, address = request.address,
                addressDetail = request.addressDetail, latitude = request.latitude,
                longitude = request.longitude, businessNumber = request.businessNumber,
                operatingHours = request.operatingHours ?: Vendor.defaultOperatingHours(),
                amenities = request.amenities ?: emptyList(), thumbnailUrl = request.thumbnailUrl
            )
        )
        return VendorResponse.from(vendor)
    }

    fun getApprovedVendors(pageable: Pageable): Page<VendorResponse> =
        vendorRepository.findByStatus(VendorStatus.APPROVED, pageable).map { toResponseWithReviewStats(it) }

    fun getPendingVendors(pageable: Pageable): Page<VendorResponse> =
        vendorRepository.findByStatus(VendorStatus.PENDING, pageable).map { VendorResponse.from(it) }

    fun getVendor(id: UUID): VendorResponse {
        val vendor = vendorRepository.findById(id)
            .orElseThrow { NotFoundException("업체를 찾을 수 없습니다: $id") }
        return toResponseWithReviewStats(vendor)
    }

    @Transactional
    fun updateVendor(id: UUID, request: VendorUpdateRequest): VendorResponse {
        val vendor = vendorRepository.findById(id)
            .orElseThrow { NotFoundException("업체를 찾을 수 없습니다: $id") }
        request.name?.let { vendor.name = it }
        request.description?.let { vendor.description = it }
        request.phone?.let { vendor.phone = it }
        request.address?.let { vendor.address = it }
        request.addressDetail?.let { vendor.addressDetail = it }
        request.latitude?.let { vendor.latitude = it }
        request.longitude?.let { vendor.longitude = it }
        request.operatingHours?.let { vendor.operatingHours = it }
        request.amenities?.let { vendor.amenities = it }
        request.thumbnailUrl?.let { vendor.thumbnailUrl = it }
        return VendorResponse.from(vendor)
    }

    @Transactional
    fun approveVendor(id: UUID): VendorResponse {
        val vendor = vendorRepository.findById(id)
            .orElseThrow { NotFoundException("업체를 찾을 수 없습니다: $id") }
        vendor.status = VendorStatus.APPROVED
        return VendorResponse.from(vendor)
    }

    @Transactional
    fun rejectVendor(id: UUID): VendorResponse {
        val vendor = vendorRepository.findById(id)
            .orElseThrow { NotFoundException("업체를 찾을 수 없습니다: $id") }
        vendor.status = VendorStatus.REJECTED
        return VendorResponse.from(vendor)
    }

    private fun toResponseWithReviewStats(vendor: Vendor): VendorResponse {
        val rating = reviewRepository.findAverageRatingByVendorId(vendor.id!!) ?: 0.0
        val reviewCount = reviewRepository.countByVendorId(vendor.id!!)
        return VendorResponse.from(vendor, Math.round(rating * 10) / 10.0, reviewCount)
    }
}
