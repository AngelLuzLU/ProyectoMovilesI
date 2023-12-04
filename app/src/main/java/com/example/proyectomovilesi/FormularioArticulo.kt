package com.example.proyectomovilesi

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.FrameLayout
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.proyectomovilesi.models.Articulo
import com.example.proyectomovilesi.models.Escultura
import com.example.proyectomovilesi.models.Fosil
import com.example.proyectomovilesi.models.Pintura
import com.example.proyectomovilesi.ui.formularioarticulos.EsculturaFormFragment
import com.example.proyectomovilesi.ui.formularioarticulos.FosilFormFragment
import com.example.proyectomovilesi.ui.formularioarticulos.PinturaFormFragment

class FormularioArticulo : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario_articulo)

        val spinner: Spinner = findViewById(R.id.spinnerTipoFosil)
        val container: FrameLayout = findViewById(R.id.container)

        val adapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(
            this, R.array.form_types, android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        // Preselección en "Fosil"
        spinner.setSelection(adapter.getPosition("Fosil"))
        val tipo = intent.getIntExtra("tipo", 1100)
        if (tipo == 1100) {
            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parentView: AdapterView<*>?, selectedItemView: View?,
                    position: Int, id: Long
                ) {
                    // Cambiar el formulario según la selección
                    when (position) {
                        0 -> replaceFragment(FosilFormFragment(null))
                        1 -> replaceFragment(EsculturaFormFragment(null))
                        2 -> replaceFragment(PinturaFormFragment(null))
                    }
                }
                override fun onNothingSelected(parentView: AdapterView<*>?) {}

            }
        }
            if (tipo != 1100) {
                spinner.isEnabled = false
                when (tipo) {
                    0 -> {
                        replaceFragment(FosilFormFragment(Fosil(intent)))
                        spinner.setSelection(adapter.getPosition("Fosil"))
                    }

                    1 -> {
                        replaceFragment(EsculturaFormFragment(Escultura(intent)))
                        spinner.setSelection(adapter.getPosition("Escultura"))
                    }

                    2 -> {
                        replaceFragment(PinturaFormFragment(Pintura(intent)))
                        spinner.setSelection(adapter.getPosition("Pintura"))
                    }
                }
            }



        }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }
    }
