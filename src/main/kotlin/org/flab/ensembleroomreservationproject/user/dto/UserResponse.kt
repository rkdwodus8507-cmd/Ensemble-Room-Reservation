package org.flab.ensembleroomreservationproject.user.dto

import org.flab.ensembleroomreservationproject.user.entity.User
import java.util.*

data class UserResponse(
    val id: UUID,
    val tossUserId: String,
    val nickname: String,
    val phone: String?,
    val profileImageUrl: String?,
    val role: String
) {
    companion object {
        fun from(user: User) = UserResponse(
            id = user.id!!,
            tossUserId = user.tossUserId,
            nickname = user.nickname,
            phone = user.phone,
            profileImageUrl = user.profileImageUrl,
            role = user.role.name
        )
    }
}
