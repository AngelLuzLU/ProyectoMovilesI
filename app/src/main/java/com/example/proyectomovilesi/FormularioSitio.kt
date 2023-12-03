package com.example.proyectomovilesi

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RadioButton
import com.example.proyectomovilesi.models.Sitio

class FormularioSitio : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario_sitio)

        val sitio = Sitio(intent)
        val actualizar = sitio.id != 0
        val exposicion = findViewById<EditText>(R.id.editTextExpoJ)
        val nombre = findViewById<EditText>(R.id.editTextNomJ)
        val radioSi = findViewById<RadioButton>(R.id.radioButtonSJ)
        val radioNo = findViewById<RadioButton>(R.id.radioButtonNJ)
        val registrar = findViewById<Button>(R.id.buttonRegistrarJ)

        if (actualizar){
            registrar.text = "Actualizar"
            exposicion.setText(sitio.nombreExposicion)
            nombre.setText(sitio.nombre)
            radioSi.isChecked = sitio.esPublico
            radioNo.isChecked = !sitio.esPublico
        }


        registrar.setOnClickListener(){
            var expo = exposicion.text.toString()
            var nom = nombre.text.toString()
            var publi = false
            if (radioSi.isChecked) publi = true
            if (!actualizar){
                Storage.getSitios().add(Sitio(nom, publi, expo))
                Storage.saveToSharedPreferences(this)
                finish()
                return@setOnClickListener
            }
            val site = Storage.getSitios().find { e -> e.id == sitio.id }
            site?.nombreExposicion = expo
            site?.nombre = nom
            site?.esPublico = publi
            Storage.saveToSharedPreferences(this)
            startActivity(Intent(this, MainActivity::class.java))
        }


    }
}