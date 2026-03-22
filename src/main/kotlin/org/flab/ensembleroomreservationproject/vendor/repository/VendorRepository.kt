package org.flab.ensembleroomreservationproject.vendor.repository

import org.flab.ensembleroomreservationproject.vendor.entity.Vendor
import org.flab.ensembleroomreservationproject.vendor.entity.VendorStatus
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface VendorRepository : JpaRepository<Vendor, UUID> {
    fun findByStatus(status: VendorStatus, pageable: Pageable): Page<Vendor>
    fun findByOwnerId(ownerId: UUID): List<Vendor>
}
