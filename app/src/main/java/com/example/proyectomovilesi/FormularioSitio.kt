package com.example.proyectomovilesi

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import com.example.proyectomovilesi.models.Sitio

class FormularioSitio : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario_sitio)

        val exposicion = findViewById<EditText>(R.id.editTextExpoJ)
        val nombre = findViewById<EditText>(R.id.editTextNomJ)
        val radioSi = findViewById<RadioButton>(R.id.radioButtonSJ)
        val registrar = findViewById<Button>(R.id.buttonRegistrarJ)
        val actualizar = findViewById<Button>(R.id.buttonActualizarJ)
        val regresar = findViewById<Button>(R.id.buttonRegresarJ)

        registrar.setOnClickListener(){
            var expo = exposicion.text.toString()
            var nom = nombre.text.toString()
            var publi = false
            if (radioSi.isChecked) publi = true
            Storage.getSitios().add(Sitio(nom, publi, expo))
            Storage.saveToSharedPreferences(this)
            finish()
        }

        actualizar.setOnClickListener(){

        }

        regresar.setOnClickListener(){
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}