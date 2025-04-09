package com.app.serviexpress.data.auth.service

import com.app.serviexpress.data.auth.provider.AuthenticationProvider
import com.app.serviexpress.utils.auth.AuthResult
import com.facebook.AccessToken
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AuthService: KoinComponent{

    private val firebaseProvider: AuthenticationProvider by inject()
    private val googleProvider: AuthenticationProvider by inject()
    private val facebookProvider: AuthenticationProvider by inject()

    suspend fun getCurrentUser(): FirebaseUser? {
        return withContext(Dispatchers.IO) {
            firebaseProvider.getCurrentUser()
        }
    }

    suspend fun loginUser(email: String, password: String): AuthResult {
        return withContext(Dispatchers.IO) {
            firebaseProvider.loginUser(email, password)
        }
    }

    suspend fun loginWithGoogle(idToken: String): AuthResult {
        return withContext(Dispatchers.IO) {
            googleProvider.loginWithGoogle(idToken)
        }
    }

    suspend fun loginWithFacebook(accessToken: AccessToken): AuthResult {
        return withContext(Dispatchers.IO) {
            facebookProvider.loginWithFacebook(accessToken)
        }
    }

    suspend fun logoutUser() {
        return withContext(Dispatchers.IO) {
            firebaseProvider.logoutUser()
        }
    }
}