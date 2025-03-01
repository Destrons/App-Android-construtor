package com.Construtor.client.ui.pagamento

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.Construtor.client.databinding.ActivityPagamentoBinding
import com.stripe.android.PaymentConfiguration
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult
import com.stripe.android.paymentsheet.model.PaymentIntentClientSecret
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

class PagamentoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPagamentoBinding
    private lateinit var paymentSheet: PaymentSheet
    private var paymentIntentClientSecret: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPagamentoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        PaymentConfiguration.init(this, getString(R.string.STRIPE_PUBLIC_KEY))

        paymentSheet = PaymentSheet(this, ::onPaymentResult)

        binding.btnPagar.setOnClickListener {
            iniciarPagamento()
        }
    }

    private fun iniciarPagamento() {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://seu-servidor.com/create-payment-intent")
            .get()
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                runOnUiThread { Toast.makeText(this, "Erro ao processar pagamento", Toast.LENGTH_SHORT).show() }
                return
            }

            val jsonObject = JSONObject(response.body!!.string())
            paymentIntentClientSecret = jsonObject.getString("clientSecret")

            runOnUiThread {
                paymentSheet.presentWithPaymentIntent(paymentIntentClientSecret!!)
            }
        }
    }

    private fun onPaymentResult(result: PaymentSheetResult) {
        when (result) {
            is PaymentSheetResult.Completed -> Toast.makeText(this, "Pagamento concluído!", Toast.LENGTH_LONG).show()
            is PaymentSheetResult.Canceled -> Toast.makeText(this, "Pagamento cancelado.", Toast.LENGTH_LONG).show()
            is PaymentSheetResult.Failed -> Toast.makeText(this, "Erro: ${result.error.message}", Toast.LENGTH_LONG).show()
        }
    }
}
