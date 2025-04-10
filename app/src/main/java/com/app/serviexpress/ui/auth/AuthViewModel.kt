package com.app.serviexpress.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.serviexpress.data.auth.service.AuthService
import com.app.serviexpress.utils.auth.AuthError
import com.app.serviexpress.utils.auth.AuthResult
import com.facebook.AccessToken
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.net.UnknownHostException

class AuthViewModel: ViewModel(), KoinComponent {

    private val authService: AuthService by inject()

    private val _user = MutableLiveData<FirebaseUser?>()
    val user: LiveData<FirebaseUser?> = _user

    private val _authResult = MutableLiveData<AuthResult>()
    val authResult: LiveData<AuthResult> = _authResult

    private val _error = MutableLiveData<AuthError>()
    val error: LiveData<AuthError> = _error

    private suspend fun <T> authenticate(
        operation: suspend () -> T,
        onSuccess: (T) -> Unit,
        onError: (AuthError) -> Unit
    ){
        try{
            val result = operation()
            onSuccess(result)
        } catch (e: Exception){
            when(e){
                is UnknownHostException -> onError(AuthError.NetworkError)
                else -> onError(AuthError.Unknown(e.message ?: "Error desconocido"))
            }
        }
    }

    fun getCurrentUser(){
        viewModelScope.launch {
            authenticate(
                operation = { authService.getCurrentUser() ?: throw Exception("No hay usuario autenticado") },
                onSuccess = { user ->
                    _user.value = user
                    _authResult.value = AuthResult.Success(user)
                },
                onError = { error ->
                    _error.value = error
                    _authResult.value = AuthResult.Failure(error)
                }
            )
        }
    }

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            authenticate(
                operation = { authService.loginUser(email, password)},
                onSuccess = { result ->
                    _authResult.value = result
                    if(result is AuthResult.Success){
                        _user.value = result.user
                    }
                },
                onError = { error ->
                    _error.value = error
                    _authResult.value = AuthResult.Failure(error)
                }
            )
        }
    }

    fun loginWithGoogle(idToken: String) {
        viewModelScope.launch {
            authenticate(
                operation = { authService.loginWithGoogle(idToken) },
                onSuccess = { result ->
                    _authResult.value = result
                    if(result is AuthResult.Success){
                        _user.value = result.user
                    }
                },
                onError = { error ->
                    _error.value = error
                    _authResult.value = AuthResult.Failure(error)
                }
            )
        }
    }

    fun loginWithFacebook(accessToken: AccessToken) {
        viewModelScope.launch {
            authenticate(
                operation = { authService.loginWithFacebook(accessToken) },
                onSuccess = { result ->
                    _authResult.value = result
                    if(result is AuthResult.Success){
                        _user.value = result.user
                    }
                },
                onError = { error ->
                    _error.value = error
                    _authResult.value = AuthResult.Failure(error)
                }
            )
        }
    }

    fun logoutUser() {
        viewModelScope.launch {
            authenticate(
                operation = { authService.logoutUser() },
                onSuccess = {
                    _user.value = null
                    _authResult.value = AuthResult.Success(null)
                },
                onError = { error ->
                    _error.value = error
                    _authResult.value = AuthResult.Failure(error)
                }
            )
        }
    }
}