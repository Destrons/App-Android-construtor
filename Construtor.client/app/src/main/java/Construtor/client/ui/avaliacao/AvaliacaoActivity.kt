package com.Construtor.client.ui.avaliacao

import android.os.Bundle
import android.widget.RatingBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.Construtor.client.databinding.ActivityAvaliacaoBinding
import com.Construtor.client.model.Avaliacao

class AvaliacaoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAvaliacaoBinding
    private val db = FirebaseFirestore.getInstance()
    private val userId = FirebaseAuth.getInstance().currentUser?.uid
    private var prestadorId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAvaliacaoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prestadorId = intent.getStringExtra("PRESTADOR_ID") ?: ""

        binding.btnEnviarAvaliacao.setOnClickListener {
            enviarAvaliacao()
        }
    }

    private fun enviarAvaliacao() {
        val nota = binding.ratingBar.rating
        val comentario = binding.etComentario.text.toString()

        if (nota > 0 && comentario.isNotEmpty()) {
            val avaliacao = Avaliacao(clienteId = userId!!, prestadorId = prestadorId, nota = nota, comentario = comentario)

            db.collection("avaliacoes").document(prestadorId)
                .collection("feedbacks").add(avaliacao)
                .addOnSuccessListener {
                    Toast.makeText(this, "Avaliação enviada!", Toast.LENGTH_LONG).show()
                    finish()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Erro ao enviar avaliação.", Toast.LENGTH_LONG).show()
                }
        } else {
            Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_LONG).show()
        }
    }
}