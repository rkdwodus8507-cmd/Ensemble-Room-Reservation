package org.flab.ensembleroomreservationproject.vendor.controller

import org.flab.ensembleroomreservationproject.common.dto.ApiResponse
import org.flab.ensembleroomreservationproject.vendor.dto.VendorResponse
import org.flab.ensembleroomreservationproject.vendor.service.VendorService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/admin/vendors")
class AdminVendorController(
    private val vendorService: VendorService
) {
    @GetMapping("/pending")
    fun getPendingVendors(@PageableDefault(size = 20) pageable: Pageable): ApiResponse<Page<VendorResponse>> =
        ApiResponse.ok(vendorService.getPendingVendors(pageable))

    @PostMapping("/{id}/approve")
    fun approveVendor(@PathVariable id: UUID): ApiResponse<VendorResponse> =
        ApiResponse.ok(vendorService.approveVendor(id))

    @PostMapping("/{id}/reject")
    fun rejectVendor(@PathVariable id: UUID): ApiResponse<VendorResponse> =
        ApiResponse.ok(vendorService.rejectVendor(id))
}
