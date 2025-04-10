package com.app.serviexpress.data.register.provider

import com.app.serviexpress.data.model.Billetera
import com.app.serviexpress.data.model.Usuario
import com.app.serviexpress.utils.register.RegisterError
import com.app.serviexpress.utils.register.RegisterResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class GoogleRegistrationProvider(
    auth: FirebaseAuth,
    firestore: FirebaseFirestore
) : FirebaseRegistrationProvider(auth, firestore) {

    override suspend fun registerWithGoogle(idToken: String, usuario: Usuario): RegisterResult {
        return withContext(Dispatchers.IO) {
            try{
                val credential = GoogleAuthProvider.getCredential(idToken, null)
                val authResult = auth.signInWithCredential(credential).await()
                val user = authResult.user

                if(user != null){
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
                    e.message?.contains("network") == true -> {
                        RegisterResult.Failure(RegisterError.NetworkError)
                    }
                    else -> {
                        RegisterResult.Failure(RegisterError.Unknown(e.message ?: "Error desconocido"))
                    }
                }
            }
        }
    }
}