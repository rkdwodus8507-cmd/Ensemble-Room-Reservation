package org.flab.ensembleroomreservationproject.vendor.dto

import org.flab.ensembleroomreservationproject.vendor.entity.OperatingHour
import org.flab.ensembleroomreservationproject.vendor.entity.Vendor
import java.util.*

data class VendorResponse(
    val id: UUID,
    val ownerId: UUID,
    val name: String,
    val description: String?,
    val phone: String,
    val address: String,
    val addressDetail: String?,
    val latitude: Double?,
    val longitude: Double?,
    val businessNumber: String,
    val status: String,
    val operatingHours: Map<String, OperatingHour>,
    val amenities: List<String>,
    val thumbnailUrl: String?
) {
    companion object {
        fun from(vendor: Vendor) = VendorResponse(
            id = vendor.id!!,
            ownerId = vendor.owner.id!!,
            name = vendor.name,
            description = vendor.description,
            phone = vendor.phone,
            address = vendor.address,
            addressDetail = vendor.addressDetail,
            latitude = vendor.latitude,
            longitude = vendor.longitude,
            businessNumber = vendor.businessNumber,
            status = vendor.status.name,
            operatingHours = vendor.operatingHours,
            amenities = vendor.amenities,
            thumbnailUrl = vendor.thumbnailUrl
        )
    }
}
