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
import com.example.proyectomovilesi.databinding.FragmentFosilFormBinding
import com.example.proyectomovilesi.models.Articulo
import com.example.proyectomovilesi.models.Fosil
import com.example.proyectomovilesi.models.Sitio
import com.example.proyectomovilesi.models.Usuario

class FosilFormFragment(val fosil : Fosil?)  : Fragment() {

    private var _binding : FragmentFosilFormBinding? = null

    private lateinit var txtNombreF : EditText
    private lateinit var txtAnioF : EditText
    private lateinit var txtDescF : EditText
    private lateinit var txtPeriodo : EditText
    private lateinit var sTipos : Spinner
    private lateinit var txtEspecieF : EditText
    private lateinit var txtUbicacionF : EditText
    private lateinit var btnRegF : Button
    private lateinit var txtCuidados : EditText
    private lateinit var sSitios : Spinner

    private val binding get() = _binding!!

    private val sitios = Storage.getSitios()
    private val nombres = sitios.map { e -> e.nombre }
    private var sitioSelected = nombres[0]


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFosilFormBinding.inflate(inflater, container, false)
        val root: View = binding.root

        txtNombreF = binding.editTextNomF
        txtAnioF = binding.editTextAnioF
        txtDescF = binding.editTextDescripcionF
        txtPeriodo = binding.editTextPeriodoF
        sTipos = binding.spinnerTipoFosil
        txtEspecieF = binding.editTextEspecieF
        txtUbicacionF = binding.editTextUbicacionF
        btnRegF = binding.botonRegistrarF
        txtCuidados = binding.editTextCuidadosF
        sSitios = binding.spinnerSitioFosilForm
        val currentUser = Storage.getCurrentUser()
        var tipoFosil = "Icnofósiles"
        val actualizar = fosil != null


        val opciones = resources.getStringArray(R.array.fosil_types)
        val adapterTipoFosil = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, opciones)
        sTipos.adapter = adapterTipoFosil


        sTipos.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                tipoFosil = opciones[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, nombres)
        sSitios.adapter = adapter

        sSitios.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
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
            btnRegF.text = "Actualizar"

            txtNombreF.setText(fosil?.nombre)
            txtAnioF.setText(fosil?.anio)
            txtDescF.setText(fosil?.descripcion)
            txtPeriodo.setText(fosil?.periodo)
            sTipos.setSelection(adapterTipoFosil.getPosition(fosil?.tipoFosil))
            txtEspecieF.setText(fosil?.especie)
            txtUbicacionF.setText(fosil?.ubicacionDescubrimiento)
            txtCuidados.setText(fosil?.cuidadosEspeciales)

            val f = Storage.getFosiles().find { e -> e.id == fosil?.id }
            val sitioInicial: Sitio = sitios.find { e -> e.articulos?.contains(f as Articulo) ?: false }!!
            sSitios.setSelection(adapter.getPosition(sitioInicial.nombre))
        }


        btnRegF.setOnClickListener(){
            var nombre = txtNombreF.text.toString()
            var  anio = txtAnioF.text.toString()
            var desc = txtDescF.text.toString()
            var periodo = txtPeriodo.text.toString()
            var especie = txtEspecieF.text.toString()
            var ubi = txtUbicacionF.text.toString()
            var cuidados = txtCuidados.text.toString()


            if(actualizar)
            {
                val f = Storage.getFosiles().find { e -> e.id == fosil?.id }
                f?.nombre = nombre
                f?.anio = anio
                f?.descripcion = desc
                f?.periodo = periodo
                f?.tipoFosil = tipoFosil
                f?.especie = especie
                f?.ubicacionDescubrimiento = ubi
                f?.cuidadosEspeciales = cuidados
                val sitioInicial: Sitio = sitios.find { e -> e.articulos?.contains(f as Articulo) ?: false }!!
                if(sitioSelected != sitioInicial.nombre){
                    sitioInicial.articulos?.remove(f!!)
                    val nuevoSitio: Sitio = sitios.find { e -> e.nombre == sitioSelected }!!
                    if(nuevoSitio.articulos != null)  nuevoSitio.articulos!!.add(f!!)
                    else nuevoSitio.articulos = arrayListOf(f!!)
                }
                Storage.saveToSharedPreferences(requireContext())
                startActivity(Intent(requireContext(), MainActivity::class.java))
            }
            else
            {
                var nuevoFosil = Fosil(nombre, anio, desc, periodo, tipoFosil, especie, ubi, cuidados, currentUser!!, currentUser)
                Storage.getFosiles().add(nuevoFosil)
                val sitio: Sitio = sitios.find { e -> e.nombre == sitioSelected }!!
                if(sitio.articulos != null)  sitio.articulos!!.add(nuevoFosil)
                else sitio.articulos = arrayListOf(nuevoFosil)

                Storage.saveToSharedPreferences(requireContext())

                Toast.makeText(requireContext(), "Agregado", Toast.LENGTH_SHORT).show()
                startActivity(Intent(requireContext(), MainActivity::class.java))
            }

        }


        return root
    }
}