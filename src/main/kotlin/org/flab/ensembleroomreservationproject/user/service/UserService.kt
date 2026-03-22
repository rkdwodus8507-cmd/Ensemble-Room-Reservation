package org.flab.ensembleroomreservationproject.user.service

import org.flab.ensembleroomreservationproject.common.exception.NotFoundException
import org.flab.ensembleroomreservationproject.user.dto.UserResponse
import org.flab.ensembleroomreservationproject.user.dto.UserUpdateRequest
import org.flab.ensembleroomreservationproject.user.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional(readOnly = true)
class UserService(
    private val userRepository: UserRepository
) {
    fun getUser(id: UUID): UserResponse {
        val user = userRepository.findById(id)
            .orElseThrow { NotFoundException("유저를 찾을 수 없습니다: $id") }
        return UserResponse.from(user)
    }

    @Transactional
    fun updateUser(id: UUID, request: UserUpdateRequest): UserResponse {
        val user = userRepository.findById(id)
            .orElseThrow { NotFoundException("유저를 찾을 수 없습니다: $id") }
        request.nickname?.let { user.nickname = it }
        request.phone?.let { user.phone = it }
        request.profileImageUrl?.let { user.profileImageUrl = it }
        return UserResponse.from(user)
    }
}
