package com.stalwart.marvel.details.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.stalwart.data.details.model.ComicsDetails
import com.stalwart.marvel.R
import com.stalwart.marvel.databinding.ItemComicsRowBinding
import java.util.ArrayList

/**
Created by Swanand Deshpande
 */
class ComicsAdapter(
    private val comicList: ArrayList<ComicsDetails>
): RecyclerView.Adapter<ComicsAdapter.ComicViewHolder>() {

    class ComicViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val binding = ItemComicsRowBinding.bind(itemView)
        fun bindData(comic: ComicsDetails) {
            binding.comicName.text = comic.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicViewHolder =
        ComicViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_comics_row, parent, false))

    override fun onBindViewHolder(holder: ComicViewHolder, position: Int) = holder.bindData(comicList[position])

    override fun getItemCount(): Int = comicList.size

    fun addData(listOfComics: List<ComicsDetails>) {
        comicList.addAll(listOfComics)
    }
}