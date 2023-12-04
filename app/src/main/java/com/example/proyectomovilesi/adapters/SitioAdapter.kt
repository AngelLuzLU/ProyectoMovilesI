package com.example.proyectomovilesi.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectomovilesi.R
import com.example.proyectomovilesi.models.Escultura
import com.example.proyectomovilesi.models.Fosil
import com.example.proyectomovilesi.models.Pintura
import com.example.proyectomovilesi.models.Sitio
import kotlin.random.Random

class SitioAdapter(private val context: Context, private val sitios: List<Sitio>) :
    RecyclerView.Adapter<SitioAdapter.SitioViewHolder>() {
    private var onClickListener: OnClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SitioViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.site_list_item, parent, false)
        return SitioViewHolder(view)
    }

    override fun onBindViewHolder(holder: SitioViewHolder, position: Int) {
        val sitio = sitios[position]
        var images = listOf(R.drawable.ex_paint, R.drawable.ex_sculp, R.drawable.dinosaur)
        var imgId = 0
        if(sitio.articulos != null && sitio.articulos!!.isNotEmpty()){
            val first = sitio.articulos!!.first()
            if(first is Fosil) imgId = R.drawable.dinosaur
            if(first is Pintura) imgId = R.drawable.ex_paint
            if(first is Escultura) imgId = R.drawable.ex_sculp
        }else{
            imgId = images[Random.nextInt(0, images.count())]
        }
        holder.siteListImage.setImageResource(imgId)

        holder.siteListName.text = "Nombre: ${sitio.nombre}"
        holder.siteListExhibition.text = "Exposición: ${sitio.nombreExposicion}"
        holder.siteListId.text = "ID: ${sitio.id}"
        holder.itemView.setOnClickListener {
            if (onClickListener != null) {
                onClickListener!!.onClick(position, sitio )
            }
        }
    }

    override fun getItemCount(): Int {
        return sitios.size
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    // onClickListener Interface
    interface OnClickListener {
        fun onClick(position: Int, model: Sitio)
    }

    class SitioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val siteListImage: ImageView = itemView.findViewById(R.id.siteListImage)
        val siteListName: TextView = itemView.findViewById(R.id.siteListName)
        val siteListExhibition: TextView = itemView.findViewById(R.id.siteListExhibition)
        val siteListId: TextView = itemView.findViewById(R.id.siteListId)
    }
}
