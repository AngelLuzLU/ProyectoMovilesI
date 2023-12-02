package com.example.proyectomovilesi

import android.app.Application
import android.content.Context
import com.example.proyectomovilesi.models.Usuario

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()

        Storage.init(this)
        var usuarios = Storage.getUsuarios()
        var root = usuarios.find { e -> e.email == "root@admin.com" }
        if (null == root) {
            usuarios.add(
                Usuario(
                    usuarios.size + 1,
                    "root",
                    "Administrador Global",
                    "Administrador",
                    "root@admin.com"
                )
            )
        }
    }

    override fun onTerminate() {
        super.onTerminate()

        Storage.saveToSharedPreferences(this)
    }
}