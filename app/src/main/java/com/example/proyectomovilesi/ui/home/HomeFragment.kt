package com.example.proyectomovilesi.ui.home

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
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.example.proyectomovilesi.ArticuloDetalles
import com.example.proyectomovilesi.FormularioArticulo
import com.example.proyectomovilesi.R
import com.example.proyectomovilesi.Storage
import com.example.proyectomovilesi.adapters.ArticuloAdapter
import com.example.proyectomovilesi.databinding.FragmentHomeBinding
import com.example.proyectomovilesi.models.Articulo
import com.example.proyectomovilesi.models.Escultura
import com.example.proyectomovilesi.models.Fosil
import com.example.proyectomovilesi.models.Pintura

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var addBtn: Button
    private lateinit var searchBtn: ImageButton
    private lateinit var search: EditText
    private lateinit var recycler: RecyclerView
    private lateinit var searchAdapter: ArticuloAdapter
    private lateinit var adapter: ArticuloAdapter
    val pinturas = Storage.getPinturas()
    val esculturas = Storage.getEsculturas()
    val fosiles = Storage.getFosiles()
    var articulos = mutableListOf<Articulo>()
    var buscando = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        articulos = mutableListOf()
        articulos.addAll(pinturas)
        articulos.addAll(esculturas)
        articulos.addAll(fosiles)
        articulos.sortBy { it.id }
        addBtn = binding.btnAddArticle
        val currentUser = Storage.getCurrentUser()
        if(currentUser?.rol != "Administrador") addBtn.visibility = View.GONE
        searchBtn = binding.btnSearchArticle
        search = binding.searchArticle
        recycler = binding.articleRecycler
        adapter = ArticuloAdapter(requireContext(), articulos.toList())
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
                    articulos.filter { e -> hasMatch(comparar, e.nombre, e.anio, e.id.toString()) }
                if(resultados.isEmpty()){
                    Toast.makeText(
                        requireContext(),
                        "No se encontraron resultados",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }
                searchAdapter = ArticuloAdapter(requireContext(), resultados)
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
            val sitios = Storage.getSitios()
            if(sitios.isEmpty()){
                Toast.makeText(requireContext(), "Primero registra un sitio", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            startActivity(Intent(requireContext(), FormularioArticulo::class.java))
        }

        return root
    }

    private fun setRecyclerOnClickListener(adapter: ArticuloAdapter) {
        adapter.setOnClickListener(object : ArticuloAdapter.OnClickListener {
            override fun onClick(position: Int, model: Articulo) {
                val inte = Intent(requireContext(), ArticuloDetalles::class.java)
                if(model is Fosil){
                    val fosil: Fosil = model
                    fosil.putToIntent(inte)
                }
                if(model is Pintura){
                    val pintura: Pintura = model
                    pintura.putToIntent(inte)
                }
                if(model is Escultura){
                    val escultura: Escultura = model
                    escultura.putToIntent(inte)
                }
                startActivity(inte)
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