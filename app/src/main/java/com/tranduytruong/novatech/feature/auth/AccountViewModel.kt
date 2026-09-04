package com.tranduytruong.novatech.feature.auth

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tranduytruong.novatech.core.domain.model.User
import com.tranduytruong.novatech.core.domain.repository.AuthRepository
import com.tranduytruong.novatech.core.domain.util.AppResult
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

enum class AuthMode { SIGN_IN, SIGN_UP }

data class AccountUiState(
    val mode: AuthMode = AuthMode.SIGN_IN,
    val fullName: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val user: User? = null,
    val profileName: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val success: String? = null,
)

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {
    private val initialUser = authRepository.currentUser

    var uiState by mutableStateOf(
        AccountUiState(
            user = initialUser,
            profileName = initialUser?.displayName.orEmpty(),
            email = initialUser?.email.orEmpty(),
        )
    )
        private set

    init {
        viewModelScope.launch {
            authRepository.observeAuthState().collectLatest { user ->
                uiState = uiState.copy(
                    user = user,
                    profileName = user?.displayName.orEmpty(),
                    email = user?.email ?: uiState.email,
                    isLoading = false,
                )
            }
        }
    }

    fun selectMode(mode: AuthMode) {
        uiState = uiState.copy(
            mode = mode,
            password = "",
            confirmPassword = "",
            error = null,
            success = null,
        )
    }

    fun updateFullName(value: String) {
        uiState = uiState.copy(fullName = value, error = null)
    }

    fun updateEmail(value: String) {
        uiState = uiState.copy(email = value, error = null)
    }

    fun updatePassword(value: String) {
        uiState = uiState.copy(password = value, error = null)
    }

    fun updateConfirmPassword(value: String) {
        uiState = uiState.copy(confirmPassword = value, error = null)
    }

    fun submit() {
        if (uiState.isLoading) return
        val validationError = validate()
        if (validationError != null) {
            uiState = uiState.copy(error = validationError, success = null)
            return
        }

        viewModelScope.launch {
            val name = uiState.fullName.trim()
            val email = uiState.email.trim()
            val password = uiState.password
            val mode = uiState.mode
            uiState = uiState.copy(isLoading = true, error = null, success = null)

            val result = if (mode == AuthMode.SIGN_IN) {
                authRepository.signIn(email, password)
            } else {
                authRepository.signUp(name, email, password)
            }

            uiState = when (result) {
                is AppResult.Success -> uiState.copy(
                    user = result.data,
                    profileName = result.data.displayName,
                    email = result.data.email,
                    isLoading = false,
                    error = null,
                    success = if (mode == AuthMode.SIGN_IN) {
                        "Đăng nhập thành công."
                    } else {
                        "Tạo tài khoản thành công."
                    },
                )
                is AppResult.Error -> uiState.copy(
                    isLoading = false,
                    error = result.error.message,
                    success = null,
                )
            }
        }
    }

    fun signOut() {
        authRepository.signOut()
        uiState = AccountUiState()
    }

    private fun validate(): String? {
        if (uiState.mode == AuthMode.SIGN_UP && uiState.fullName.trim().length < 2) {
            return "Vui lòng nhập họ tên đầy đủ."
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(uiState.email.trim()).matches()) {
            return "Email không hợp lệ."
        }
        if (uiState.password.length < 6) {
            return "Mật khẩu phải có ít nhất 6 ký tự."
        }
        if (uiState.mode == AuthMode.SIGN_UP && uiState.password != uiState.confirmPassword) {
            return "Mật khẩu xác nhận không khớp."
        }
        return null
    }
}
