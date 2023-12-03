package com.example.proyectomovilesi.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectomovilesi.R
import com.example.proyectomovilesi.models.Usuario
import com.google.android.material.imageview.ShapeableImageView

class UsuarioAdapter(private val context: Context, private val usuarios: List<Usuario>) :
    RecyclerView.Adapter<UsuarioAdapter.UsuarioViewHolder>() {
    private var onClickListener: OnClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsuarioViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.user_list_item, parent, false)
        return UsuarioViewHolder(view)
    }

    override fun onBindViewHolder(holder: UsuarioViewHolder, position: Int) {
        val usuario = usuarios[position]

        holder.imagenUserList.setImageResource(R.drawable.defaultusergrey)

        holder.userListName.text = "Nombre: ${usuario.nombre}"
        holder.userListEmail.text = "Email: ${usuario.email}"
        holder.userListId.text = "Id: ${usuario.id.toString()}"
        holder.itemView.setOnClickListener {
            if (onClickListener != null) {
                onClickListener!!.onClick(position, usuario)
            }
        }
    }

    override fun getItemCount(): Int {
        return usuarios.size
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    // onClickListener Interface
    interface OnClickListener {
        fun onClick(position: Int, model: Usuario)
    }

    class UsuarioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imagenUserList: ShapeableImageView = itemView.findViewById(R.id.imagenUserList)
        val userListName: TextView = itemView.findViewById(R.id.userListName)
        val userListEmail: TextView = itemView.findViewById(R.id.userListEmail)
        val userListId: TextView = itemView.findViewById(R.id.userListId)
    }
}
