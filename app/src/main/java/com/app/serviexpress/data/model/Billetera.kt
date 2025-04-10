package com.app.serviexpress.data.model

data class Billetera(
    val id: String,
    val usuarioId: String? = null,
    val empresaId: String? = null,
    val saldo: Double = 0.0
)