package com.example.proyectomovilesi.ui.formularioarticulos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.proyectomovilesi.R
import com.example.proyectomovilesi.models.Pintura

class PinturaFormFragment(var pintura: Pintura?) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_pintura_form, container, false)
    }
}