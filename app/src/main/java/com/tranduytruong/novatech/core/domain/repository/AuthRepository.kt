package com.tranduytruong.novatech.core.domain.repository

import com.tranduytruong.novatech.core.domain.model.User
import com.tranduytruong.novatech.core.domain.util.AppResult
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val currentUser: User?
    fun observeAuthState(): Flow<User?>
    suspend fun signIn(email: String, password: String): AppResult<User>
    suspend fun signUp(name: String, email: String, password: String): AppResult<User>
    fun signOut()
}
