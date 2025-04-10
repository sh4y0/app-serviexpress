package com.app.serviexpress.data.auth.provider


import com.app.serviexpress.utils.auth.AuthError
import com.app.serviexpress.utils.auth.AuthResult
import com.facebook.AccessToken
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import kotlin.coroutines.resumeWithException

class FacebookAuthProvider(private val auth: FirebaseAuth) : AuthenticationProvider {

    override suspend fun getCurrentUser(): FirebaseUser? {
        return withContext(Dispatchers.IO) {
            suspendCancellableCoroutine { continuation ->
                val user = auth.currentUser
                if (user != null) {
                    continuation.resume(user) { _, _, _ -> }
                } else {
                    continuation.resumeWithException(Exception("No hay usuario logueado"))
                }
            }
        }
    }

    override suspend fun loginUser(email: String, password: String): AuthResult {
        return AuthResult.Failure(AuthError.Unknown("Método no implementado en FacebookAuthProvider"))
    }

    override suspend fun loginWithGoogle(idToken: String): AuthResult {
        return AuthResult.Failure(AuthError.Unknown("Método no implementado en FacebookAuthProvider"))
    }

    override suspend fun loginWithFacebook(accessToken: AccessToken): AuthResult {
        return withContext(Dispatchers.IO) {
            try {
                val credential = FacebookAuthProvider.getCredential(accessToken.token)
                val authResult = auth.signInWithCredential(credential).await()
                val user = authResult.user
                if (user != null) {
                    AuthResult.Success(user)
                } else {
                    AuthResult.Failure(AuthError.UserNotFound)
                }
            } catch (e: Exception) {
                when {
                    e.message?.contains("network") == true -> AuthResult.Failure(AuthError.NetworkError)
                    else -> AuthResult.Failure(AuthError.Unknown(e.message ?: "Error de autenticación con Facebook"))
                }
            }
        }

    }

    override suspend fun logoutUser() {
        return withContext(Dispatchers.IO) {
            auth.signOut()
        }
    }
}