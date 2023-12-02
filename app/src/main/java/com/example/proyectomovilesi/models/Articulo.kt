package com.example.proyectomovilesi.models

import android.content.Intent
import java.io.Serializable

open class Articulo(
    var id: Int,
    var anio: String,
    var nombre: String,
    var creadoPor: Usuario,
    var actualizadoPor: Usuario,
    var descripcion: String
) : BaseMethods, Serializable {
    constructor(intent: Intent) : this(
        intent.getIntExtra("id", 0),
        intent.getStringExtra("anio") ?: "",
        intent.getStringExtra("nombre") ?: "",
        intent.getSerializableExtra("creadoPor") as Usuario,
        intent.getSerializableExtra("actualizadoPor") as Usuario,
        intent.getStringExtra("descripcion") ?: ""
    )

    override fun putToIntent(intent: Intent) {
        intent.putExtra("id", id)
        intent.putExtra("anio", anio)
        intent.putExtra("nombre", nombre)
        intent.putExtra("creadoPor", creadoPor)
        intent.putExtra("actualizadoPor", actualizadoPor)
        intent.putExtra("descripcion", descripcion)
    }
}