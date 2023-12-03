package com.example.proyectomovilesi.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectomovilesi.R
import com.example.proyectomovilesi.Storage
import com.example.proyectomovilesi.models.Articulo
import com.example.proyectomovilesi.models.Escultura
import com.example.proyectomovilesi.models.Fosil
import com.example.proyectomovilesi.models.Pintura

class ArticuloAdapter(private val context: Context, private val articulos: List<Articulo>) :
    RecyclerView.Adapter<ArticuloAdapter.ArticuloViewHolder>() {
    private var onClickListener: OnClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticuloViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.article_list_item, parent, false)
        return ArticuloViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticuloViewHolder, position: Int) {
        val articulo = articulos[position]
        if(articulo is Fosil){
            holder.imageArticleList.setImageResource(R.drawable.fossil)
        }
        if(articulo is Escultura){
            holder.imageArticleList.setImageResource(R.drawable.sculpture)
        }
        if(articulo is Pintura){
            holder.imageArticleList.setImageResource(R.drawable.canvas)
        }
        val sitios = Storage.getSitios()
        val sitio = sitios.find { e -> e.articulos.contains(articulo) }
        holder.articleListName.text = "Nombre: ${articulo.nombre}"
        holder.articleListSite.text = "Sitio: n/a"
        if(sitio != null) holder.articleListSite.text = "Sitio: ${sitio.nombre}"
        holder.articleListId.text = "Id: ${articulo.id}"
        holder.itemView.setOnClickListener {
            if (onClickListener != null) {
                onClickListener!!.onClick(position, articulo)
            }
        }
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    // onClickListener Interface
    interface OnClickListener {
        fun onClick(position: Int, model: Articulo)
    }

    override fun getItemCount(): Int {
        return articulos.size
    }

    class ArticuloViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageArticleList: ImageView = itemView.findViewById(R.id.imageArticleList)
        val articleListName: TextView = itemView.findViewById(R.id.articleListName)
        val articleListSite: TextView = itemView.findViewById(R.id.articleListSite)
        val articleListId: TextView = itemView.findViewById(R.id.articleListId)
    }
}