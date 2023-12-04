package com.example.proyectomovilesi

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.FrameLayout
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
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

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?, selectedItemView: View?,
                position: Int, id: Long
            ) {
                // Cambiar el formulario según la selección
                when (position) {
                    0 -> replaceFragment(FosilFormFragment())
                    1 -> replaceFragment(EsculturaFormFragment())
                    2 -> replaceFragment(PinturaFormFragment())
                }
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // No hacer nada
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }
}
