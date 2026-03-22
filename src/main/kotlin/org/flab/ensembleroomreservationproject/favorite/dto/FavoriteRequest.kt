package org.flab.ensembleroomreservationproject.favorite.dto

import java.util.*

data class FavoriteRequest(
    val userId: UUID,
    val vendorId: UUID
)
