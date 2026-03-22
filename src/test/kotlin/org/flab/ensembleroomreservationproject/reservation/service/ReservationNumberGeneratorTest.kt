package org.flab.ensembleroomreservationproject.reservation.service

import org.junit.jupiter.api.Test
import java.time.LocalDate
import kotlin.test.assertTrue

class ReservationNumberGeneratorTest {

    @Test
    fun `예약번호 형식 검증`() {
        val generator = ReservationNumberGenerator()
        val number = generator.generate(LocalDate.of(2026, 3, 15))
        assertTrue(number.matches(Regex("R-20260315-[A-Z0-9]{4}")))
    }

    @Test
    fun `예약번호 고유성 검증`() {
        val generator = ReservationNumberGenerator()
        val date = LocalDate.of(2026, 3, 15)
        val numbers = (1..100).map { generator.generate(date) }.toSet()
        assertTrue(numbers.size >= 95)
    }
}
