package org.flab.ensembleroomreservationproject.support

import jakarta.persistence.EntityManager
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class DatabaseCleanup(
    private val entityManager: EntityManager
) {
    @Transactional
    fun execute() {
        entityManager.flush()
        // FK 의존성 역순으로 삭제
        val tables = listOf(
            "reviews",
            "favorites",
            "reservations",
            "time_slot_overrides",
            "rooms",
            "vendors",
            "users"
        )
        for (table in tables) {
            entityManager.createNativeQuery("DELETE FROM $table").executeUpdate()
        }
    }
}
