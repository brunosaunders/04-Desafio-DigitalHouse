package com.example.desafio04digitalhouse.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.desafio04digitalhouse.R
import com.example.desafio04digitalhouse.domain.Game
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class GamesAdapter: ListAdapter<Game, GamesAdapter.GamesViewHolder>(GameDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GamesViewHolder {
        return GamesViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: GamesViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class GamesViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val image = view.findViewById<ImageView>(R.id.iv_game_image)
        val title = view.findViewById<TextView>(R.id.tv_game_title)
        val year = view.findViewById<TextView>(R.id.tv_game_year)

        fun bind(item: Game) {
            title.text = item.title
            year.text = item.launchYear

            val storageReference = Firebase.storage.reference
            val imageReference = item.imagePath?.let { storageReference.child("images").child(it) }
            Glide.with(view).load(imageReference).into(image)
        }

        companion object {
            fun from(parent: ViewGroup): GamesViewHolder {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_game, parent, false)
                return GamesViewHolder(view)
            }
        }
    }
}

class GameDiff : DiffUtil.ItemCallback<Game>() {
    override fun areItemsTheSame(oldItem: Game, newItem: Game): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Game, newItem: Game): Boolean {
        return oldItem.launchYear == newItem.launchYear
    }
}