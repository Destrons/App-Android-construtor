package construtorclient.ui.home

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import construtorclient.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()
        if (auth.currentUser != null) {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
            return
        }

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = FirebaseFirestore.getInstance()

        binding.btnRegister.setOnClickListener {
            val nome = binding.etNome.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val senha = binding.etPassword.text.toString()
            val confirmSenha = binding.etConfirmPassword.text.toString()

            if (nome.isNotEmpty() && email.isNotEmpty() && senha.isNotEmpty() && confirmSenha.isNotEmpty()) {
                if (senha.length >= 6) {
                    if (senha == confirmSenha) {
                        if (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                            registerUser(nome, email, senha)
                        } else {
                            Toast.makeText(this, "E-mail inválido!", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this, "As senhas não conferem!", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "A senha precisa ter no mínimo 6 caracteres!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Preencha todos os campos corretamente!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun registerUser(nome: String, email: String, senha: String) {
        auth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val userId = auth.currentUser!!.uid
                val user = hashMapOf(
                    "nome" to nome,
                    "email" to email,
                    "tipo_usuario" to "cliente"
                )
                db.collection("users").document(userId).set(user).addOnSuccessListener {
                    startActivity(Intent(this, HomeActivity::class.java))
                    finish()
                }
            } else {
                Toast.makeText(this, "Erro ao cadastrar: ${task.exception?.message}", Toast.LENGTH_LONG).show()
            }
        }
    }
}
