package com.Construtor.client.ui.contratos

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.Construtor.client.databinding.ActivityContratosBinding
import com.Construtor.client.model.Contrato

class ContratosActivity : AppCompatActivity() {

    private lateinit var binding: ActivityContratosBinding
    private val db = FirebaseFirestore.getInstance()
    private val userId = FirebaseAuth.getInstance().currentUser?.uid
    private lateinit var contratosAdapter: ContratosAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContratosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        contratosAdapter = ContratosAdapter(emptyList())
        binding.recyclerContratos.layoutManager = LinearLayoutManager(this)
        binding.recyclerContratos.adapter = contratosAdapter

        carregarContratos()
    }

    private fun carregarContratos() {
        db.collection("contratos").whereEqualTo("clienteId", userId)
            .get()
            .addOnSuccessListener { result ->
                val contratos = result.documents.map { it.toObject(Contrato::class.java)!! }
                contratosAdapter = ContratosAdapter(contratos)
                binding.recyclerContratos.adapter = contratosAdapter
            }
    }
}
