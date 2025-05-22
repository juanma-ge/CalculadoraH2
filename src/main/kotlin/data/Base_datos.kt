package es.prog2425.calclog.data

import es.prog2425.calclog.model.User
import java.sql.*

object DatabaseConfig {
    const val DB_URL = "jdbc:h2:./data/calculadora"
    const val USER = "sa"
    const val PASS = ""

    fun getConnection(): Connection =
        DriverManager.getConnection(DB_URL, USER, PASS)

    fun initDatabase() {
        getConnection().use { conn ->
            conn.createStatement().use { stmt ->
                stmt.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS operaciones (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        numero1 DOUBLE NOT NULL,
                        numero2 DOUBLE NOT NULL,
                        operador VARCHAR(5) NOT NULL,
                        resultado DOUBLE NOT NULL,
                        fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                    )
                """)
            }
        }
    }

    fun eliminarTabla() {
        getConnection().use { conn ->
            conn.createStatement().use { stmt ->
                stmt.executeUpdate("DROP TABLE IF EXISTS users")
                println("Tabla eliminada correctamente")
            }
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