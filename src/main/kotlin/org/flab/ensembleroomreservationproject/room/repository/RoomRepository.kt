package org.flab.ensembleroomreservationproject.room.repository

import org.flab.ensembleroomreservationproject.room.entity.Room
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface RoomRepository : JpaRepository<Room, UUID> {
    fun findByVendorIdAndIsActiveTrue(vendorId: UUID): List<Room>
}
