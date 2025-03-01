package com.Construtor.client.ui.suporte

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.Construtor.client.databinding.ActivitySuporteBinding
import com.Construtor.client.model.SuporteTicket

class SuporteActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySuporteBinding
    private val db = FirebaseFirestore.getInstance()
    private val userId = FirebaseAuth.getInstance().currentUser?.uid

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySuporteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnEnviarTicket.setOnClickListener {
            abrirTicket()
        }
    }

    private fun abrirTicket() {
        val titulo = binding.etTitulo.text.toString()
        val descricao = binding.etDescricao.text.toString()

        if (titulo.isNotEmpty() && descricao.isNotEmpty()) {
            val ticket = SuporteTicket(
                usuarioId = userId!!,
                tipoUsuario = "cliente",
                titulo = titulo,
                descricao = descricao
            )

            db.collection("suporte_tickets").add(ticket)
                .addOnSuccessListener {
                    Toast.makeText(this, "Ticket enviado com sucesso!", Toast.LENGTH_LONG).show()
                    finish()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Erro ao enviar ticket.", Toast.LENGTH_LONG).show()
                }
        } else {
            Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_LONG).show()
        }
    }
}