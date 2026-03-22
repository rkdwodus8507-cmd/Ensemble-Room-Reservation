package org.flab.ensembleroomreservationproject.user.controller

import org.flab.ensembleroomreservationproject.common.dto.ApiResponse
import org.flab.ensembleroomreservationproject.user.dto.UserResponse
import org.flab.ensembleroomreservationproject.user.dto.UserUpdateRequest
import org.flab.ensembleroomreservationproject.user.service.UserService
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/users")
class UserController(
    private val userService: UserService
) {
    @GetMapping("/{id}")
    fun getUser(@PathVariable id: UUID): ApiResponse<UserResponse> =
        ApiResponse.ok(userService.getUser(id))

    @PatchMapping("/{id}")
    fun updateUser(
        @PathVariable id: UUID,
        @RequestBody request: UserUpdateRequest
    ): ApiResponse<UserResponse> =
        ApiResponse.ok(userService.updateUser(id, request))
}
