package com.example.proyectomovilesi

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import com.example.proyectomovilesi.models.Sitio

class FormularioSitio : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario_sitio)

        val id = findViewById<EditText>(R.id.editTextIDJ)
        val exposicion = findViewById<EditText>(R.id.editTextIDJ)
        val nombre = findViewById<EditText>(R.id.editTextIDJ)
        val radioSi = findViewById<EditText>(R.id.editTextIDJ)
        val registrar = findViewById<EditText>(R.id.editTextIDJ)
        val actualizar = findViewById<EditText>(R.id.editTextIDJ)
        val regresar = findViewById<EditText>(R.id.editTextIDJ)

        registrar.setOnClickListener(){
            var ident = id.text.toString()
            var expo = exposicion.text.toString()
            var nom = nombre.text.toString()
            var publi = ""
            if (true) publi = "Si"
            else publi = "No"
            Storage.getSitios().add(Sitio(0, "", true, arrayListOf(), ""))
            Storage.saveToSharedPreferences(this)
        }

        actualizar.setOnClickListener(){

        }

        regresar.setOnClickListener(){
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}