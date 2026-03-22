package org.flab.ensembleroomreservationproject.favorite.service

import org.flab.ensembleroomreservationproject.common.exception.ConflictException
import org.flab.ensembleroomreservationproject.common.exception.NotFoundException
import org.flab.ensembleroomreservationproject.favorite.dto.FavoriteResponse
import org.flab.ensembleroomreservationproject.favorite.entity.Favorite
import org.flab.ensembleroomreservationproject.favorite.repository.FavoriteRepository
import org.flab.ensembleroomreservationproject.user.repository.UserRepository
import org.flab.ensembleroomreservationproject.vendor.repository.VendorRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional(readOnly = true)
class FavoriteService(
    private val favoriteRepository: FavoriteRepository,
    private val userRepository: UserRepository,
    private val vendorRepository: VendorRepository
) {
    @Transactional
    fun addFavorite(userId: UUID, vendorId: UUID): FavoriteResponse {
        if (favoriteRepository.existsByUserIdAndVendorId(userId, vendorId)) {
            throw ConflictException("이미 즐겨찾기에 추가된 업체입니다")
        }

        val user = userRepository.findById(userId)
            .orElseThrow { NotFoundException("유저를 찾을 수 없습니다: $userId") }
        val vendor = vendorRepository.findById(vendorId)
            .orElseThrow { NotFoundException("업체를 찾을 수 없습니다: $vendorId") }

        val favorite = favoriteRepository.save(
            Favorite(user = user, vendor = vendor)
        )
        return FavoriteResponse.from(favorite)
    }

    @Transactional
    fun removeFavorite(userId: UUID, vendorId: UUID) {
        if (!favoriteRepository.existsByUserIdAndVendorId(userId, vendorId)) {
            throw NotFoundException("즐겨찾기를 찾을 수 없습니다")
        }
        favoriteRepository.deleteByUserIdAndVendorId(userId, vendorId)
    }

    fun getUserFavorites(userId: UUID): List<FavoriteResponse> =
        favoriteRepository.findByUserIdWithVendor(userId)
            .map { FavoriteResponse.from(it) }

    fun isFavorite(userId: UUID, vendorId: UUID): Boolean =
        favoriteRepository.existsByUserIdAndVendorId(userId, vendorId)
}
