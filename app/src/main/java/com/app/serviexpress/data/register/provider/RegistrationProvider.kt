package com.app.serviexpress.data.register.provider

import com.app.serviexpress.data.model.Usuario
import com.app.serviexpress.utils.register.RegisterResult
import com.facebook.AccessToken

interface RegistrationProvider {
    suspend fun registerUser(email: String, password: String, usuario: Usuario): RegisterResult
    suspend fun registerWithGoogle(idToken: String, usuario: Usuario): RegisterResult
    suspend fun registerWithFacebook(accessToken: AccessToken, usuario: Usuario): RegisterResult
}