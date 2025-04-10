package com.app.serviexpress.data.model

data class Pago(
    val id: String,
    val solicitudId: String,
    val billeteraEmpleadorId: String,
    val billeteraTrabajadorId: String,
    val monto: Double = 0.0,
    val fechaPago: Long = System.currentTimeMillis()
)