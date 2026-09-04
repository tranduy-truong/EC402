package com.tranduytruong.novatech

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

enum class AuthMode { SIGN_IN, SIGN_UP }

data class AccountUiState(
    val mode: AuthMode = AuthMode.SIGN_IN,
    val fullName: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val user: FirebaseUser? = null,
    val profileName: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val success: String? = null,
)

class AccountViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    var uiState by mutableStateOf(
        AccountUiState(
            user = auth.currentUser,
            profileName = auth.currentUser?.displayName.orEmpty(),
            email = auth.currentUser?.email.orEmpty(),
        )
    )
        private set

    private val authStateListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
        val user = firebaseAuth.currentUser
        uiState = uiState.copy(
            user = user,
            profileName = user?.displayName.orEmpty(),
            email = user?.email.orEmpty(),
            isLoading = false,
        )
        if (user != null) loadProfile(user)
    }

    init {
        auth.addAuthStateListener(authStateListener)
        auth.currentUser?.let(::loadProfile)
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
        if (uiState.mode == AuthMode.SIGN_IN) signIn() else signUp()
    }

    fun signOut() {
        auth.signOut()
        uiState = AccountUiState()
    }

    private fun signIn() {
        val email = uiState.email.trim()
        val password = uiState.password
        uiState = uiState.copy(isLoading = true, error = null, success = null)

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            uiState = if (task.isSuccessful) {
                uiState.copy(isLoading = false, success = "Đăng nhập thành công.")
            } else {
                uiState.copy(isLoading = false, error = authError(task.exception))
            }
        }
    }

    private fun signUp() {
        val name = uiState.fullName.trim()
        val email = uiState.email.trim()
        val password = uiState.password
        uiState = uiState.copy(isLoading = true, error = null, success = null)

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                uiState = uiState.copy(isLoading = false, error = authError(task.exception))
                return@addOnCompleteListener
            }
            val user = task.result?.user
            if (user == null) {
                uiState = uiState.copy(isLoading = false, error = authError(task.exception))
                return@addOnCompleteListener
            }
            saveProfile(user, name, email)
        }
    }

    private fun saveProfile(user: FirebaseUser, name: String, email: String) {
        val request = UserProfileChangeRequest.Builder().setDisplayName(name).build()
        val profile = mapOf<String, Any>(
            "name" to name,
            "email" to email,
            "role" to "customer",
            "createdAt" to FieldValue.serverTimestamp(),
        )

        user.updateProfile(request).addOnCompleteListener { profileTask ->
            firestore.collection("users").document(user.uid).set(profile)
                .addOnCompleteListener { firestoreTask ->
                    uiState = uiState.copy(
                        user = user,
                        profileName = name,
                        email = email,
                        isLoading = false,
                        success = if (profileTask.isSuccessful && firestoreTask.isSuccessful) {
                            "Tạo tài khoản thành công."
                        } else {
                            "Tài khoản đã tạo, nhưng hồ sơ chưa được lưu đầy đủ."
                        },
                        error = null,
                    )
                }
        }
    }

    private fun loadProfile(user: FirebaseUser) {
        firestore.collection("users").document(user.uid).get()
            .addOnSuccessListener { document ->
                val name = document.getString("name")
                    ?: user.displayName
                    ?: "Khách hàng NovaTech"
                uiState = uiState.copy(profileName = name, email = user.email.orEmpty())
            }
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

    private fun authError(exception: Exception?): String = when (exception) {
        is FirebaseAuthUserCollisionException -> "Email này đã được đăng ký."
        is FirebaseAuthInvalidUserException -> "Không tìm thấy tài khoản với email này."
        is FirebaseAuthInvalidCredentialsException -> "Email hoặc mật khẩu không chính xác."
        is FirebaseNetworkException -> "Không thể kết nối mạng. Vui lòng thử lại."
        else -> exception?.localizedMessage ?: "Đã xảy ra lỗi. Vui lòng thử lại."
    }

    override fun onCleared() {
        auth.removeAuthStateListener(authStateListener)
        super.onCleared()
    }
}
