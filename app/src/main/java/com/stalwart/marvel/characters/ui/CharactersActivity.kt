package com.stalwart.marvel.characters.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.stalwart.data.characters.model.Character
import com.stalwart.domain.Status
import com.stalwart.marvel.databinding.ActivityCharactersBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharactersActivity : AppCompatActivity() {

    private val characterViewModel: CharactersViewModel by viewModels()
    private lateinit var binding: ActivityCharactersBinding
    private lateinit var characterAdapter: CharactersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharactersBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setupUi()
        setUpObserver()
    }

    private fun setupUi() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        characterAdapter = CharactersAdapter(arrayListOf())
        binding.recyclerView.adapter = characterAdapter
    }

    private fun setUpObserver() {
        characterViewModel.characters.observe(this, {
            when(it.status) {

                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                }

                Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    it.data?.let { characters -> renderList(characters) }
                    binding.recyclerView.visibility = View.VISIBLE
                }

                Status.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun renderList(characters: List<Character>) {
        characterAdapter.addData(characters)
        characterAdapter.notifyDataSetChanged()
    }
}