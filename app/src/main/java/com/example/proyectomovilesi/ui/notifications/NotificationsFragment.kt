package com.example.proyectomovilesi.ui.notifications

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
import com.example.proyectomovilesi.R
import com.example.proyectomovilesi.Storage
import com.example.proyectomovilesi.adapters.SitioAdapter
import com.example.proyectomovilesi.adapters.UsuarioAdapter
import com.example.proyectomovilesi.databinding.FragmentNotificationsBinding
import com.example.proyectomovilesi.models.Sitio
import com.example.proyectomovilesi.models.Usuario

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null

    private lateinit var addBtn: Button
    private lateinit var searchBtn: ImageButton
    private lateinit var search: EditText
    private lateinit var recycler: RecyclerView
    private lateinit var searchAdapter: UsuarioAdapter
    private lateinit var adapter: UsuarioAdapter
    val usrs = Storage.getUsuarios()
    val users = usrs.filter { e -> (e.email != "root@admin.com" && e.email != Storage.getCurrentUser()?.email) }
    var usuarios = mutableListOf<Usuario>()
    var buscando = false
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        usuarios = mutableListOf()
        usuarios.addAll(users)
        usuarios.sortBy { it.id }
        addBtn = binding.btnAddUser
        val currentUser = Storage.getCurrentUser()
        if(currentUser?.rol != "Administrador") addBtn.visibility = View.GONE
        searchBtn = binding.btnSearchUser
        search = binding.searchUser
        recycler = binding.userRecycler
        adapter = UsuarioAdapter(requireContext(), usuarios.toList())
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
                    usuarios.filter { e -> hasMatch(comparar, e.nombre, e.email, e.id.toString()) }
                if(resultados.isEmpty()){
                    Toast.makeText(
                        requireContext(),
                        "No se encontraron resultados",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }
                searchAdapter = UsuarioAdapter(requireContext(), resultados)
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
            Toast.makeText(requireContext(), "Ir al formulario", Toast.LENGTH_SHORT).show()
        }

        return root
    }

    private fun setRecyclerOnClickListener(adapter: UsuarioAdapter) {
        adapter.setOnClickListener(object : UsuarioAdapter.OnClickListener {
            override fun onClick(position: Int, model: Usuario) {
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