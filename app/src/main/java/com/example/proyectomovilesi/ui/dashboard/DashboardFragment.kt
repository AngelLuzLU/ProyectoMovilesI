package com.example.proyectomovilesi.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectomovilesi.FormularioSitio
import com.example.proyectomovilesi.R
import com.example.proyectomovilesi.Storage
import com.example.proyectomovilesi.adapters.ArticuloAdapter
import com.example.proyectomovilesi.adapters.SitioAdapter
import com.example.proyectomovilesi.databinding.FragmentDashboardBinding
import com.example.proyectomovilesi.models.Articulo
import com.example.proyectomovilesi.models.Sitio

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val sitio: Sitio? = null

    private lateinit var addBtn: Button
    private lateinit var searchBtn: ImageButton
    private lateinit var search: EditText
    private lateinit var recycler: RecyclerView
    private lateinit var searchAdapter: SitioAdapter
    private lateinit var adapter: SitioAdapter
    val sites = Storage.getSitios()
    var sitios = mutableListOf<Sitio>()
    var buscando = false
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        sitios = mutableListOf()
        sitios.addAll(sites)
        println(sitios.count())
        sitios.sortBy { it.id }
        addBtn = binding.btnAddSite
        searchBtn = binding.btnSearchSite
        search = binding.searchSite
        recycler = binding.siteRecycler
        adapter = SitioAdapter(requireContext(), sitios.toList())
        recycler.adapter = adapter
        setRecyclerOnClickListener(adapter)

        searchBtn.setOnClickListener {
            if (!buscando) {
                val comparar = search.text.toString()
                if (comparar.isBlank()) {
                    Toast.makeText(
                        requireContext(),
                        "Ingresa un valor para buscar",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }
                val resultados =
                    sitios.filter { e -> hasMatch(comparar, e.nombre, e.nombreExposicion, e.id.toString()) }
                if(resultados.isEmpty()){
                    Toast.makeText(
                        requireContext(),
                        "No se encontraron resultados",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }
                searchAdapter = SitioAdapter(requireContext(), resultados)
                recycler.adapter = searchAdapter
                setRecyclerOnClickListener(searchAdapter)
                searchBtn.setImageResource(R.drawable.close)
                buscando = true
                return@setOnClickListener
            }
            recycler.adapter = adapter
            searchBtn.setImageResource(R.drawable.search)
            search.text.clear()
            buscando = false
        }

        addBtn.setOnClickListener {
            startActivity(Intent(requireContext(), FormularioSitio::class.java))
        }

        return root
    }

    private fun setRecyclerOnClickListener(adapter: SitioAdapter) {
        adapter.setOnClickListener(object : SitioAdapter.OnClickListener {
            override fun onClick(position: Int, model: Sitio) {
                Toast.makeText(requireContext(), model.nombre, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun hasMatch(compare: String, value1: String, value2: String, value3: String): Boolean {
        var busqueda = "$value1#$value2#$value3"
        var compareInfo = compare
        var withDia =
            "ÀÁÂÃÄÅàáâãäåÒÓÔÕÕÖØòóôõöøÈÉÊËèéêëðÇçÐÌÍÎÏìíîïÙÚÛÜùúûüÑñŠšŸÿýŽž";
        var withoutDia =
            "AAAAAAaaaaaaOOOOOOOooooooEEEEeeeeeCcDIIIIiiiiUUUUuuuuNnSsYyyZz";

        for (i in withDia.indices) {
            compareInfo = compareInfo.replace(withDia[i], withoutDia[i]);
            busqueda = busqueda.replace(withDia[i], withoutDia[i]);
        }

        try {
            val regExp = Regex(compareInfo)
            return regExp.containsMatchIn(busqueda);
        } catch (e: Exception) {
            return false;
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}