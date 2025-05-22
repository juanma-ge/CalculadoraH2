package org.example.model

import java.time.LocalDateTime

data class Operacion(
    val id: Int? = null,
    val numero1: Double,
    val numero2: Double,
    val operador: String,
    val resultado: Double,
    val fecha: LocalDateTime? = null
)
