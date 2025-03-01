package com.Construtor.client.ui.relatorio

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.Construtor.client.databinding.ActivityRelatorioClienteBinding
import com.Construtor.client.model.Relatorio

class RelatorioClienteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRelatorioClienteBinding
    private val db = FirebaseFirestore.getInstance()
    private val userId = FirebaseAuth.getInstance().currentUser?.uid

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRelatorioClienteBinding.inflate(layoutInflater)
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
        binding.tvTotalObras.text = "Total de Obras: ${relatorio.totalObras}"
        binding.tvTotalGasto.text = "Total Gasto: R$ ${relatorio.totalGasto}"
        binding.tvObrasConcluidas.text = "Obras Concluídas: ${relatorio.obrasConcluidas}"
        binding.tvObrasCanceladas.text = "Obras Canceladas: ${relatorio.obrasCanceladas}"

        configurarGrafico(relatorio)
    }

    private fun configurarGrafico(relatorio: Relatorio) {
        val pieChart: PieChart = binding.pieChart
        val entries = listOf(
            PieEntry(relatorio.obrasConcluidas.toFloat(), "Concluídas"),
            PieEntry(relatorio.obrasCanceladas.toFloat(), "Canceladas")
        )

        val dataSet = PieDataSet(entries, "Obras")
        val data = PieData(dataSet)

        pieChart.data = data
        pieChart.invalidate()
    }
}