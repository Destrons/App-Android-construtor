package com.Construtor.client.recompensas

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.Construtor.client.model.Recompensa

object RecompensaManager {

    private val db = FirebaseFirestore.getInstance()
    private val userId = FirebaseAuth.getInstance().currentUser?.uid

    fun adicionarPontos(pontos: Int) {
        if (userId == null) return

        db.collection("recompensas").document(userId).get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val recompensa = document.toObject(Recompensa::class.java)
                    val novosPontos = (recompensa?.pontos ?: 0) + pontos
                    val novoNivel = calcularNivel(novosPontos)

                    db.collection("recompensas").document(userId)
                        .update("pontos", novosPontos, "nivel", novoNivel)
                }
            }
    }

    private fun calcularNivel(pontos: Int): String {
        return when {
            pontos >= 500 -> "VIP"
            pontos >= 300 -> "Avançado"
            pontos >= 100 -> "Intermediário"
            else -> "Iniciante"
        }
    }
}
