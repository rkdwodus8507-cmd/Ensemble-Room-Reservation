package org.flab.ensembleroomreservationproject.room.dto

import org.flab.ensembleroomreservationproject.room.entity.Equipment
import org.flab.ensembleroomreservationproject.room.entity.Room
import java.util.*

data class RoomResponse(
    val id: UUID,
    val vendorId: UUID,
    val name: String,
    val description: String?,
    val capacity: Int,
    val hourlyPrice: Int,
    val minHours: Int,
    val maxHours: Int,
    val equipment: List<Equipment>,
    val isActive: Boolean
) {
    companion object {
        fun from(room: Room) = RoomResponse(
            id = room.id!!, vendorId = room.vendor.id!!,
            name = room.name, description = room.description,
            capacity = room.capacity, hourlyPrice = room.hourlyPrice,
            minHours = room.minHours, maxHours = room.maxHours,
            equipment = room.equipment, isActive = room.isActive
        )
    }
}
