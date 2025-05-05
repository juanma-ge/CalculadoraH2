package es.prog2425.calclog.ui

import es.prog2425.calclog.data.DatabaseConfig.DB_URL
import es.prog2425.calclog.data.DatabaseConfig.PASS
import es.prog2425.calclog.data.DatabaseConfig.USER
import es.prog2425.calclog.model.User
import java.sql.Connection
import java.sql.DriverManager
import java.util.Scanner

/**
 * Implementación de entrada y salida a través de la consola estándar.
 * Proporciona métodos para mostrar mensajes, leer datos del usuario
 * y controlar la interacción mediante el teclado.
 */
class Consola : IEntradaSalida {
    private val scanner = Scanner(System.`in`)

    /**
     * Muestra un mensaje por pantalla, con o sin salto de línea final.
     */
    override fun mostrar(msj: String, salto: Boolean) {
        print("$msj${if (salto) "\n" else ""}")
    }

    /**
     * Muestra un mensaje de error formateado por pantalla.
     */
    override fun mostrarError(msj: String, salto: Boolean) {
        mostrar("ERROR - $msj", salto)
    }

    /**
     * Solicita al usuario que introduzca información por consola.
     */
    override fun pedirInfo(msj: String): String {
        if (msj.isNotEmpty()) mostrar(msj, false)
        return scanner.nextLine().trim()
    }

    /**
     * Solicita al usuario que introduzca un valor decimal.
     */
    override fun pedirDouble(msj: String) = pedirInfo(msj).replace(',', '.').toDoubleOrNull()

    /**
     * Solicita al usuario que introduzca un valor entero.
     */
    override fun pedirEntero(msj: String) = pedirInfo(msj).toIntOrNull()

    /**
     * Pregunta al usuario si desea continuar y valida la respuesta.
     */
    override fun preguntar(msj: String): Boolean {
        do {
            val respuesta = pedirInfo(msj).lowercase()
            when (respuesta) {
                "s", "si" -> return true
                "n", "no" -> return false
                else -> mostrarError("Respuesta no válida. Responde con s, n, si o no.")
            }
        } while (true)
    }

    /**
     * Limpia la pantalla simulando múltiples saltos de línea.
     */
    override fun limpiarPantalla(numSaltos: Int) {
        if (System.console() != null) {
            mostrar("\u001b[H\u001b[2J", false)
            System.out.flush()
        } else {
            repeat(numSaltos) {
                mostrar("")
            }
        }
    }

    /**
     * Pausa la ejecución esperando a que el usuario pulse ENTER.
     */
    override fun pausar(msj: String) {
        pedirInfo("\n" + msj)
        mostrar()
    }

    fun getConnection(): Connection =
        DriverManager.getConnection(DB_URL, USER, PASS)

    fun initDatabase() {
        getConnection().use { conn ->
            val stmt = conn.createStatement()
            stmt.executeUpdate(
                """CREATE TABLE IF NOT EXISTS users (
                id INT AUTO_INCREMENT PRIMARY KEY,
                name VARCHAR(255),
                email VARCHAR(255)
            )"""
            )
            stmt.close()
        }
    }

    fun createUser(name: String, email: String) {
        getConnection().use { conn ->
            val stmt = conn.prepareStatement("INSERT INTO users (name, email) VALUES (?, ?)")
            stmt.setString(1, name)
            stmt.setString(2, email)
            stmt.executeUpdate()
        }
    }

    fun readUsers(): List<User> {
        val users = mutableListOf<User>()
        getConnection().use { conn ->
            val stmt = conn.createStatement()
            val rs = stmt.executeQuery("SELECT * FROM users")
            while (rs.next()) {
                users += User(rs.getInt("id"), rs.getString("name"), rs.getString("email"))
            }
        }
        return users
    }

    fun updateUser(id: Int, name: String, email: String) {
        getConnection().use { conn ->
            val stmt = conn.prepareStatement("UPDATE users SET name = ?, email = ? WHERE id = ?")
            stmt.setString(1, name)
            stmt.setString(2, email)
            stmt.setInt(3, id)
            stmt.executeUpdate()
        }
    }

    fun deleteUser(id: Int) {
        getConnection().use { conn ->
            val stmt = conn.prepareStatement("DELETE FROM users WHERE id = ?")
            stmt.setInt(1, id)
            stmt.executeUpdate()
        }
    }


}

