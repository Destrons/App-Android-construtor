package com.Construtor.client.ui.favoritos

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.Construtor.client.databinding.ActivityFavoritosBinding
import com.Construtor.client.model.Favorito
import com.Construtor.client.ui.perfil.PerfilPrestadorActivity

class FavoritosActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoritosBinding
    private val db = FirebaseFirestore.getInstance()
    private val userId = FirebaseAuth.getInstance().currentUser?.uid
    private lateinit var favoritosAdapter: FavoritosAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoritosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        favoritosAdapter = FavoritosAdapter(emptyList()) { prestadorId ->
            val intent = Intent(this, PerfilPrestadorActivity::class.java)
            intent.putExtra("PRESTADOR_ID", prestadorId)
            startActivity(intent)
        }

        binding.recyclerFavoritos.layoutManager = LinearLayoutManager(this)
        binding.recyclerFavoritos.adapter = favoritosAdapter

        carregarFavoritos()
    }

    private fun carregarFavoritos() {
        db.collection("favoritos").document(userId!!)
            .collection("lista")
            .get()
            .addOnSuccessListener { result ->
                val favoritos = result.documents.map { it.toObject(Favorito::class.java)!! }
                favoritosAdapter.atualizarLista(favoritos)
            }
    }
}