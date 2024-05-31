package com.example.jeisonbetancourt

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class CambiarContraseña : AppCompatActivity() {

    private lateinit var username: TextInputEditText
    private lateinit var oldPassword: TextInputEditText
    private lateinit var newPassword: TextInputEditText
    private lateinit var changePasswordButton: Button
    private lateinit var userDAO: UserDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cambiar_contrasena)

        userDAO = UserDAO(this)

        username = findViewById(R.id.username)
        oldPassword = findViewById(R.id.old_password)
        newPassword = findViewById(R.id.new_password)
        changePasswordButton = findViewById(R.id.change_password_button)

        changePasswordButton.setOnClickListener {
            cambiarContraseña()
        }
    }

    private fun cambiarContraseña() {
        val usernameText = username.text.toString()
        val oldPasswordText = oldPassword.text.toString()
        val newPasswordText = newPassword.text.toString()

        if (usernameText.isEmpty() || oldPasswordText.isEmpty() || newPasswordText.isEmpty()) {
            Toast.makeText(this, "Todos los campos deben estar llenos", Toast.LENGTH_SHORT).show()
            return
        }

        val user = userDAO.getUserByUsernameAndPassword(usernameText, oldPasswordText)
        if (user != null) {
            user.contrasena = newPasswordText
            userDAO.updateUser(user)
            Toast.makeText(this, "Contraseña cambiada exitosamente", Toast.LENGTH_SHORT).show()
            finish() // Cierra la actividad después de cambiar la contraseña
        } else {
            Toast.makeText(this, "Usuario o contraseña actual incorrectos", Toast.LENGTH_SHORT).show()
        }
    }
}
