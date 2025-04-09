package com.app.serviexpress.data.register.provider

import com.app.serviexpress.data.model.Billetera
import com.app.serviexpress.data.model.Usuario
import com.app.serviexpress.utils.register.RegisterError
import com.app.serviexpress.utils.register.RegisterResult
import com.facebook.AccessToken
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

open class FirebaseRegistrationProvider (
    internal val auth: FirebaseAuth,
    internal val firestore: FirebaseFirestore) : RegistrationProvider{

    override suspend fun registerUser(
        email: String,
        password: String,
        usuario: Usuario
    ): RegisterResult {
        return withContext(Dispatchers.IO) {
            try {
                val authResult = auth.createUserWithEmailAndPassword(email, password).await()
                val user = authResult.user

                if (user != null){
                    firestore.collection("usuario").document(user.uid).set(usuario).await()

                    val billetera = Billetera(
                        id = user.uid,
                        usuarioId = user.uid
                    )
                    firestore.collection("billetera").document(user.uid).set(billetera).await()

                    firestore.collection("usuario").document(user.uid).update("billeteraId", user.uid).await()

                    RegisterResult.Success(user)
                } else{
                    RegisterResult.Failure(RegisterError.Unknown("Error desconocido al crear el usuario"))
                }

            } catch (e: Exception){
                when {
                    e.message?.contains("email address is already in use") == true -> {
                        RegisterResult.Failure(RegisterError.EmailAlreadyInUse)
                    }

                    e.message?.contains("network") == true -> {
                        RegisterResult.Failure(RegisterError.NetworkError)
                    }

                    else -> {
                        RegisterResult.Failure(
                            RegisterError.Unknown(
                                e.message ?: "Error desconocido"
                            )
                        )
                    }
                }
            }
        }
    }

    override suspend fun registerWithGoogle(idToken: String, usuario: Usuario): RegisterResult {
        return RegisterResult.Failure(RegisterError.Unknown("Método no implementado en FirebaseRegistrationProvider"))
    }

    override suspend fun registerWithFacebook(
        accessToken: AccessToken,
        usuario: Usuario
    ): RegisterResult {
        return RegisterResult.Failure(RegisterError.Unknown("Método no implementado en FirebaseRegistrationProvider"))
    }
}