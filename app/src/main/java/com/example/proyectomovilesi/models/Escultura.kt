package com.example.proyectomovilesi.models

import android.content.Intent
import java.io.Serializable

class Escultura(
    id: Int,
    anio: String,
    nombre: String,
    creadoPor: Usuario,
    actualizadoPor: Usuario,
    descripcion: String,
    var altura: Double,
    var ancho: Double,
    var peso: Double,
    var material: String,
    var autor: String,
    var corrienteArtistica: String
) : Articulo(id, anio, nombre, creadoPor, actualizadoPor, descripcion), BaseMethods, Serializable {

    constructor(intent: Intent) : this(
        intent.getIntExtra("id", 0),
        intent.getStringExtra("anio") ?: "",
        intent.getStringExtra("nombre") ?: "",
        intent.getSerializableExtra("creadoPor") as Usuario,
        intent.getSerializableExtra("actualizadoPor") as Usuario,
        intent.getStringExtra("descripcion") ?: "",
        intent.getDoubleExtra("altura", 0.0),
        intent.getDoubleExtra("ancho", 0.0),
        intent.getDoubleExtra("peso", 0.0),
        intent.getStringExtra("material") ?: "",
        intent.getStringExtra("autor") ?: "",
        intent.getStringExtra("corrienteArtistica") ?: ""
    )

    override fun putToIntent(intent: Intent) {
        super.putToIntent(intent)
        intent.putExtra("altura", altura)
        intent.putExtra("ancho", ancho)
        intent.putExtra("peso", peso)
        intent.putExtra("material", material)
        intent.putExtra("autor", autor)
        intent.putExtra("corrienteArtistica", corrienteArtistica)
    }
}
