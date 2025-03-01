package com.Construtor.client.ui.suporte

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.Construtor.client.databinding.ActivityListaTicketsBinding
import com.Construtor.client.model.SuporteTicket

class ListaTicketsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListaTicketsBinding
    private val db = FirebaseFirestore.getInstance()
    private val userId = FirebaseAuth.getInstance().currentUser?.uid
    private lateinit var ticketAdapter: TicketAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListaTicketsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ticketAdapter = TicketAdapter(emptyList()) { ticketId ->
            val intent = Intent(this, ChatSuporteActivity::class.java)
            intent.putExtra("TICKET_ID", ticketId)
            startActivity(intent)
        }

        binding.recyclerTickets.layoutManager = LinearLayoutManager(this)
        binding.recyclerTickets.adapter = ticketAdapter

        carregarTickets()
    }

    private fun carregarTickets() {
        db.collection("suporte_tickets")
            .whereEqualTo("usuarioId", userId)
            .get()
            .addOnSuccessListener { result ->
                val tickets = result.documents.map { it.toObject(SuporteTicket::class.java)!! }
                ticketAdapter.atualizarLista(tickets)
            }
    }
}