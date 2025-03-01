package Construtor.client.ui.solicitacao

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.Construtor.client.databinding.ActivityNovaSolicitacaoBinding
import Construtor.client.ui.mapa.MapaActivity

class NovaSolicitacaoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNovaSolicitacaoBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private var latitude: Double? = null
    private var longitude: Double? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNovaSolicitacaoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        binding.btnSelecionarLocal.setOnClickListener {
            val intent = Intent(this, MapaActivity::class.java)
            startActivityForResult(intent, 100)
        }

        binding.btnEnviarSolicitacao.setOnClickListener {
            enviarSolicitacao()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == RESULT_OK) {
            latitude = data?.getDoubleExtra("LATITUDE", 0.0)
            longitude = data?.getDoubleExtra("LONGITUDE", 0.0)
            binding.tvLocalizacao.text = "Localização selecionada: $latitude, $longitude"
        }
    }

    private fun enviarSolicitacao() {
        val tipoServico = binding.etTipoServico.text.toString()
        val descricao = binding.etDescricao.text.toString()
        val custoEstimado = binding.etCusto.text.toString()
        val userId = auth.currentUser?.uid

        if (tipoServico.isNotEmpty() && descricao.isNotEmpty() && custoEstimado.isNotEmpty() && latitude != null && longitude != null) {
            val novaObra = hashMapOf(
                "clienteId" to userId,
                "tipo_servico" to tipoServico,
                "descricao" to descricao,
                "custo_estimado" to custoEstimado.toDouble(),
                "latitude" to latitude,
                "longitude" to longitude,
                "status" to "pendente"
            )

            db.collection("obras").add(novaObra).addOnSuccessListener {
                Toast.makeText(this, "Solicitação enviada!", Toast.LENGTH_LONG).show()
                finish()
            }.addOnFailureListener {
                Toast.makeText(this, "Erro ao enviar solicitação", Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_LONG).show()
        }
    }
}
