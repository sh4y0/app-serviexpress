package com.app.serviexpress.utils.auth

import com.google.firebase.auth.FirebaseUser

sealed class AuthResult {
    data class Success(val user: FirebaseUser?) : AuthResult()
    data class Failure(val error: AuthError) : AuthResult()
}

sealed class AuthError {
    data object InvalidCredentials : AuthError()
    data object UserNotFound : AuthError()
    data object NetworkError : AuthError()
    data class Unknown(val message: String) : AuthError()
}