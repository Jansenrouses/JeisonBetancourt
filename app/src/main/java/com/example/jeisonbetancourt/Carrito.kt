package com.example.jeisonbetancourt

import android.content.Intent
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
class Carrito() : AppCompatActivity(), Parcelable {
    constructor(parcel: Parcel) : this() {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Carrito> {
        override fun createFromParcel(parcel: Parcel): Carrito {
            return Carrito(parcel)
        }

        override fun newArray(size: Int): Array<Carrito?> {
            return arrayOfNulls(size)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.carrito)

        // Botón "Productos"
        val botonProductos: Button = findViewById(R.id.productos2)
        botonProductos.setOnClickListener {
            // Crear un Intent para ir a ItemsProductosActivity
            val intent = Intent(this, ItemsProductos::class.java)
            startActivity(intent)
        }

        // Botón "Cancelar"
        val botonCancelar: Button = findViewById(R.id.button5)
        botonCancelar.setOnClickListener {
            // Crear un Intent para ir a ItemsProductosActivity
            val intent = Intent(this, ItemsProductos::class.java)
            startActivity(intent)

        }
    }
}