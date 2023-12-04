package com.example.proyectomovilesi

import com.example.proyectomovilesi.models.Escultura
import com.example.proyectomovilesi.models.Fosil
import com.example.proyectomovilesi.models.Pintura
import com.example.proyectomovilesi.models.Sitio
import com.example.proyectomovilesi.models.Usuario

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Storage private constructor(context: Context) {

    companion object {
        private const val SHARED_PREFS_NAME = "MyStoragePrefs"

        private const val KEY_USUARIOS = "key_usuarios"
        private const val KEY_SITIOS = "key_sitios"
        private const val KEY_PINTURAS = "key_pinturas"
        private const val KEY_FOSILES = "key_fosiles"
        private const val KEY_ESCULTURAS = "key_esculturas"
        private const val KEY_CURRENTUSER = "key_currentuser"

        private var usuarios: ArrayList<Usuario>? = null
        private var sitios: ArrayList<Sitio>? = null
        private var pinturas: ArrayList<Pintura>? = null
        private var fosiles: ArrayList<Fosil>? = null
        private var esculturas: ArrayList<Escultura>? = null
        private var currentUser: Usuario? = null

        fun init(context: Context) {
            val prefs: SharedPreferences = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
            val gson = Gson()

            usuarios = gson.fromJson(prefs.getString(KEY_USUARIOS, ""), object : TypeToken<ArrayList<Usuario>>() {}.type)
            sitios = gson.fromJson(prefs.getString(KEY_SITIOS, ""), object : TypeToken<ArrayList<Sitio>>() {}.type)
            pinturas = gson.fromJson(prefs.getString(KEY_PINTURAS, ""), object : TypeToken<ArrayList<Pintura>>() {}.type)
            fosiles = gson.fromJson(prefs.getString(KEY_FOSILES, ""), object : TypeToken<ArrayList<Fosil>>() {}.type)
            esculturas = gson.fromJson(prefs.getString(KEY_ESCULTURAS, ""), object : TypeToken<ArrayList<Escultura>>() {}.type)
            currentUser = gson.fromJson(prefs.getString(KEY_CURRENTUSER, ""), object : TypeToken<Usuario>() {}.type)


            usuarios = usuarios ?: ArrayList()
            sitios = sitios ?: ArrayList()
            pinturas = pinturas ?: ArrayList()
            fosiles = fosiles ?: ArrayList()
            esculturas = esculturas ?: ArrayList()

            /*if(sitios!!.isNotEmpty()){
                for(sitio in sitios!!){
                    if(sitio.articulos != null && sitio.articulos!!.isNotEmpty()){
                        for(i in sitio.articulos!!.indices){
                            val articulo = sitio.articulos!![i]
                            if(articulo is Fosil){
                                val fosil: Fosil = fosiles!!.find { e -> e.id == articulo.id }!!
                                sitio.articulos!![i] = fosil
                            }
                            if(articulo is Escultura){
                                val escultura: Escultura = esculturas!!.find { e -> e.id == articulo.id }!!
                                sitio.articulos!![i] = escultura
                            }
                            if(articulo is Pintura){
                                val pintura: Pintura = pinturas!!.find { e -> e.id == articulo.id }!!
                                sitio.articulos!![i] = pintura
                            }
                        }
                    }
                }
            }*/
        }

        fun cleanSharedPreferences(context: Context){
            val prefs: SharedPreferences = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
            val editor = prefs.edit()

            editor.putString(KEY_USUARIOS, null)
            editor.putString(KEY_SITIOS, null)
            editor.putString(KEY_PINTURAS, null)
            editor.putString(KEY_FOSILES, null)
            editor.putString(KEY_ESCULTURAS, null)
            editor.putString(KEY_CURRENTUSER, null)

            editor.apply()
        }

        fun saveToSharedPreferences(context: Context) {
            val prefs: SharedPreferences = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
            val editor = prefs.edit()
            val gson = Gson()

            editor.putString(KEY_USUARIOS, gson.toJson(usuarios))
            editor.putString(KEY_SITIOS, gson.toJson(sitios))
            editor.putString(KEY_PINTURAS, gson.toJson(pinturas))
            editor.putString(KEY_FOSILES, gson.toJson(fosiles))
            editor.putString(KEY_ESCULTURAS, gson.toJson(esculturas))
            editor.putString(KEY_CURRENTUSER, gson.toJson(currentUser))

            editor.apply()
        }

        // Métodos para acceder a las listas de manera estática
        fun getUsuarios(): ArrayList<Usuario> {
            return usuarios!!
        }

        fun getSitios(): ArrayList<Sitio> {
            return sitios!!
        }

        fun getPinturas(): ArrayList<Pintura> {
            return pinturas!!
        }

        fun getFosiles(): ArrayList<Fosil> {
            return fosiles!!
        }

        fun getEsculturas(): ArrayList<Escultura> {
            return esculturas!!
        }

        fun getCurrentUser(): Usuario?{
            return currentUser
        }

        fun setCurrentUser(usuario: Usuario, context: Context){
            currentUser = usuario
            saveToSharedPreferences(context)
        }
        fun logOut(context: Context){
            currentUser = null
            saveToSharedPreferences(context)
        }
    }
}
