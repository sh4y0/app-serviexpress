package com.app.serviexpress.utils.register

import com.google.firebase.auth.FirebaseUser

sealed class RegisterResult {
    data class Success(val user: FirebaseUser?) : RegisterResult()
    data class Failure(val error: RegisterError) : RegisterResult()
}

sealed class RegisterError {
    data object EmailAlreadyInUse : RegisterError()
    data object NetworkError : RegisterError()
    data class Unknown(val message: String) : RegisterError()
}