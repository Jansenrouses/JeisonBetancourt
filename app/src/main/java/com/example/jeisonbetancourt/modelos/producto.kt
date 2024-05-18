package com.example.jeisonbetancourt.modelos

import android.os.Parcel
import android.os.Parcelable

data class producto (val imagen:String, val nombre:String, val descripcion:String, val precio:String) :
    Parcelable {
    constructor(parcel: Parcel) : this(
        TODO("imagen"),
        TODO("nombre"),
        TODO("descripcion"),
        TODO("precio")
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<producto> {
        override fun createFromParcel(parcel: Parcel): producto {
            return producto(parcel)
        }

        override fun newArray(size: Int): Array<producto?> {
            return arrayOfNulls(size)
        }
    }
}