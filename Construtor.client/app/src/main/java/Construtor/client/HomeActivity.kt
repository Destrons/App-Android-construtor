package com.construtor.client.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.construtor.client.R
import com.construtor.client.databinding.ActivityHomeBinding
import com.construtor.client.ui.chat.ChatFragment
import com.construtor.client.ui.cronograma.CronogramaFragment
import com.construtor.client.ui.perfil.PerfilFragment
import com.construtor.client.ui.solicitacao.SolicitacoesFragment

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadFragment(SolicitacoesFragment())

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_solicitacoes -> loadFragment(SolicitacoesFragment())
                R.id.nav_cronograma -> loadFragment(CronogramaFragment())
                R.id.nav_chat -> loadFragment(ChatFragment())
                R.id.nav_perfil -> loadFragment(PerfilFragment())
            }
            true
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}