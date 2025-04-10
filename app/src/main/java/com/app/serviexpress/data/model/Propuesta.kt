package com.app.serviexpress.data.model

data class Propuesta(
    val id: String,
    val solicitudId: String,
    val trabajadorId: String? = null,
    val empresaId: String? = null,
    val descripcion: String,
    val precio: Double = 0.0,
    val estado: String = "pendiente"
)