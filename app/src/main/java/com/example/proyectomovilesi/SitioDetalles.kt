package com.example.proyectomovilesi

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.example.proyectomovilesi.models.Escultura
import com.example.proyectomovilesi.models.Fosil
import com.example.proyectomovilesi.models.Pintura
import com.example.proyectomovilesi.models.Sitio
import kotlin.random.Random

class SitioDetalles : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sitio_detalles)

        val img = findViewById<ImageView>(R.id.ImagenSitioDetalle)
        val id = findViewById<TextView>(R.id.textViewIDSitio)
        val nombre = findViewById<TextView>(R.id.textViewTituloSitio)
        val expo = findViewById<TextView>(R.id.textViewExposicionSitio)
        val publico = findViewById<TextView>(R.id.textViewPublicoSitio)
        val editar = findViewById<Button>(R.id.buttonEditar)
        val eliminar = findViewById<ImageButton>(R.id.imageButtonEliminar)
        val acciones = findViewById<LinearLayout>(R.id.actionsSitioDetalles)
        val currentUser = Storage.getCurrentUser()

        if (currentUser?.rol != "Administrador") acciones.visibility = View.GONE

        val sitio = Sitio(intent)
        id.text = "Id: ${sitio.id}"
        nombre.text = "Nombre: ${sitio.nombre}"
        expo.text = "Exposición: ${sitio.nombreExposicion}"
        publico.text = "Es privado"
        if (sitio.esPublico) publico.text = "Es público"

        var images = listOf(R.drawable.ex_paint, R.drawable.ex_sculp, R.drawable.dinosaur)
        var imgId = 0
        if (sitio.articulos != null){
            if(sitio.articulos!!.isNotEmpty()){
                val first = sitio.articulos!!.first()
                if(first is Fosil) imgId = R.drawable.dinosaur
                if(first is Pintura) imgId = R.drawable.ex_paint
                if(first is Escultura) imgId = R.drawable.ex_sculp
            }
        }
        else{
            imgId = images[Random.nextInt(0, images.count())]
        }
        img.setImageResource(imgId)

        editar.setOnClickListener(){
            val inte = Intent(this, FormularioSitio::class.java)
            sitio.putToIntent(inte)
            startActivity(inte)
        }
        eliminar.setOnClickListener(){
            if((sitio.articulos?.count() ?: 0) != 0) {
                Toast.makeText(this, "No se puede eliminar un sito con artículos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val sitios = Storage.getSitios()
            val index = sitios.indexOfFirst { e -> e.id == sitio.id }
            sitios.removeAt(index)
            Storage.saveToSharedPreferences(this)
            startActivity(Intent(this, MainActivity::class.java))
        }


    }
}