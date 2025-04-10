package com.app.serviexpress.data.model

data class Empresa(
    val id: String,
    val nombre: String,
    val ruc: String,
    val email: String,
    val telefono: String,
    val ubicaciones: List<String> = emptyList(),
    val rating: Double = 0.0,
    val categorias: List<String> = emptyList(),
    val trabajadores: List<String> = emptyList(),
    val billeteraId: String
)