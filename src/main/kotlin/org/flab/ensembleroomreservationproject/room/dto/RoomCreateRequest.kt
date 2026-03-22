package org.flab.ensembleroomreservationproject.room.dto

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import org.flab.ensembleroomreservationproject.room.entity.Equipment

data class RoomCreateRequest(
    @field:NotBlank(message = "룸 이름은 필수입니다")
    val name: String,
    val description: String? = null,
    @field:Min(1, message = "수용 인원은 1명 이상이어야 합니다")
    val capacity: Int,
    @field:Min(0, message = "가격은 0 이상이어야 합니다")
    val hourlyPrice: Int,
    val minHours: Int = 1,
    val maxHours: Int = 4,
    val equipment: List<Equipment>? = null
)
