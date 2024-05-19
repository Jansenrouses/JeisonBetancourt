package com.example.jeisonbetancourt

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class MainActivity : AppCompatActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var userDAO: UserDAO

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

        // Asigna el botón de crear cuenta
        val crearCuentaButton: Button = findViewById(R.id.create_account_button)
        crearCuentaButton.setOnClickListener {
            abrirCrearCuentaActivity()
        }

        // Asigna el botón de iniciar sesión
        val iniciarSesionButton: Button = findViewById(R.id.login_button)
        iniciarSesionButton.setOnClickListener {
            iniciarSesion()
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

    private fun iniciarSesion() {
        val usernameEditText: EditText = findViewById(R.id.email)
        val passwordEditText: EditText = findViewById(R.id.password)
        val username = usernameEditText.text.toString()
        val password = passwordEditText.text.toString()

        val user = userDAO.getUser(username)
        if (user != null && user.contrasena == password) { // Asegúrate de que el nombre del campo de contraseña sea 'contrasena'
            // Inicio de sesión exitoso
            Log.d("Login", "Inicio de sesión exitoso para el usuario: $username")
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
