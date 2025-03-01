package com.Construtor.client.ui.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.Construtor.client.model.Message
import com.google.firebase.functions.FirebaseFunctions

class ChatViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    private val userId = FirebaseAuth.getInstance().currentUser?.uid
    private val _messages = MutableLiveData<List<Message>>()
    val messages: LiveData<List<Message>> get() = _messages

    fun loadMessages(chatId: String) {
        db.collection("mensagens").document(chatId).collection("mensagens")
            .orderBy("timestamp")
            .addSnapshotListener { snapshot, _ ->
                if (snapshot != null) {
                    val messagesList = snapshot.documents.map { it.toObject(Message::class.java)!! }
                    _messages.postValue(messagesList)
                }
            }
    }

    fun sendMessage(chatId: String, destinatarioId: String, texto: String) {
        val message = Message(remetenteId = userId!!, destinatarioId = destinatarioId, mensagem = texto)
        db.collection("mensagens").document(chatId).collection("mensagens").add(message)
    }

    fun sendMessage(chatId: String, destinatarioId: String, texto: String) {
        val message = Message(remetenteId = userId!!, destinatarioId = destinatarioId, mensagem = texto)

        db.collection("mensagens").document(chatId).collection("mensagens").add(message)
            .addOnSuccessListener {
                enviarNotificacao(destinatarioId, "Nova Mensagem", "Você recebeu uma nova mensagem.")
            }
    }

    private fun enviarNotificacao(userId: String, titulo: String, mensagem: String) {
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
}
