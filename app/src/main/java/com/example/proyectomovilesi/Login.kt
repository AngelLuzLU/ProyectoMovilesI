package com.example.proyectomovilesi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class Login : AppCompatActivity() {
    private lateinit var tfLoginEmail: EditText
    private lateinit var tfLoginContra: EditText
    private lateinit var btnLogin: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        if(Storage.getCurrentUser() != null) startActivity(Intent(this, MainActivity::class.java))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        tfLoginEmail = findViewById(R.id.tfLoginEmail)
        tfLoginContra = findViewById(R.id.tfLoginContra)
        btnLogin = findViewById(R.id.btnLogin)

        btnLogin.setOnClickListener {
            val email = tfLoginEmail.text.toString()
            val contra = tfLoginContra.text.toString()
            if(email.isBlank() || contra.isBlank()){
                Toast.makeText(this, "Verifica los datos Ingresados", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val usuarios = Storage.getUsuarios()
            val user =  usuarios.find { e -> e.email == email }
            if(user == null){
                Toast.makeText(this, "No se ha encontrado el usuario", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(user.contrasenia != contra){
                Toast.makeText(this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            startActivity(Intent(this, MainActivity::class.java))
        }

    }
}