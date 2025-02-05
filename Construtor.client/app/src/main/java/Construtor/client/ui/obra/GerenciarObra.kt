package com.Construtor.client.ui.obra

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.functions.FirebaseFunctions

fun atualizarStatusObra(obraId: String, status: String, clienteId: String) {
    val db = FirebaseFirestore.getInstance()

    db.collection("obras").document(obraId)
        .update("status", status)
        .addOnSuccessListener {
            enviarNotificacao(clienteId, "Status da Obra Atualizado", "Sua obra agora está: $status")
        }
}

private fun enviarNotificacao(userId: String, titulo: String, mensagem: String) {
    val db = FirebaseFirestore.getInstance()
    db.collection("users").document(userId).get()
        .addOnSuccessListener { document ->
            val token = document.getString("fcmToken")
            if (token != null) {
                val notificacao = hashMapOf(
                    "to" to token,
                    "notification" to mapOf(
                        "title" to titulo,
                        "body" to mensagem
                    )
                )

                FirebaseFunctions.getInstance()
                    .getHttpsCallable("sendNotification")
                    .call(notificacao)
            }
        }
}
