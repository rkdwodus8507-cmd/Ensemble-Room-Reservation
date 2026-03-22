package org.flab.ensembleroomreservationproject.vendor.controller

import jakarta.validation.Valid
import org.flab.ensembleroomreservationproject.common.dto.ApiResponse
import org.flab.ensembleroomreservationproject.vendor.dto.VendorCreateRequest
import org.flab.ensembleroomreservationproject.vendor.dto.VendorResponse
import org.flab.ensembleroomreservationproject.vendor.dto.VendorUpdateRequest
import org.flab.ensembleroomreservationproject.vendor.service.VendorService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/vendors")
class VendorController(
    private val vendorService: VendorService
) {
    @PostMapping
    fun createVendor(@Valid @RequestBody request: VendorCreateRequest): ApiResponse<VendorResponse> =
        ApiResponse.ok(vendorService.createVendor(request))

    @GetMapping
    fun getApprovedVendors(@PageableDefault(size = 20) pageable: Pageable): ApiResponse<Page<VendorResponse>> =
        ApiResponse.ok(vendorService.getApprovedVendors(pageable))

    @GetMapping("/{id}")
    fun getVendor(@PathVariable id: UUID): ApiResponse<VendorResponse> =
        ApiResponse.ok(vendorService.getVendor(id))

    @PatchMapping("/{id}")
    fun updateVendor(@PathVariable id: UUID, @RequestBody request: VendorUpdateRequest): ApiResponse<VendorResponse> =
        ApiResponse.ok(vendorService.updateVendor(id, request))
}
