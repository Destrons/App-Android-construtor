package com.Construtor.client.ui.recompensas

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.Construtor.client.databinding.ActivityRecompensasClienteBinding
import com.Construtor.client.model.Recompensa

class RecompensasClienteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecompensasClienteBinding
    private val db = FirebaseFirestore.getInstance()
    private val userId = FirebaseAuth.getInstance().currentUser?.uid

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecompensasClienteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        carregarRecompensas()
    }

    private fun carregarRecompensas() {
        db.collection("recompensas").document(userId!!)
            .get()
            .addOnSuccessListener { document ->
                val recompensa = document.toObject(Recompensa::class.java)
                if (recompensa != null) {
                    atualizarUI(recompensa)
                }
            }
    }

    private fun atualizarUI(recompensa: Recompensa) {
        binding.tvPontos.text = "Pontos: ${recompensa.pontos}"
        binding.tvNivel.text = "Nível: ${recompensa.nivel}"
        binding.tvPremios.text = "Prêmios: ${recompensa.premiosDisponiveis.joinToString(", ")}"
    }
}
