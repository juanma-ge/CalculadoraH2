package org.example.model

/**
 * Data class que establece un error en la database.
 * No se usó finalmente, aunque es probable que se haga.
 */
data class DatabaseError(val mensaje: String, val excepcion: Exception? = null)
