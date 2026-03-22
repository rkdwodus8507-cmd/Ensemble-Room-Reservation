package org.flab.ensembleroomreservationproject.vendor.dto

import jakarta.validation.constraints.NotBlank
import org.flab.ensembleroomreservationproject.vendor.entity.OperatingHour
import java.util.*

data class VendorCreateRequest(
    val ownerId: UUID,
    @field:NotBlank(message = "업체명은 필수입니다")
    val name: String,
    val description: String? = null,
    @field:NotBlank(message = "연락처는 필수입니다")
    val phone: String,
    @field:NotBlank(message = "주소는 필수입니다")
    val address: String,
    val addressDetail: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
    @field:NotBlank(message = "사업자등록번호는 필수입니다")
    val businessNumber: String,
    val operatingHours: Map<String, OperatingHour>? = null,
    val amenities: List<String>? = null,
    val thumbnailUrl: String? = null
)
