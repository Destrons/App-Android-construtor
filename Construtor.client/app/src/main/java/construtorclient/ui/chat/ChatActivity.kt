package com.Construtor.client.ui.chat

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.storage.FirebaseStorage
import com.Construtor.client.databinding.ActivityChatBinding
import com.Construtor.client.model.Message
import java.util.*

class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding
    private val chatViewModel: ChatViewModel by viewModels()
    private lateinit var chatAdapter: ChatAdapter
    private var chatId: String = ""
    private var destinatarioId: String = ""
    private val storageRef = FirebaseStorage.getInstance().reference

    private val imagePicker = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let { uploadImageToFirebase(it) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        chatId = intent.getStringExtra("CHAT_ID") ?: ""
        destinatarioId = intent.getStringExtra("DESTINATARIO_ID") ?: ""

        chatAdapter = ChatAdapter(emptyList())
        binding.recyclerChat.apply {
            layoutManager = LinearLayoutManager(this@ChatActivity)
            adapter = chatAdapter
        }

        chatViewModel.messages.observe(this) { messages ->
            chatAdapter = ChatAdapter(messages)
            binding.recyclerChat.adapter = chatAdapter
            binding.recyclerChat.scrollToPosition(messages.size - 1)
        }

        chatViewModel.loadMessages(chatId)

        binding.btnEnviar.setOnClickListener {
            val texto = binding.etMensagem.text.toString()
            if (texto.isNotEmpty()) {
                chatViewModel.sendMessage(chatId, destinatarioId, texto)
                binding.etMensagem.text.clear()
            }
        }

        binding.btnEnviarImagem.setOnClickListener {
            imagePicker.launch("image/*")
        }

        binding.btnChamadaVideo.setOnClickListener {
            val intent = Intent(this, CallActivity::class.java)
            startActivity(intent)
        }

    }

    private fun uploadImageToFirebase(uri: Uri) {
        val fileName = "images/${UUID.randomUUID()}.jpg"
        val fileRef = storageRef.child(fileName)

        fileRef.putFile(uri).addOnSuccessListener {
            fileRef.downloadUrl.addOnSuccessListener { downloadUri ->
                chatViewModel.sendMessage(chatId, destinatarioId, downloadUri.toString())
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Falha ao enviar imagem!", Toast.LENGTH_SHORT).show()
        }
    }
}
