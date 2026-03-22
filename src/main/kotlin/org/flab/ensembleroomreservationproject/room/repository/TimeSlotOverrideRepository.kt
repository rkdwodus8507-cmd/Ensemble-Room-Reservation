package org.flab.ensembleroomreservationproject.room.repository

import org.flab.ensembleroomreservationproject.room.entity.TimeSlotOverride
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate
import java.util.*

interface TimeSlotOverrideRepository : JpaRepository<TimeSlotOverride, UUID> {
    fun findByRoomIdAndDate(roomId: UUID, date: LocalDate): List<TimeSlotOverride>
}
