package Construtor.client.ui.mapa

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.Construtor.client.databinding.ActivityMapaBinding

class MapaActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityMapaBinding
    private lateinit var mMap: GoogleMap
    private var localizacaoSelecionada: LatLng? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager.findFragmentById(binding.map.id) as SupportMapFragment
        mapFragment.getMapAsync(this)

        binding.btnConfirmarLocal.setOnClickListener {
            localizacaoSelecionada?.let { local ->
                val resultIntent = Intent().apply {
                    putExtra("LATITUDE", local.latitude)
                    putExtra("LONGITUDE", local.longitude)
                }
                setResult(RESULT_OK, resultIntent)
                finish()
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Localização inicial (Brasil)
        val localInicial = LatLng(-14.2350, -51.9253)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(localInicial, 4f))

        mMap.setOnMapClickListener { latLng ->
            mMap.clear()
            mMap.addMarker(MarkerOptions().position(latLng).title("Local Selecionado"))
            localizacaoSelecionada = latLng
        }
    }
}
