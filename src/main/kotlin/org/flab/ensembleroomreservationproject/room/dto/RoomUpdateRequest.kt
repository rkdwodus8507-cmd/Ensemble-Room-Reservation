package org.flab.ensembleroomreservationproject.room.dto

import org.flab.ensembleroomreservationproject.room.entity.Equipment

data class RoomUpdateRequest(
    val name: String? = null,
    val description: String? = null,
    val capacity: Int? = null,
    val hourlyPrice: Int? = null,
    val minHours: Int? = null,
    val maxHours: Int? = null,
    val equipment: List<Equipment>? = null
)
