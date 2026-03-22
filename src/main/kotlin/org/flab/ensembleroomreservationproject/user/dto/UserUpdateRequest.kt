package org.flab.ensembleroomreservationproject.user.dto

data class UserUpdateRequest(
    val nickname: String?,
    val phone: String?,
    val profileImageUrl: String?
)
