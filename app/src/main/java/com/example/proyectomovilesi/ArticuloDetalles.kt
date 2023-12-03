package com.example.proyectomovilesi

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Telephony.TextBasedSmsColumns
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.proyectomovilesi.models.Articulo
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
    private lateinit var txtAtrr1: TextView;
    private lateinit var txtAtrr2: TextView;
    private lateinit var txtAtrr3: TextView;
    private lateinit var txtAtrr4: TextView;
    private lateinit var txtAtrr5: TextView;
    private lateinit var txtAtrr6: TextView;
    private lateinit var btnEditar: Button
    private lateinit var btnEliminar: ImageButton
    private lateinit var actionsRow: LinearLayout


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
        txtAtrr1 = findViewById(R.id.txtAtrr1)
        txtAtrr2 = findViewById(R.id.txtAtrr2)
        txtAtrr3 = findViewById(R.id.txtAtrr3)
        txtAtrr4 = findViewById(R.id.txtAtrr4)
        txtAtrr5 = findViewById(R.id.txtAtrr5)
        txtAtrr6 = findViewById(R.id.txtAtrr6)
        actionsRow = findViewById(R.id.articleActionsRow)
        val currentUser = Storage.getCurrentUser()
        if(currentUser?.rol != "Administrador") actionsRow.visibility = View.GONE
        btnEditar = findViewById(R.id.btnEditarArticulo)
        btnEliminar = findViewById(R.id.btnEliminarArticulo)

        val tipo = intent.getIntExtra("tipo", 1100)
        val art: Articulo = when(tipo){
            0 -> Fosil(intent)
            1 -> Escultura(intent)
            else -> Pintura(intent)
        }
        assignBaseValues(art)
        if(art is Fosil){
            image.setImageResource(R.drawable.fossil)
            txtAtrr1.text = "Especie: ${art.especie}"
            txtAtrr2.text = "Periodo: ${art.periodo}"
            txtAtrr3.text = "Encontrado en: ${art.ubicacionDescubrimiento}"
            txtAtrr4.text = "Tipo de fósil: ${art.tipoFosil}"
            txtAtrr5.text = "Cuidados: ${art.cuidadosEspeciales}"
            txtAtrr6.visibility = View.GONE
        }
        if(art is Escultura){
            image.setImageResource(R.drawable.sculpture)
            txtAtrr1.text = "Altura: ${art.altura}cm"
            txtAtrr2.text = "Ancho: ${art.ancho}cm"
            txtAtrr3.text = "Peso: ${art.peso}kg"
            txtAtrr4.text = "Material: ${art.material}"
            txtAtrr5.text = "Autor: ${art.autor}"
            txtAtrr6.text = "Corriente Artística: ${art.corrienteArtistica}"
        }
        if(art is Pintura){
            image.setImageResource(R.drawable.canvas)
            txtAtrr1.text = "Largo: ${art.largo}cm"
            txtAtrr2.text = "Ancho: ${art.ancho}cm"
            txtAtrr3.text = "Tipo: ${art.tipoPintura}kg"
            txtAtrr4.text = "Lienzo: ${art.tipoLienzo}"
            txtAtrr5.text = "Autor: ${art.autor}"
            txtAtrr6.text = "Corriente Artística: ${art.corrienteArtistica}"
        }

        btnEditar.setOnClickListener {
            val inte = Intent(this, MainActivity::class.java)
            if(art is Fosil){
                val fosil: Fosil = art
                fosil.putToIntent(inte)
            }
            if(art is Pintura){
                val pintura: Pintura = art
                pintura.putToIntent(inte)
            }
            if(art is Escultura){
                val escultura: Escultura = art
                escultura.putToIntent(inte)
            }
            startActivity(inte)
        }

        btnEliminar.setOnClickListener {
            if(art is Fosil){
                val fosiles = Storage.getFosiles()
                val index = fosiles.indexOfFirst { e -> e.id == art.id }
                fosiles.removeAt(index)
                finish()
            }
            if(art is Escultura){
                val esculturas = Storage.getEsculturas()
                val index = esculturas.indexOfFirst { e -> e.id == art.id }
                esculturas.removeAt(index)
                finish()
            }
            if(art is Pintura){
                val pinturas = Storage.getPinturas()
                val index = pinturas.indexOfFirst { e -> e.id == art.id }
                pinturas.removeAt(index)
                finish()
            }
        }
    }
    fun assignBaseValues(art: Articulo){
        txtNombre.text = "Nombre: ${art.nombre}"
        txtAnio.text = "Año: ${art.anio}"
        txtCreadoPor.text = "Creado Por: ${art.creadoPor.nombre}"
        txtActualizadoPor.text = "Actualizado Por: ${art.actualizadoPor.nombre}"
        txtId.text = "Id: ${art.id}"
        txtDesc.text = "Descripción: ${art.descripcion}"
    }
}