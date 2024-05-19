package com.example.jeisonbetancourt

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class ItemsProductos : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.items_productos)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val cartButton: View = findViewById(R.id.boton_carrito)
        cartButton.setOnClickListener {
            val intent = Intent(this, Carrito::class.java)
            startActivity(intent)
        }
    }
}
