package org.flab.ensembleroomreservationproject.reservation.dto

data class CancelRequest(
    val cancelledBy: String,
    val cancelReason: String? = null
)
