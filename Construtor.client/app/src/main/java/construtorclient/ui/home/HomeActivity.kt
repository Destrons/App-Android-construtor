package construtorclient.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import construtorclient.R
import construtorclient.databinding.ActivityHomeBinding
import construtorclient.ui.chat.ChatFragment
import construtorclient.ui.cronograma.CronogramaFragment
import construtorclient.ui.perfil.PerfilFragment
import construtorclient.ui.solicitacao.SolicitacoesFragment
import construtorclient.ui.notifications.FirebaseTokenManager


class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseTokenManager.saveToken()
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