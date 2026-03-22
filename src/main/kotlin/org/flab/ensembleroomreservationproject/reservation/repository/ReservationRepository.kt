package org.flab.ensembleroomreservationproject.reservation.repository

import jakarta.persistence.LockModeType
import org.flab.ensembleroomreservationproject.reservation.entity.Reservation
import org.flab.ensembleroomreservationproject.reservation.entity.ReservationStatus
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

interface ReservationRepository : JpaRepository<Reservation, UUID> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
        SELECT r FROM Reservation r
        WHERE r.room.id = :roomId
          AND r.date = :date
          AND r.startTime < :endTime
          AND r.endTime > :startTime
          AND r.status IN (:statuses)
    """)
    fun findConflictingReservations(
        roomId: UUID,
        date: LocalDate,
        startTime: LocalTime,
        endTime: LocalTime,
        statuses: List<ReservationStatus>
    ): List<Reservation>

    fun findByUserId(userId: UUID, pageable: Pageable): Page<Reservation>

    @Query(
        value = "SELECT r FROM Reservation r JOIN FETCH r.user JOIN FETCH r.room JOIN FETCH r.vendor WHERE r.user.id = :userId ORDER BY r.createdAt DESC",
        countQuery = "SELECT count(r) FROM Reservation r WHERE r.user.id = :userId"
    )
    fun findByUserIdWithDetails(userId: UUID, pageable: Pageable): Page<Reservation>

    @Query("""
        SELECT r FROM Reservation r
        WHERE r.room.id = :roomId
          AND r.date = :date
          AND r.status IN (:statuses)
    """)
    fun findByRoomIdAndDate(
        roomId: UUID,
        date: LocalDate,
        statuses: List<ReservationStatus>
    ): List<Reservation>
}
