package com.app.serviexpress.data.model

data class Servicio(
    val id: String,
    val solicitudId: String,
    val empleadorId: String,
    val trabajadorId: String? = null,
    val empresaId: String? = null,
    val fechaInicio: Long = System.currentTimeMillis(),
    val fechaFin: Long? = null,
    val estado: String = "en_proceso"
)