package com.example.proyectomovilesi.models

import android.content.Intent
import java.io.Serializable

class Fosil(
    id: Int,
    anio: String,
    nombre: String,
    creadoPor: Usuario,
    actualizadoPor: Usuario,
    descripcion: String,
    var especie: String,
    var periodo: String,
    var ubicacionDescubrimiento: String,
    var tipoFosil: String,
    var cuidadosEspeciales: String
) : Articulo(id, anio, nombre, creadoPor, actualizadoPor, descripcion), BaseMethods, Serializable {

    constructor(intent: Intent) : this(
        intent.getIntExtra("id", 0),
        intent.getStringExtra("anio") ?: "",
        intent.getStringExtra("nombre") ?: "",
        intent.getSerializableExtra("creadoPor") as Usuario,
        intent.getSerializableExtra("actualizadoPor") as Usuario,
        intent.getStringExtra("descripcion") ?: "",
        intent.getStringExtra("especie") ?: "",
        intent.getStringExtra("periodo") ?: "",
        intent.getStringExtra("ubicacionDescubrimiento") ?: "",
        intent.getStringExtra("tipoFosil") ?: "",
        intent.getStringExtra("cuidadosEspeciales") ?: ""
    )

    override fun putToIntent(intent: Intent) {
        super.putToIntent(intent)
        intent.putExtra("tipo", 0)
        intent.putExtra("especie", especie)
        intent.putExtra("periodo", periodo)
        intent.putExtra("ubicacionDescubrimiento", ubicacionDescubrimiento)
        intent.putExtra("tipoFosil", tipoFosil)
        intent.putExtra("cuidadosEspeciales", cuidadosEspeciales)
    }
}
