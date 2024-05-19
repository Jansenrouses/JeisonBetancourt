package com.example.jeisonbetancourt

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CrearCuentaActivity : AppCompatActivity() {

    // Declaración de la variable userDAO
    private lateinit var userDAO: UserDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.crear_cuenta)

        // Inicialización de userDAO
        userDAO = UserDAO(this)

        val botonCancelar: Button = findViewById(R.id.cancelar)
        botonCancelar.setOnClickListener {
            finish()
        }

        // Obtener referencia al botón "Crear cuenta"
        val crearCuentaButton: Button = findViewById(R.id.crear)

        // Configurar OnClickListener para el botón "Crear cuenta"
        crearCuentaButton.setOnClickListener {
            // Obtener referencias a los EditText para el nombre de usuario, la contraseña y la confirmación de la contraseña
            val usernameEditText: EditText = findViewById(R.id.editTextTextEmailAddress)
            val passwordEditText: EditText = findViewById(R.id.editTextTextPassword2)
            val confirmPasswordEditText: EditText = findViewById(R.id.editTextTextPassword3)

            // Obtener los valores ingresados por el usuario
            val username: String = usernameEditText.text.toString()
            val password: String = passwordEditText.text.toString()
            val confirmPassword: String = confirmPasswordEditText.text.toString()

            // Verificar si los campos están vacíos
            if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                // Mostrar un mensaje indicando que los campos son obligatorios
                Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            } else if (password != confirmPassword) {
                // Mostrar un mensaje indicando que las contraseñas no coinciden
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            } else {
                // Los campos no están vacíos y las contraseñas coinciden, por lo tanto, podemos proceder a crear la cuenta

                // Aquí iría el código para guardar el nuevo usuario en la base de datos
                // Por ejemplo, podrías llamar a un método en tu clase UserDAO para agregar el nuevo usuario
                val newUser = User(nombreusuario = username, contrasena = password) // Proporcionar todos los argumentos requeridos

                val userId = userDAO.addUser(newUser)

                // Verificar si la inserción fue exitosa
                if (userId != -1L) {
                    // Mostrar un mensaje indicando que la cuenta se creó con éxito
                    Toast.makeText(this, "¡Cuenta creada con éxito!", Toast.LENGTH_SHORT).show()

                    // Opcionalmente, podrías navegar a otra actividad después de crear la cuenta
                    // Por ejemplo:
                    // startActivity(Intent(this, OtraActividad::class.java))
                } else {
                    // Mostrar un mensaje indicando que ocurrió un error al crear la cuenta
                    Toast.makeText(this, "Error al crear la cuenta. Por favor, inténtelo de nuevo.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

