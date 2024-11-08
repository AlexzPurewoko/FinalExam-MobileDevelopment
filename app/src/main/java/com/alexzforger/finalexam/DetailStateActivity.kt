package com.alexzforger.finalexam

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.IntentCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

class DetailStateActivity : AppCompatActivity() {
    private lateinit var stateFlagsImage: ImageView
    private lateinit var stateLabelText: TextView
    private lateinit var populationText: TextView

    private lateinit var mapView: MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail_state)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        Configuration.getInstance().load(
            applicationContext,
            getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE)
        )

        stateFlagsImage = findViewById(R.id.state_flag)
        stateLabelText  = findViewById(R.id.state_label)
        populationText  = findViewById(R.id.state_population)

        mapView = findViewById(R.id.osmMapView)
        mapView.setTileSource(TileSourceFactory.MAPNIK)
        mapView.setMultiTouchControls(true)


        IntentCompat.getParcelableExtra(intent, "state_metadata", StateMetadata::class.java)?.run {
            applyMetadataExtrasToUI(this)
            supportActionBar?.title = state
            supportActionBar?.title = state
            lifecycleScope.launch(Dispatchers.IO) {
                val capitalLocation = RequestAPI.getGeocodedState(this@run)

                withContext(Dispatchers.Main) {
                    updateMapsUIWithCapitalLocation(capitalLocation)
                }
            }
        }
    }

    private fun applyMetadataExtrasToUI(stateMetadata: StateMetadata) {
        val stateCodeAndFlag = mapOfStateSlugAndFlags[stateMetadata.stateSlug] ?: return

        Glide.with(this)
            .load(stateCodeAndFlag.getFlagUrl("h40"))
            .into(stateFlagsImage)

        stateLabelText.text = stateMetadata.state
        populationText.text = "Population: ${stateMetadata.population}"
    }

    private fun updateMapsUIWithCapitalLocation(capitalLocation: CityGeoCodingSearchResult) {
        val geoPoint = GeoPoint(capitalLocation.latitude, capitalLocation.longitude)
        mapView.controller.animateTo(geoPoint)
        mapView.controller.setZoom(15)

        val marker = Marker(mapView).apply {
            title = capitalLocation.name + " (Capital)"
            position = geoPoint
            setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
            showInfoWindow()
        }

        mapView.overlays.clear()
        mapView.overlays.add(marker)
        mapView.invalidate()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }

        return super.onOptionsItemSelected(item)
    }
}