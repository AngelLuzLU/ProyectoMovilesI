package com.example.proyectomovilesi

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Telephony.TextBasedSmsColumns
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.proyectomovilesi.models.Escultura
import com.example.proyectomovilesi.models.Fosil
import com.example.proyectomovilesi.models.Pintura

class ArticuloDetalles : AppCompatActivity() {
    private lateinit var txtNombre: TextView;
    private lateinit var txtAnio: TextView;
    private lateinit var txtCreadoPor: TextView;
    private lateinit var txtActualizadoPor: TextView;
    private lateinit var txtId: TextView;
    private lateinit var txtDesc: TextView;
    private lateinit var image: ImageView;
    private lateinit var layout: LinearLayout;

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_articulo_detalles)
        txtNombre = findViewById(R.id.nombreArtDetails)
        txtAnio = findViewById(R.id.anioArtDetails)
        txtCreadoPor = findViewById(R.id.creadoArtDetails)
        txtActualizadoPor = findViewById(R.id.actualizadoArtDetails)
        txtId = findViewById(R.id.idArtDetails)
        txtDesc = findViewById(R.id.descArtDetails)
        image = findViewById(R.id.imageArticulo)
        layout = findViewById(R.id.mainLayoutArtDetails)
        val tipo = intent.getIntExtra("tipo", 1100)
        when(tipo){
            0 -> {
                image.setImageResource(R.drawable.fossil)
                val fosil = Fosil(intent)
            }
            1 ->{
                image.setImageResource(R.drawable.sculpture)
                val escultura = Escultura(intent)
            }
            2 ->{
                image.setImageResource(R.drawable.canvas)
                val pintura = Pintura(intent)
            }
        }

    }
}