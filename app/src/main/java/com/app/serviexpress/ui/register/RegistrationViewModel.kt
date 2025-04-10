package com.app.serviexpress.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.serviexpress.data.model.Usuario
import com.app.serviexpress.data.register.service.RegistrationService
import com.app.serviexpress.utils.register.RegisterError
import com.app.serviexpress.utils.register.RegisterResult
import com.facebook.AccessToken
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.net.UnknownHostException

class RegistrationViewModel: ViewModel(), KoinComponent {

    private val registrationService: RegistrationService by inject()

    private val _user = MutableLiveData<FirebaseUser?>()
    val user: LiveData<FirebaseUser?> = _user

    private val _registerResult = MutableLiveData<RegisterResult>()
    val registerResult: LiveData<RegisterResult> = _registerResult

    private val _error = MutableLiveData<RegisterError>()
    val error: LiveData<RegisterError> = _error

    private suspend fun <T> authenticate(
        operation: suspend () -> T,
        onSuccess: (T) -> Unit,
        onError: (RegisterError) -> Unit
    ) {
        try {
            val result = operation()
            onSuccess(result)
        } catch (e: Exception) {
            when (e) {
                is UnknownHostException -> onError(RegisterError.NetworkError)
                else -> onError(RegisterError.Unknown(e.message ?: "Error desconocido"))
            }
        }
    }

    fun registerUser(email: String, password: String, usuario: Usuario) {
        viewModelScope.launch {
            authenticate(
                operation = { registrationService.registerUserWithEmail(email, password, usuario) },
                onSuccess = { result ->
                    _registerResult.value = result
                    if (result is RegisterResult.Success) {
                        _user.value = result.user
                    }
                },
                onError = { error ->
                    _error.value = error
                    _registerResult.value = RegisterResult.Failure(error)
                }
            )
        }
    }

    fun registerWithGoogle(idToken: String, usuario: Usuario) {
        viewModelScope.launch {
            authenticate(
                operation = { registrationService.registerWithGoogle(idToken, usuario) },
                onSuccess = { result ->
                    _registerResult.value = result
                    if (result is RegisterResult.Success) {
                        _user.value = result.user
                    }
                },
                onError = { error ->
                    _error.value = error
                    _registerResult.value = RegisterResult.Failure(error)
                }
            )
        }
    }

    fun registerWithFacebook(accessToken: AccessToken, usuario: Usuario) {
        viewModelScope.launch {
            authenticate(
                operation = { registrationService.registerWithFacebook(accessToken, usuario) },
                onSuccess = { result ->
                    _registerResult.value = result
                    if (result is RegisterResult.Success) {
                        _user.value = result.user
                    }
                },
                onError = { error ->
                    _error.value = error
                    _registerResult.value = RegisterResult.Failure(error)
                }
            )
        }
    }


}