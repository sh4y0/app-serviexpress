package com.app.serviexpress.di

import com.app.serviexpress.data.auth.provider.AuthenticationProvider
import com.app.serviexpress.data.auth.provider.FacebookAuthProvider
import com.app.serviexpress.data.auth.provider.FirebaseAuthProvider
import com.app.serviexpress.data.auth.provider.GoogleAuthProvider
import com.app.serviexpress.data.auth.service.AuthService
import com.app.serviexpress.data.register.provider.FacebookRegistrationProvider
import com.app.serviexpress.data.register.provider.FirebaseRegistrationProvider
import com.app.serviexpress.data.register.provider.GoogleRegistrationProvider
import com.app.serviexpress.data.register.provider.RegistrationProvider
import com.app.serviexpress.data.register.service.RegistrationService
import com.app.serviexpress.ui.auth.AuthViewModel
import com.app.serviexpress.ui.register.RegistrationViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

/**
 * Módulos de inyección de dependencias.
 */
val authModule = module {
    single { FirebaseAuth.getInstance() }

    single<AuthenticationProvider> { FirebaseAuthProvider(get()) }
    single<AuthenticationProvider> { GoogleAuthProvider(get()) }
    single<AuthenticationProvider> { FacebookAuthProvider(get()) }

    single { AuthService() }

    viewModel { AuthViewModel() }
}

val registerModule = module {
    single { FirebaseAuth.getInstance() }
    single { FirebaseFirestore.getInstance() }

    single<RegistrationProvider> { FirebaseRegistrationProvider(get(), get()) }
    single<RegistrationProvider> { GoogleRegistrationProvider(get(), get()) }
    single<RegistrationProvider> { FacebookRegistrationProvider(get(), get()) }

    single { RegistrationService() }

    viewModel { RegistrationViewModel() }
}