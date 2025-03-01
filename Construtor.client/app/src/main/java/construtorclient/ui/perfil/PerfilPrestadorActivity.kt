package com.Construtor.client.ui.perfil

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.Construtor.client.databinding.ActivityPerfilPrestadorBinding
import com.Construtor.client.model.Avaliacao
import com.Construtor.client.ui.avaliacao.AvaliacaoAdapter
import com.Construtor.client.model.Favorito

class PerfilPrestadorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPerfilPrestadorBinding
    private val db = FirebaseFirestore.getInstance()
    private val userId = FirebaseAuth.getInstance().currentUser?.uid
    private var prestadorId: String = ""
    private lateinit var avaliacaoAdapter: AvaliacaoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPerfilPrestadorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prestadorId = intent.getStringExtra("PRESTADOR_ID") ?: ""

        verificarFavorito()

        binding.btnFavorito.setOnClickListener {
            favoritarPrestador()
        }

        avaliacaoAdapter = AvaliacaoAdapter(emptyList())
        binding.recyclerAvaliacoes.layoutManager = LinearLayoutManager(this)
        binding.recyclerAvaliacoes.adapter = avaliacaoAdapter

        carregarAvaliacoes()
    }

    private fun carregarAvaliacoes() {
        db.collection("avaliacoes").document(prestadorId).collection("feedbacks")
            .get()
            .addOnSuccessListener { result ->
                val avaliacoes = result.documents.map { it.toObject(Avaliacao::class.java)!! }
                avaliacaoAdapter = AvaliacaoAdapter(avaliacoes)
                binding.recyclerAvaliacoes.adapter = avaliacaoAdapter
            }
    }

    private fun verificarFavorito() {
        db.collection("favoritos").document(userId!!)
            .collection("lista").document(prestadorId)
            .get().addOnSuccessListener { document ->
                if (document.exists()) {
                    binding.btnFavorito.text = "Desfavoritar"
                } else {
                    binding.btnFavorito.text = "Favoritar"
                }
            }
    }

    private fun favoritarPrestador() {
        val favorito = Favorito(
            prestadorId = prestadorId,
            nome = binding.tvNomePrestador.text.toString(),
            email = binding.tvEmailPrestador.text.toString(),
            servicos = listOf("Pedreiro", "Pintor"),
            fotoPerfil = "" // Adicionar foto do Firebase Storage
        )

        db.collection("favoritos").document(userId!!)
            .collection("lista").document(prestadorId)
            .set(favorito)
            .addOnSuccessListener {
                Toast.makeText(this, "Prestador favoritado!", Toast.LENGTH_SHORT).show()
                binding.btnFavorito.text = "Desfavoritar"
            }
    }
}