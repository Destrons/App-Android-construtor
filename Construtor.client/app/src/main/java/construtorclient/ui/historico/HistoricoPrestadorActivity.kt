package com.Construtor.client.ui.historico

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.Construtor.client.databinding.ActivityHistoricoPrestadorBinding
import com.Construtor.client.model.HistoricoServico

class HistoricoPrestadorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoricoPrestadorBinding
    private val db = FirebaseFirestore.getInstance()
    private val userId = FirebaseAuth.getInstance().currentUser?.uid
    private lateinit var historicoAdapter: HistoricoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoricoPrestadorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        historicoAdapter = HistoricoAdapter(emptyList())
        binding.recyclerHistorico.layoutManager = LinearLayoutManager(this)
        binding.recyclerHistorico.adapter = historicoAdapter

        carregarHistorico()
    }

    private fun carregarHistorico() {
        db.collection("historico_servicos").document(userId!!)
            .collection("lista")
            .get()
            .addOnSuccessListener { result ->
                val historicos = result.documents.map { it.toObject(HistoricoServico::class.java)!! }
                historicoAdapter.atualizarLista(historicos)
            }
    }
}