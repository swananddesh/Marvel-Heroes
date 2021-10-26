package com.stalwart.marvel.characters.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.stalwart.data.characters.model.Character
import com.stalwart.marvel.R
import com.stalwart.marvel.databinding.ItemCharacterRowBinding
import java.util.ArrayList

/**
Created by Swanand Deshpande
 */
class CharactersAdapter(
    private val characters: ArrayList<Character>,
    private val listener: (Character) -> Unit
) : RecyclerView.Adapter<CharactersAdapter.CharacterViewHolder>() {

    class CharacterViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val binding = ItemCharacterRowBinding.bind(itemView)
        fun bindData(character: Character) {
            with(binding) {
                characterName.text = character.name
                Picasso.get().load("${character.thumbnail.path}.${character.thumbnail.extension}")
                    .error(R.drawable.ic_launcher_background)
                    .into(characterImage)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CharacterViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_character_row, parent, false))

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = characters[position]
        holder.bindData(character)
        holder.itemView.setOnClickListener { listener(character) }
    }

    override fun getItemCount(): Int = characters.size

    fun addData(listOfCharacters: List<Character>) {
        characters.addAll(listOfCharacters)
    }
}