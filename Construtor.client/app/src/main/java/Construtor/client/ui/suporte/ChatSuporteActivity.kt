package com.Construtor.client.ui.suporte

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.Construtor.client.databinding.ActivityChatSuporteBinding
import com.Construtor.client.model.Message

class ChatSuporteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatSuporteBinding
    private val db = FirebaseFirestore.getInstance()
    private val userId = FirebaseAuth.getInstance().currentUser?.uid
    private var ticketId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatSuporteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ticketId = intent.getStringExtra("TICKET_ID") ?: ""

        binding.recyclerChat.layoutManager = LinearLayoutManager(this)
        carregarMensagens()

        binding.btnEnviar.setOnClickListener {
            enviarMensagem()
        }
    }

    private fun carregarMensagens() {
        db.collection("suporte_tickets").document(ticketId)
            .collection("mensagens")
            .orderBy("timestamp")
            .addSnapshotListener { snapshot, _ ->
                val mensagens = snapshot?.documents?.map { it.toObject(Message::class.java)!! } ?: listOf()
                // Atualizar RecyclerView com as mensagens
            }
    }

    private fun enviarMensagem() {
        val mensagem = Message(remetenteId = userId!!, destinatarioId = "suporte", mensagem = binding.etMensagem.text.toString())
        db.collection("suporte_tickets").document(ticketId)
            .collection("mensagens").add(mensagem)
    }
}