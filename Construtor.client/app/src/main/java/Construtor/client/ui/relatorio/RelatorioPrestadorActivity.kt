package com.Construtor.client.ui.relatorio

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.Construtor.client.databinding.ActivityRelatorioPrestadorBinding
import com.Construtor.client.model.Relatorio

class RelatorioPrestadorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRelatorioPrestadorBinding
    private val db = FirebaseFirestore.getInstance()
    private val userId = FirebaseAuth.getInstance().currentUser?.uid

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRelatorioPrestadorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        carregarRelatorio()
    }

    private fun carregarRelatorio() {
        db.collection("relatorios").document(userId!!)
            .get()
            .addOnSuccessListener { document ->
                val relatorio = document.toObject(Relatorio::class.java)
                if (relatorio != null) {
                    atualizarUI(relatorio)
                }
            }
    }

    private fun atualizarUI(relatorio: Relatorio) {
        binding.tvTotalServicos.text = "Total de Serviços: ${relatorio.totalObras}"
        binding.tvTotalRecebido.text = "Total Recebido: R$ ${relatorio.totalGasto}"
        binding.tvServicosConcluidos.text = "Serviços Concluídos: ${relatorio.obrasConcluidas}"
        binding.tvServicosCancelados.text = "Serviços Cancelados: ${relatorio.obrasCanceladas}"
    }
}