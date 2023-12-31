package com.example.proyectomovilesi.models

import android.content.Intent
import com.example.proyectomovilesi.Storage
import java.io.Serializable

class Usuario(
    var id: Int,
    var nombre: String,
    var rol: String,
    var puesto: String,
    var email: String,
    var contrasenia: String,
): BaseMethods, Serializable {


    constructor(intent: Intent) : this(
        intent.getIntExtra("id", 0),
        intent.getStringExtra("nombre") ?: "",
        intent.getStringExtra("rol") ?: "",
        intent.getStringExtra("puesto") ?: "",
        intent.getStringExtra("email") ?: "",
        intent.getStringExtra("contrasenia") ?: "",
    )

    constructor(nombre: String, rol: String, puesto: String, email: String, contrasenia: String) : this(
        Storage.getUsuarios().count() + 1,
        nombre,
        rol,
        puesto,
        email,
        contrasenia
    )

    override fun putToIntent(intent: Intent) {
        intent.putExtra("id", id)
        intent.putExtra("nombre", nombre)
        intent.putExtra("rol", rol)
        intent.putExtra("puesto", puesto)
        intent.putExtra("email", email)
        intent.putExtra("contrasenia", contrasenia)
    }
}