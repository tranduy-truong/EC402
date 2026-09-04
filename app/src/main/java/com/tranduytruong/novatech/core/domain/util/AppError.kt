package com.tranduytruong.novatech.core.domain.util

sealed interface AppError {
    val message: String

    data class Authentication(override val message: String) : AppError
    data class Database(override val message: String) : AppError
    data class Network(override val message: String) : AppError
    data class Unknown(override val message: String) : AppError
}
