package org.flab.ensembleroomreservationproject.vendor.dto

import org.flab.ensembleroomreservationproject.vendor.entity.OperatingHour

data class VendorUpdateRequest(
    val name: String? = null,
    val description: String? = null,
    val phone: String? = null,
    val address: String? = null,
    val addressDetail: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val operatingHours: Map<String, OperatingHour>? = null,
    val amenities: List<String>? = null,
    val thumbnailUrl: String? = null
)
