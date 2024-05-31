package com.example.jeisonbetancourt

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class EliminarCuenta : AppCompatActivity() {

    private lateinit var username: TextInputEditText
    private lateinit var deleteAccountButton: Button
    private lateinit var userDAO: UserDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.eliminar_cuenta)

        userDAO = UserDAO(this)

        username = findViewById(R.id.username)
        deleteAccountButton = findViewById(R.id.delete_account_button)

        deleteAccountButton.setOnClickListener {
            eliminarCuenta()
        }
    }

    private fun eliminarCuenta() {
        val usernameText = username.text.toString()

        if (usernameText.isEmpty()) {
            Toast.makeText(this, "El nombre de usuario no puede estar vacío", Toast.LENGTH_SHORT).show()
            return
        }

        val user = userDAO.getUserByUsername(usernameText)
        if (user != null) {
            userDAO.deleteUser(user)
            Toast.makeText(this, "Cuenta eliminada exitosamente", Toast.LENGTH_SHORT).show()
            finish() // Cierra la actividad después de eliminar la cuenta
        } else {
            Toast.makeText(this, "Usuario no encontrado", Toast.LENGTH_SHORT).show()
        }
    }
}
