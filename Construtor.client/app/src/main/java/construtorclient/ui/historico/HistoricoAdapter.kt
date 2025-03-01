package com.Construtor.client.ui.historico

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.Construtor.client.R
import com.Construtor.client.model.HistoricoServico

class HistoricoAdapter(private var historicos: List<HistoricoServico>) : RecyclerView.Adapter<HistoricoAdapter.HistoricoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoricoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_historico, parent, false)
        return HistoricoViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoricoViewHolder, position: Int) {
        holder.bind(historicos[position])
    }

    override fun getItemCount(): Int = historicos.size

    fun atualizarLista(novaLista: List<HistoricoServico>) {
        historicos = novaLista
        notifyDataSetChanged()
    }

    class HistoricoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvServico: TextView = itemView.findViewById(R.id.tvServico)
        private val tvStatus: TextView = itemView.findViewById(R.id.tvStatus)

        fun bind(historico: HistoricoServico) {
            tvServico.text = historico.tipoServico
            tvStatus.text = "Status: ${historico.status}"
        }
    }
}