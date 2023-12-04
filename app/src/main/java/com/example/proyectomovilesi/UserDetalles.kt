package com.example.proyectomovilesi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.example.proyectomovilesi.models.Usuario
import com.google.firebase.firestore.auth.User

class UserDetalles : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detalles)

        val txtdatos = findViewById<TextView>(R.id.txtIDDetUser)
        val btnEdtDetUser = findViewById<Button>(R.id.btnEdtDerUser)
        val btnEliDetUser = findViewById<ImageButton>(R.id.imgBtnEliDetUser)
        val currentUser = Storage.getCurrentUser()

        if (currentUser?.rol != "Administrador"){
            btnEliDetUser.visibility = View.GONE
            btnEdtDetUser.visibility = View.GONE
        }

        val user = Usuario(intent)
        val datos = "ID: " + user.id + "\nNombre: " + user.nombre + "\nRol: " + user.rol + "\nPuesto: " + user.puesto + "\nEmail: " + user.email + "\nContraseña: *****"
        txtdatos.text = datos

        btnEdtDetUser.setOnClickListener(){
            val inte = Intent(this, FormularioUser::class.java)
            user.putToIntent(inte)
            startActivity(inte)
        }

        btnEliDetUser.setOnClickListener(){
            val usuarios = Storage.getUsuarios()
            usuarios.removeAt(user.id - 1)
            Storage.saveToSharedPreferences(this)
            Toast.makeText(this, "Eliminado", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}