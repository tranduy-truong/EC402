package com.tranduytruong.novatech.core.domain.model

data class User(
    val id: String,
    val email: String,
    val displayName: String,
    val role: String = "customer",
    val isEmailVerified: Boolean = false,
)
