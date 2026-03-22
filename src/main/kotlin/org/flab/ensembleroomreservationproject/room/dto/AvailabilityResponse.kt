package org.flab.ensembleroomreservationproject.room.dto

import java.time.LocalDate
import java.util.*

data class AvailabilityResponse(
    val roomId: UUID,
    val date: LocalDate,
    val operatingHours: Map<String, String>,
    val hourlyPrice: Int,
    val slots: List<TimeSlot>
)

data class TimeSlot(
    val start: String,
    val end: String,
    val available: Boolean,
    val price: Int
)
