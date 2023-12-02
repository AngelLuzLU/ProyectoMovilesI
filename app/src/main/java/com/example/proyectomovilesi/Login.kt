package com.example.proyectomovilesi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        if(Storage.getCurrentUser() != null) startActivity(Intent(this, MainActivity::class.java))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

    }
}