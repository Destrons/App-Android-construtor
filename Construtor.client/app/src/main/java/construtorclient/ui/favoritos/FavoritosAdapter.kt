package com.Construtor.client.ui.favoritos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.Construtor.client.R
import com.Construtor.client.model.Favorito

class FavoritosAdapter(
    private var favoritos: List<Favorito>,
    private val onItemClick: (String) -> Unit
) : RecyclerView.Adapter<FavoritosAdapter.FavoritosViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritosViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_favorito, parent, false)
        return FavoritosViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoritosViewHolder, position: Int) {
        holder.bind(favoritos[position], onItemClick)
    }

    override fun getItemCount(): Int = favoritos.size

    fun atualizarLista(novaLista: List<Favorito>) {
        favoritos = novaLista
        notifyDataSetChanged()
    }

    class FavoritosViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvNome: TextView = itemView.findViewById(R.id.tvNomeFavorito)

        fun bind(favorito: Favorito, onItemClick: (String) -> Unit) {
            tvNome.text = favorito.nome
            itemView.setOnClickListener { onItemClick(favorito.prestadorId) }
        }
    }
}