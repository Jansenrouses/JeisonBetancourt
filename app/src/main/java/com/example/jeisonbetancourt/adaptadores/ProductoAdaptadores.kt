package com.example.jeisonbetancourt.adaptadores

import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.jeisonbetancourt.R
import com.example.jeisonbetancourt.modelos.producto

class ProductoAdaptadores(val productos: ArrayList<producto>) : RecyclerView.Adapter<ProductoAdaptadores.ViewHolder>(), Parcelable {

    constructor(parcel: Parcel) : this(
           parcel.readArrayList(producto::class.java.classLoader) as ArrayList<producto>

    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeList(productos)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ProductoAdaptadores> {
        override fun createFromParcel(parcel: Parcel): ProductoAdaptadores {
            return ProductoAdaptadores(parcel)
        }

        override fun newArray(size: Int): Array<ProductoAdaptadores?> {
            return arrayOfNulls(size)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_carrito_compras, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(productos[position])
    }

    override fun getItemCount(): Int {
        return productos.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(producto: producto) {
            val nombre = itemView.findViewById<TextView>(R.id.Nombre_Producto_1)
            nombre.text = producto.nombre
            val descripcion = itemView.findViewById<TextView>(R.id.Descripcion_Producto_1)
            descripcion.text = producto.descripcion

        }
    }
}
