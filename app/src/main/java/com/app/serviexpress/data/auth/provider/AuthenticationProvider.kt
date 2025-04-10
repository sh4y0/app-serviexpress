package com.app.serviexpress.data.auth.provider

import com.app.serviexpress.utils.auth.AuthResult
import com.facebook.AccessToken
import com.google.firebase.auth.FirebaseUser

interface AuthenticationProvider {
    suspend fun getCurrentUser(): FirebaseUser?
    suspend fun loginUser(email: String, password: String): AuthResult
    suspend fun loginWithGoogle(idToken: String): AuthResult
    suspend fun loginWithFacebook(accessToken: AccessToken): AuthResult
    suspend fun logoutUser()
}