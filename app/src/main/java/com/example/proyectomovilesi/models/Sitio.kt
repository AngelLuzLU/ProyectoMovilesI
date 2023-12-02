package com.example.proyectomovilesi.models

import android.content.Intent
import java.io.Serializable

class Sitio(
    var id: Int,
    var nombre: String,
    var esPublico: Boolean,
    var articulos: ArrayList<Articulo>,
    var nombreExposicion: String
) : BaseMethods, Serializable {

    constructor(intent: Intent) : this(
        intent.getIntExtra("id", 0),
        intent.getStringExtra("nombre") ?: "",
        intent.getBooleanExtra("esPublico", false),
        intent.getSerializableExtra("articulos") as ArrayList<Articulo>,
        intent.getStringExtra("nombreExposicion") ?: ""
    )

    override fun putToIntent(intent: Intent) {
        intent.putExtra("id", id)
        intent.putExtra("nombre", nombre)
        intent.putExtra("esPublico", esPublico)
        intent.putExtra("articulos", articulos)
        intent.putExtra("nombreExposicion", nombreExposicion)
    }

}