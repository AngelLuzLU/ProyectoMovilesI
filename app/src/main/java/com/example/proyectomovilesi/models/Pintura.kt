package com.example.proyectomovilesi.models
import android.content.Intent
import java.io.Serializable

class Pintura(
    id: Int,
    anio: String,
    nombre: String,
    creadoPor: Usuario,
    actualizadoPor: Usuario,
    descripcion: String,
    var autor: String,
    var tipoPintura: String,
    var corrienteArtistica: String,
    var tipoLienzo: String,
    var ancho: Double,
    var largo: Double
) : Articulo(id, anio, nombre, creadoPor, actualizadoPor, descripcion), BaseMethods, Serializable {


    constructor(intent: Intent) : this(
        intent.getIntExtra("id", 0),
        intent.getStringExtra("anio") ?: "",
        intent.getStringExtra("nombre") ?: "",
        intent.getSerializableExtra("creadoPor") as Usuario,
        intent.getSerializableExtra("actualizadoPor") as Usuario,
        intent.getStringExtra("descripcion") ?: "",
        intent.getStringExtra("autor") ?: "",
        intent.getStringExtra("tipoPintura") ?: "",
        intent.getStringExtra("corrienteArtistica") ?: "",
        intent.getStringExtra("tipoLienzo") ?: "",
        intent.getDoubleExtra("ancho", 0.0),
        intent.getDoubleExtra("largo", 0.0)
    )

    override fun putToIntent(intent: Intent) {
        super.putToIntent(intent)
        intent.putExtra("tipo", 2)
        intent.putExtra("autor", autor)
        intent.putExtra("tipoPintura", tipoPintura)
        intent.putExtra("corrienteArtistica", corrienteArtistica)
        intent.putExtra("tipoLienzo", tipoLienzo)
        intent.putExtra("ancho", ancho)
        intent.putExtra("largo", largo)
    }
}