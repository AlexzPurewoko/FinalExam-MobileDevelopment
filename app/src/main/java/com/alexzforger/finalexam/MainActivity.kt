package com.alexzforger.finalexam

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerview_state)
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        lifecycleScope.launch(Dispatchers.IO) {
            val stateCountry = RequestAPI.getStateCountryDataset()

            withContext(Dispatchers.Main) {
                recyclerView.adapter = StateListRecylerAdapter(stateCountry.data) {
                    navigateToDetailCountry(it)
                }
            }
        }
    }

    private fun navigateToDetailCountry(stateMetadata: StateMetadata) {
        val intent = Intent(this, DetailStateActivity::class.java)
        intent.putExtra("state_metadata", stateMetadata)

        startActivity(intent)
    }
}