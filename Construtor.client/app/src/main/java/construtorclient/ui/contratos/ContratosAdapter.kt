package com.onstrutor.client.ui.contratos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.onstrutor.client.R
import com.onstrutor.client.model.Contrato

class ContratosAdapter(private val contratos: List<Contrato>) : RecyclerView.Adapter<ContratosAdapter.ContratosViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContratosViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_contrato, parent, false)
        return ContratosViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContratosViewHolder, position: Int) {
        holder.bind(contratos[position])
    }

    override fun getItemCount(): Int = contratos.size

    class ContratosViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvStatus: TextView = itemView.findViewById(R.id.tvStatus)
        private val tvValor: TextView = itemView.findViewById(R.id.tvValor)

        fun bind(contrato: Contrato) {
            tvStatus.text = "Status: ${contrato.status}"
            tvValor.text = "Valor: R$${contrato.valor}"
        }
    }
}
