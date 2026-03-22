package org.flab.ensembleroomreservationproject.reservation.service

import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Component
class ReservationNumberGenerator {
    private val dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd")
    private val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"

    fun generate(date: LocalDate): String {
        val datePart = date.format(dateFormatter)
        val randomPart = (1..4).map { chars.random() }.joinToString("")
        return "R-$datePart-$randomPart"
    }
}
