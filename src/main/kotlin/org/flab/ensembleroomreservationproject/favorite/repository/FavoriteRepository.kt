package org.flab.ensembleroomreservationproject.favorite.repository

import org.flab.ensembleroomreservationproject.favorite.entity.Favorite
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface FavoriteRepository : JpaRepository<Favorite, UUID> {
    fun findByUserId(userId: UUID): List<Favorite>

    @Query("SELECT f FROM Favorite f JOIN FETCH f.vendor WHERE f.user.id = :userId")
    fun findByUserIdWithVendor(userId: UUID): List<Favorite>
    fun findByUserIdAndVendorId(userId: UUID, vendorId: UUID): Optional<Favorite>
    fun existsByUserIdAndVendorId(userId: UUID, vendorId: UUID): Boolean
    fun deleteByUserIdAndVendorId(userId: UUID, vendorId: UUID)
}
