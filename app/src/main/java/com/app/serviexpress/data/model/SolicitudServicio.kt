package com.app.serviexpress.data.model

data class SolicitudServicio(
    val id: String,
    val empleadorId: String,
    val categoriaId: String,
    val descripcion: String,
    val estado: String = "pendiente",
    val fechaSolicitud: Long = System.currentTimeMillis()
)