package com.stalwart.marvel.details.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import com.stalwart.data.details.model.CharacterDetailsResponse
import com.stalwart.data.details.model.ComicsDetails
import com.stalwart.domain.Status
import com.stalwart.marvel.databinding.ActivityDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {

    private val detailsViewModel: DetailsViewModel by viewModels()
    private lateinit var binding: ActivityDetailsBinding

    private lateinit var characterId: String
    private lateinit var comicsAdapter: ComicsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setupUi()
        characterId = intent.getStringExtra(EXTRAS_CHARACTER_ID).toString()
        detailsViewModel.getCharacterDetails(characterId)
        setUpObserver()
    }

    private fun setupUi() {
        binding.comicsRecyclerView.layoutManager = LinearLayoutManager(this)
        comicsAdapter = ComicsAdapter(arrayListOf())
        binding.comicsRecyclerView.adapter = comicsAdapter
    }

    private fun setUpObserver() {
        detailsViewModel.details.observe(this, {
            when(it.status) {
                Status.LOADING -> {
                    binding.detailsProgressBar.visibility = View.VISIBLE
                    hideUiComponents()
                }

                Status.SUCCESS -> {
                    binding.detailsProgressBar.visibility = View.GONE
                    it.data?.let { response -> renderUi(response) }
                }

                Status.ERROR -> {
                    binding.detailsProgressBar.visibility = View.GONE
                    hideUiComponents()
                }
            }
        })
    }

    private fun renderUi(details: CharacterDetailsResponse) {
        // Load header image
        Picasso.get()
            .load("${details.data.results[0].thumbnail.path}.${details.data.results[0].thumbnail.extension}")
            .into(binding.characterDetailsImage)

        // Set character name
        binding.characterDetailsName.text = details.data.results[0].name

        // Render comics list
        renderComicsList(details.data.results[0].comics.items)

        showUiComponents()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun renderComicsList(comics: List<ComicsDetails>) {
        comicsAdapter.addData(comics)
        comicsAdapter.notifyDataSetChanged()
    }

    private fun showUiComponents() {
        with(binding) {
            characterDetailsImage.visibility = View.VISIBLE
            characterDetailsName.visibility = View.VISIBLE
            relatedComics.visibility = View.VISIBLE
            comicsRecyclerView.visibility = View.VISIBLE
        }
    }

    private fun hideUiComponents() {
        with(binding) {
            characterDetailsImage.visibility = View.GONE
            characterDetailsName.visibility = View.GONE
            relatedComics.visibility = View.GONE
            comicsRecyclerView.visibility = View.GONE
        }
    }

    companion object {
        internal const val EXTRAS_CHARACTER_ID = "characterId"

        fun getStartIntent(context: Context, characterId: String) : Intent {
            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra(EXTRAS_CHARACTER_ID, characterId)
            return intent
        }
    }
}