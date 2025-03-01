package com.construtorclient.ui.avaliacao

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.construtorclient.R
import com.construtorclient.model.Avaliacao

class AvaliacaoAdapter(private val avaliacoes: List<Avaliacao>) : RecyclerView.Adapter<AvaliacaoAdapter.AvaliacaoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AvaliacaoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_avaliacao, parent, false)
        return AvaliacaoViewHolder(view)
    }

    override fun onBindViewHolder(holder: AvaliacaoViewHolder, position: Int) {
        holder.bind(avaliacoes[position])
    }

    override fun getItemCount(): Int = avaliacoes.size

    class AvaliacaoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvComentario: TextView = itemView.findViewById(R.id.tvComentario)
        private val ratingBar: RatingBar = itemView.findViewById(R.id.ratingBar)

        fun bind(avaliacao: Avaliacao) {
            tvComentario.text = avaliacao.comentario
            ratingBar.rating = avaliacao.nota
        }
    }
}