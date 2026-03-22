package org.flab.ensembleroomreservationproject.user.repository

import org.flab.ensembleroomreservationproject.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository : JpaRepository<User, UUID> {
    fun findByTossUserId(tossUserId: String): User?
}
