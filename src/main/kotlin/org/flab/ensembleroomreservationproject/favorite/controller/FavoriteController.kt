package org.flab.ensembleroomreservationproject.favorite.controller

import org.flab.ensembleroomreservationproject.common.dto.ApiResponse
import org.flab.ensembleroomreservationproject.favorite.dto.FavoriteRequest
import org.flab.ensembleroomreservationproject.favorite.dto.FavoriteResponse
import org.flab.ensembleroomreservationproject.favorite.service.FavoriteService
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/favorites")
class FavoriteController(
    private val favoriteService: FavoriteService
) {
    @PostMapping
    fun addFavorite(@RequestBody request: FavoriteRequest): ApiResponse<FavoriteResponse> =
        ApiResponse.ok(favoriteService.addFavorite(request.userId, request.vendorId))

    @DeleteMapping
    fun removeFavorite(@RequestBody request: FavoriteRequest): ApiResponse<Unit> {
        favoriteService.removeFavorite(request.userId, request.vendorId)
        return ApiResponse.ok(Unit)
    }

    @GetMapping
    fun getUserFavorites(@RequestParam userId: UUID): ApiResponse<List<FavoriteResponse>> =
        ApiResponse.ok(favoriteService.getUserFavorites(userId))

    @GetMapping("/check")
    fun isFavorite(
        @RequestParam userId: UUID,
        @RequestParam vendorId: UUID
    ): ApiResponse<Boolean> =
        ApiResponse.ok(favoriteService.isFavorite(userId, vendorId))
}
