package com.example.proyectomovilesi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import com.example.proyectomovilesi.models.Sitio
import com.example.proyectomovilesi.models.Usuario

class FormularioUser : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario_user)

        val user = Usuario(intent)
        val actualizar = user.id != 0

        val txtNombre = findViewById<EditText>(R.id.txtNombre)
        val rAdmin = findViewById<RadioButton>(R.id.rAdmin)
        val rUser = findViewById<RadioButton>(R.id.rUser)
        val txtPuesto = findViewById<EditText>(R.id.txtPuesto)
        val txtEmail = findViewById<EditText>(R.id.txtEmail)
        val txtContra = findViewById<EditText>(R.id.txtContra)
        val btnReg = findViewById<Button>(R.id.btnRegUser)

        if(actualizar){
            btnReg.text = "Actualizar"
            txtNombre.setText(user.nombre)
            if(user.rol == "Administrador")
                rAdmin.isChecked = true
            else
                rUser.isChecked = true
            txtPuesto.setText(user.puesto)
            txtEmail.setText(user.email)
            txtContra.setText(user.contrasenia)
        }


        btnReg.setOnClickListener(){
            var nombre = txtNombre.text.toString()
            var  puesto = txtPuesto.text.toString()
            var email = txtEmail.text.toString()
            var contra = txtContra.text.toString()
            var rol = "Administrador"
            if (rUser.isChecked) rol = "Usuario"

            if(actualizar)
            {
                val u = Storage.getUsuarios().find { e -> e.id == user.id }
                u?.nombre = nombre
                u?.puesto = puesto
                u?.rol = rol
                u?.email = email
                u?.contrasenia = contra
                Storage.saveToSharedPreferences(this)
                Toast.makeText(this, "Actualizado", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))
            }
            else
            {
                Storage.getUsuarios().add(Usuario(nombre, rol, puesto, email, contra))
                Storage.saveToSharedPreferences(this)
                Toast.makeText(this, "Agregado", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))
            }

        }

    }
}