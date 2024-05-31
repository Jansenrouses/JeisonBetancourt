package com.example.jeisonbetancourt

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var userDAO: UserDAO
    private lateinit var usernameLayout: TextInputLayout
    private lateinit var passwordLayout: TextInputLayout
    private lateinit var nombreusuario: TextInputEditText
    private lateinit var password: TextInputEditText
    private lateinit var loginButton: Button
    private lateinit var createAccountButton: Button
    private lateinit var changePasswordButton: Button
    private lateinit var deleteAccountButton: Button

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        userDAO = UserDAO(this)

        // Verificar permisos
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Solicitar permisos
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        } else {
            // Permisos concedidos, obtener ubicación
            requestLocation()
        }

        // Inicializar vistas
        usernameLayout = findViewById(R.id.email_layout)
        passwordLayout = findViewById(R.id.password_layout)
        nombreusuario = findViewById(R.id.email)
        password = findViewById(R.id.password)
        loginButton = findViewById(R.id.login_button)
        createAccountButton = findViewById(R.id.create_account_button)
        changePasswordButton = findViewById(R.id.change_password_button)
        deleteAccountButton = findViewById(R.id.delete_account_button)

        // Asignar acciones a los botones
        loginButton.setOnClickListener {
            if (validarEntrada()) {
                iniciarSesion()
            }
        }

        createAccountButton.setOnClickListener {
            abrirCrearCuentaActivity()
        }

        changePasswordButton.setOnClickListener {
            abrirCambiarContraseñaActivity()
        }

        deleteAccountButton.setOnClickListener {
            abrirEliminarCuentaActivity()
        }
    }

    private fun requestLocation() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location ->
                    if (location != null) {
                        val latitude = location.latitude
                        val longitude = location.longitude
                        // Hacer algo con la ubicación (por ejemplo, mostrar en un TextView)
                        // txtLocation.text = "Latitud: $latitude, Longitud: $longitude"
                    }
                }
                .addOnFailureListener { e ->
                    Log.e("MainActivity", "Error al obtener la ubicación", e)
                }
        } else {
            // Permisos no están concedidos
            Toast.makeText(this, "Permisos de ubicación no concedidos", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            LOCATION_PERMISSION_REQUEST_CODE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // Permiso concedido, obtener ubicación
                    requestLocation()
                } else {
                    // Permiso denegado
                    Toast.makeText(this, "Permiso de ubicación denegado", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun validarEntrada(): Boolean {
        var isValid = true

        // Validar nombre de usuario
        val nombreusuarioText = nombreusuario.text.toString()
        if (nombreusuarioText.isEmpty()) {
            usernameLayout.error = "El nombre de usuario no puede estar vacío"
            isValid = false
        } else {
            usernameLayout.error = null
        }

        // Validar contraseña
        val passwordText = password.text.toString()
        if (passwordText.isEmpty()) {
            passwordLayout.error = "La contraseña no puede estar vacía"
            isValid = false
        } else {
            passwordLayout.error = null
        }

        return isValid
    }

    private fun iniciarSesion() {
        // Lógica de inicio de sesión
        val nombreusuarioText = nombreusuario.text.toString()
        val passwordText = password.text.toString()

        val user = userDAO.getUserByUsernameAndPassword(nombreusuarioText, passwordText)
        if (user != null) {
            // Inicio de sesión exitoso
            Log.d("Login", "Inicio de sesión exitoso para el usuario: ${user.nombreusuario}")
            val intent = Intent(this, ItemsProductos::class.java)
            startActivity(intent)
        } else {
            // Usuario o contraseña incorrectos
            Log.e("Login", "Error: Usuario o contraseña incorrectos")
            Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun abrirCrearCuentaActivity() {
        val intent = Intent(this, CrearCuentaActivity::class.java)
        startActivity(intent)
    }

    private fun abrirCambiarContraseñaActivity() {
        val intent = Intent(this, CambiarContraseña::class.java)
        startActivity(intent)
    }

    private fun abrirEliminarCuentaActivity() {
        val intent = Intent(this, EliminarCuenta::class.java)
        startActivity(intent)
    }

    // Método para abrir la actividad de "Items de Productos"
    fun abriritems_productos(view: View) {
        val intent = Intent(this, ItemsProductos::class.java)
        startActivity(intent)
    }

    // Método para abrir la actividad de "Carrito"
    fun abrirCarrito(view: View) {
        val intent = Intent(this, Carrito::class.java)
        startActivity(intent)
    }
}
