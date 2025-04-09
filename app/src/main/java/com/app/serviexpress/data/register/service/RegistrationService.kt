package com.app.serviexpress.data.register.service

import com.app.serviexpress.data.model.Usuario
import com.app.serviexpress.data.register.provider.RegistrationProvider
import com.app.serviexpress.utils.register.RegisterResult
import com.facebook.AccessToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class RegistrationService : KoinComponent {

    private val firebaseProvider: RegistrationProvider by inject()
    private val googleProvider: RegistrationProvider by inject()
    private val facebookProvider: RegistrationProvider by inject()

    suspend fun registerUserWithEmail(email: String, password: String, usuario: Usuario): RegisterResult {
        return withContext(Dispatchers.IO){
            firebaseProvider.registerUser(email, password, usuario)
        }
    }

    suspend fun registerWithGoogle(idToken: String, usuario: Usuario): RegisterResult {
        return withContext(Dispatchers.IO) {
            googleProvider.registerWithGoogle(idToken, usuario)
        }
    }

    suspend fun registerWithFacebook(accessToken: AccessToken, usuario: Usuario): RegisterResult {
        return withContext(Dispatchers.IO) {
            facebookProvider.registerWithFacebook(accessToken, usuario)
        }
    }
}