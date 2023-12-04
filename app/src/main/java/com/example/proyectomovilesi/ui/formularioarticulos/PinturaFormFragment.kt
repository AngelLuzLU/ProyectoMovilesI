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
import com.example.proyectomovilesi.Storage
import com.example.proyectomovilesi.databinding.FragmentPinturaFormBinding
import com.example.proyectomovilesi.models.Articulo
import com.example.proyectomovilesi.models.Pintura
import com.example.proyectomovilesi.models.Sitio

class PinturaFormFragment (var pintura:Pintura?): Fragment() {
    private var _binding: FragmentPinturaFormBinding? = null
    val actualizar = pintura != null
    val pinturas = Storage.getPinturas()
    val currentUser = Storage.getCurrentUser()
    private lateinit var tfNombrePintura : EditText
    private lateinit var tfAnioPintura : EditText
    private lateinit var tfDescripcionPintura : EditText
    private lateinit var tfCorrientePintura : EditText
    private lateinit var tfAutorPintura : EditText
    private lateinit var tfAlturaPintura : EditText
    private lateinit var tfAnchuraPintura : EditText
    private lateinit var spinTipoPintura : Spinner
    private lateinit var spinTipoLienzo : Spinner
    private lateinit var botonRegistrar : Button
    private lateinit var spinSitio : Spinner

    private val binding get() = _binding!!
    private val sitios = Storage.getSitios()
    private val nombres = sitios.map {e -> e.nombre}
    private var sitiosSelected = nombres[0]
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPinturaFormBinding.inflate(inflater, container, false)
        val root:View = binding.root
        tfNombrePintura = binding.editTextNomPintura
        tfAnioPintura = binding.editTextAnioPintura
        tfDescripcionPintura = binding.editTextDescripcionPintura
        tfCorrientePintura = binding.editTextCorrientePintura
        tfAutorPintura = binding.editTextAutorPintura
        tfAlturaPintura = binding.editTextAlturaPintura
        tfAnchuraPintura = binding.editTextAnchuraPintura
        spinTipoPintura = binding.spinnerTipoPintura
        spinTipoLienzo = binding.spinnerTipoLienzo
        botonRegistrar = binding.botonRegistrarPintura
        spinSitio = binding.spinnerSitioPinturaForm

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, nombres)
        spinSitio.adapter = adapter

        spinSitio.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                sitiosSelected = nombres[position];
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        if (actualizar){
            botonRegistrar.text = "ACTUALIZAR"
            tfNombrePintura.setText(pintura?.nombre)
            tfAnioPintura.setText(pintura?.anio)
            tfDescripcionPintura.setText(pintura?.descripcion)
            tfCorrientePintura.setText(pintura?.corrienteArtistica)
            tfAutorPintura.setText(pintura?.autor)
            tfAlturaPintura.setText(pintura?.largo.toString())
            tfAnchuraPintura.setText(pintura?.ancho.toString())
            //spinTipoPintura.setSelection(pintura?.tipoPintura.)
            //spinTipoLienzo.setSelection(pintura?.tipoLienzo.)
        }

        botonRegistrar.setOnClickListener(View.OnClickListener {
            val nombre = tfNombrePintura.text.toString()
            val anio = tfAnioPintura.text.toString()
            val descripcion = tfDescripcionPintura.text.toString()
            val corriente = tfCorrientePintura.text.toString()
            val autor = tfAutorPintura.text.toString()
            val altura = tfAlturaPintura.text.toString()
            val anchura = tfAnchuraPintura.text.toString()
            val tipoPintura = spinTipoPintura.selectedItem.toString()
            val tipoLienzo = spinTipoLienzo.selectedItem.toString()
            if (nombre.isEmpty() ||
                anio.isEmpty() ||
                descripcion.isEmpty() ||
                corriente.isEmpty() ||
                autor.isEmpty() ||
                altura.isEmpty() ||
                anchura.isEmpty() ||
                tipoPintura.isEmpty() ||
                tipoLienzo.isEmpty()){
                Toast.makeText(requireContext(), "No puede campos vacíos", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }

            if (!actualizar){
                val nuevaPintura = Pintura(
                    pinturas.size +1,
                    anio,
                    nombre,
                    currentUser!!,
                    currentUser,
                    descripcion,
                    autor,
                    tipoPintura,
                    corriente,
                    tipoLienzo,
                    anchura.toDouble(),
                    altura.toDouble()
                )
                pinturas.add(nuevaPintura)
                val sitio : Sitio = sitios.find { e -> e.nombre == sitiosSelected }!!
                if (sitio.articulos != null){
                    sitio.articulos!!.add(nuevaPintura)
                }else{
                    sitio.articulos = arrayListOf(nuevaPintura)
                }
            }else {
                val pinturon: Pintura = pinturas.find { e -> e.id == pintura?.id }!!
                pinturon.anio = anio
                pinturon.nombre = nombre
                pinturon.actualizadoPor = currentUser!!
                pinturon.descripcion = descripcion
                pinturon.autor = autor
                pinturon.tipoPintura = tipoPintura
                pinturon.corrienteArtistica = corriente
                pinturon.tipoLienzo = tipoLienzo
                pinturon.ancho = anchura.toDouble()
                pinturon.largo = altura.toDouble()
                val sitioInicial: Sitio =
                    sitios.find { e -> e.articulos?.contains(pinturon as Articulo) ?: false }!!
                if (sitiosSelected != sitioInicial.nombre) {
                    sitioInicial.articulos?.remove(pinturon)
                    val nuevoSitio: Sitio = sitios.find { e -> e.nombre == sitiosSelected }!!
                    if (nuevoSitio.articulos != null) nuevoSitio.articulos!!.add(pinturon)
                    else nuevoSitio.articulos = arrayListOf(pinturon)
                }
            }
            Storage.saveToSharedPreferences(requireContext())
            startActivity(Intent(requireContext(), MainActivity::class.java))
        })

        return root
    }
}