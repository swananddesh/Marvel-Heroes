package com.stalwart.marvel.details.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.stalwart.domain.Status
import com.stalwart.marvel.R
import com.stalwart.marvel.databinding.ActivityDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {

    private val detailsViewModel: DetailsViewModel by viewModels()
    private lateinit var binding: ActivityDetailsBinding

    private lateinit var characterId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        characterId = intent.getStringExtra("characterId").toString()
        detailsViewModel.getCharacterDetails(characterId)
        setUpObserver()
    }

    private fun setUpObserver() {
        detailsViewModel.details.observe(this, {
            when(it.status) {
                Status.LOADING -> {
                    Toast.makeText(this, "Loading", Toast.LENGTH_LONG).show()
                }

                Status.SUCCESS -> {
                    Toast.makeText(this, "Success", Toast.LENGTH_LONG).show()
                }

                Status.ERROR -> {
                    Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
                }
            }
        })
    }
}