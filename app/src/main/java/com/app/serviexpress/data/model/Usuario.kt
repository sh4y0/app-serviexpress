package com.app.serviexpress.data.model

data class Usuario(
    val id: String,
    val nombre: String,
    val apellido: String,
    val email: String,
    val tipo: String,
    val telefono: String,
    val ubicaciones: List<String> = emptyList(),
    val rating: Double = 0.0,
    val categorias: List<String> = emptyList(),
    val billeteraId: String = ""
)