package com.construtorclient.network

import com.google.firebase.firestore.FirebaseFirestore

class SignalingService {
    private val db = FirebaseFirestore.getInstance()

    fun iniciarChamada(chatId: String) {
        val chamada = hashMapOf("status" to "pendente")
        db.collection("chamadas").document(chatId).set(chamada)
    }

    fun encerrarChamada(chatId: String) {
        db.collection("chamadas").document(chatId).delete()
    }
}
