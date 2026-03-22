package org.flab.ensembleroomreservationproject.common.dto

data class ApiResponse<T>(
    val success: Boolean,
    val data: T? = null,
    val error: String? = null
) {
    companion object {
        fun <T> ok(data: T): ApiResponse<T> = ApiResponse(success = true, data = data)
        fun error(message: String): ApiResponse<Nothing> = ApiResponse(success = false, error = message)
    }
}
