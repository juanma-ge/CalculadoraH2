package org.example.model

data class DatabaseError(val mensaje: String, val excepcion: Exception? = null)
