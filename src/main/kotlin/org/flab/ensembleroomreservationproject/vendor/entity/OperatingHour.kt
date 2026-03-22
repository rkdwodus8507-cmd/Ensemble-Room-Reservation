package org.flab.ensembleroomreservationproject.vendor.entity

data class OperatingHour(
    val open: String,
    val close: String,
    val closed: Boolean = false
)
