package com.tranduytruong.novatech.core.data.repository

import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.tranduytruong.novatech.core.domain.model.User
import com.tranduytruong.novatech.core.domain.repository.AuthRepository
import com.tranduytruong.novatech.core.domain.util.AppError
import com.tranduytruong.novatech.core.domain.util.AppResult
import javax.inject.Inject
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.tasks.await

class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
) : AuthRepository {
    override val currentUser: User?
        get() = auth.currentUser?.toDomain()

    override fun observeAuthState(): Flow<User?> = callbackFlow {
        val listener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            trySend(firebaseAuth.currentUser?.toDomain())
        }
        auth.addAuthStateListener(listener)
        awaitClose { auth.removeAuthStateListener(listener) }
    }.distinctUntilChanged()

    override suspend fun signIn(email: String, password: String): AppResult<User> = try {
        val firebaseUser = auth.signInWithEmailAndPassword(email, password).await().user
            ?: return AppResult.Error(AppError.Authentication("Không thể đọc thông tin tài khoản."))
        AppResult.Success(firebaseUser.toDomain())
    } catch (exception: Exception) {
        AppResult.Error(exception.toAppError())
    }

    override suspend fun signUp(
        name: String,
        email: String,
        password: String,
    ): AppResult<User> = try {
        val firebaseUser = auth.createUserWithEmailAndPassword(email, password).await().user
            ?: return AppResult.Error(AppError.Authentication("Không thể tạo tài khoản."))

        val profileRequest = UserProfileChangeRequest.Builder()
            .setDisplayName(name)
            .build()
        firebaseUser.updateProfile(profileRequest).await()

        firestore.collection(USERS_COLLECTION).document(firebaseUser.uid).set(
            mapOf(
                "name" to name,
                "email" to email,
                "role" to CUSTOMER_ROLE,
                "createdAt" to FieldValue.serverTimestamp(),
            )
        ).await()

        AppResult.Success(firebaseUser.toDomain(displayName = name))
    } catch (exception: Exception) {
        AppResult.Error(exception.toAppError())
    }

    override fun signOut() {
        auth.signOut()
    }

    private companion object {
        const val USERS_COLLECTION = "users"
        const val CUSTOMER_ROLE = "customer"
    }
}

private fun FirebaseUser.toDomain(displayName: String = this.displayName.orEmpty()) = User(
    id = uid,
    email = email.orEmpty(),
    displayName = displayName,
    isEmailVerified = isEmailVerified,
)

private fun Exception.toAppError(): AppError = when (this) {
    is FirebaseAuthUserCollisionException ->
        AppError.Authentication("Email này đã được đăng ký.")
    is FirebaseAuthInvalidUserException ->
        AppError.Authentication("Không tìm thấy tài khoản với email này.")
    is FirebaseAuthInvalidCredentialsException ->
        AppError.Authentication("Email hoặc mật khẩu không chính xác.")
    is FirebaseAuthWeakPasswordException ->
        AppError.Authentication("Mật khẩu chưa đủ mạnh.")
    is FirebaseNetworkException ->
        AppError.Network("Không thể kết nối mạng. Vui lòng thử lại.")
    is FirebaseFirestoreException ->
        AppError.Database("Tài khoản đã tạo nhưng chưa thể lưu hồ sơ. Vui lòng thử lại.")
    else -> AppError.Unknown(localizedMessage ?: "Đã xảy ra lỗi. Vui lòng thử lại.")
}
