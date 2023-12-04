package com.example.proyectomovilesi.ui.formularioarticulos

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.proyectomovilesi.MainActivity
import com.example.proyectomovilesi.R
import com.example.proyectomovilesi.Storage
import com.example.proyectomovilesi.databinding.FragmentDashboardBinding
import com.example.proyectomovilesi.databinding.FragmentEsculturaFormBinding
import com.example.proyectomovilesi.models.Articulo
import com.example.proyectomovilesi.models.Escultura
import com.example.proyectomovilesi.models.Sitio

class EsculturaFormFragment(var escultura: Escultura?) : Fragment() {
    private var _binding: FragmentEsculturaFormBinding? = null
    private lateinit var tfNombre: EditText
    private lateinit var tfAnio: EditText
    private lateinit var tfDescripcion: EditText
    private lateinit var tfMaterial: EditText
    private lateinit var tfPeso: EditText
    private lateinit var tfAltura: EditText
    private lateinit var tfAncho: EditText
    private lateinit var tfAutor: EditText
    private lateinit var tfCorrienteArtistica: EditText
    private lateinit var spinnerSitio: Spinner
    private lateinit var button: Button
    private val binding get() = _binding!!
    private val sitios = Storage.getSitios()
    private val nombres = sitios.map { e -> e.nombre }
    private var sitioSelected = nombres[0]

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEsculturaFormBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val actualizar = escultura != null
        tfNombre = binding.editTextNomEscultura
        tfAnio = binding.editTextAnioEscultura
        tfDescripcion = binding.editTextDescripcionEscultura
        tfMaterial = binding.editTextMaterialEscultura
        tfPeso = binding.editTextPesoEscultura
        tfAltura = binding.editTextAlturaEscultura
        tfAncho = binding.editTextAnchuraEscultura2
        tfAutor = binding.editTextAutorEscultura
        tfCorrienteArtistica = binding.editTextCorrienteEscultura
        spinnerSitio = binding.spinnerSitioEsculturaForm
        button = binding.botonRegistrarEscultura
        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, nombres)
        spinnerSitio.adapter = adapter

        spinnerSitio.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                sitioSelected = nombres[position];
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        if(actualizar){
            button.text = "Actualizar"
            tfNombre.setText(escultura?.nombre)
            tfAnio.setText(escultura?.anio)
            tfDescripcion.setText(escultura?.descripcion)
            tfMaterial.setText(escultura?.material)
            tfPeso.setText(escultura?.peso.toString())
            tfAltura.setText(escultura?.altura.toString())
            tfAncho.setText(escultura?.ancho.toString())
            tfAutor.setText(escultura?.autor)
            tfCorrienteArtistica.setText(escultura?.corrienteArtistica)
        }
        button.setOnClickListener {
            val nombre = tfNombre.text.toString()
            val anio = tfAnio.text.toString()
            val descripcion = tfDescripcion.text.toString()
            val material = tfMaterial.text.toString()
            val peso = tfPeso.text.toString()
            val altura = tfAltura.text.toString()
            val ancho = tfAncho.text.toString()
            val autor = tfAutor.text.toString()
            val corriente = tfCorrienteArtistica.text.toString()
            val currentUser = Storage.getCurrentUser()
            val esculturas = Storage.getEsculturas()
            if (nombre.isBlank() ||
                anio.isBlank() ||
                descripcion.isBlank() ||
                material.isBlank() ||
                peso.isBlank() ||
                altura.isBlank() ||
                ancho.isBlank() ||
                autor.isBlank() ||
                corriente.isBlank()
            ) {
                Toast.makeText(
                    requireContext(),
                    "Verifica los datos ingresados",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            if(!actualizar){
                val nuevaEscultura = Escultura(
                    esculturas.size + 1,
                    anio,
                    nombre,
                    currentUser!!,
                    currentUser,
                    descripcion,
                    altura.toDouble(),
                    ancho.toDouble(),
                    peso.toDouble(),
                    material,
                    autor,
                    corriente
                )
                esculturas.add(
                    nuevaEscultura
                )
                val sitio: Sitio = sitios.find { e -> e.nombre == sitioSelected }!!
                if(sitio.articulos != null)  sitio.articulos!!.add(nuevaEscultura)
                else sitio.articulos = arrayListOf(nuevaEscultura)
            }else{
                val sculpture: Escultura = esculturas.find { e -> e.id == escultura?.id }!!
                sculpture.anio = anio
                sculpture.nombre = nombre
                sculpture.actualizadoPor = currentUser!!
                sculpture.descripcion = descripcion
                sculpture.altura = altura.toDouble()
                sculpture.ancho = ancho.toDouble()
                sculpture.peso = peso.toDouble()
                sculpture.material = material
                sculpture.autor = autor
                sculpture.corrienteArtistica = corriente
                val sitioInicial: Sitio = sitios.find { e -> e.articulos?.contains(sculpture as Articulo) ?: false }!!
                if(sitioSelected != sitioInicial.nombre){
                    sitioInicial.articulos?.remove(sculpture)
                    val nuevoSitio: Sitio = sitios.find { e -> e.nombre == sitioSelected }!!
                    if(nuevoSitio.articulos != null)  nuevoSitio.articulos!!.add(sculpture)
                    else nuevoSitio.articulos = arrayListOf(sculpture)
                }
            }
            Storage.saveToSharedPreferences(requireContext())
            startActivity(Intent(requireContext(), MainActivity::class.java))

        }
        return root
    }
}