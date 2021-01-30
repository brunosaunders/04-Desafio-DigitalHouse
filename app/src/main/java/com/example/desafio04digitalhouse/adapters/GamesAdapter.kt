package com.example.desafio04digitalhouse.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import com.example.desafio04digitalhouse.R
import com.example.desafio04digitalhouse.domain.Game
import com.firebase.ui.storage.images.FirebaseImageLoader
import com.google.android.material.card.MaterialCardView
import com.google.firebase.storage.StorageReference
import java.io.InputStream


class GamesAdapter(val navigate: NavigateCardAction) :
    ListAdapter<Game, GamesAdapter.GamesViewHolder>(GameDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GamesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_list_game,
            parent,
            false
        )
        return GamesViewHolder(view)
    }

    override fun onBindViewHolder(holder: GamesViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class GamesViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val image = view.findViewById<ImageView>(R.id.iv_game_image)
        val title = view.findViewById<TextView>(R.id.tv_game_title)
        val year = view.findViewById<TextView>(R.id.tv_game_year)
        val materialCard = view.findViewById<MaterialCardView>(R.id.mcv_card)

        fun bind(item: Game?) {
            title.text = item?.title
            year.text = item?.launchYear
            materialCard.setOnClickListener {
                item?.let {
                    navigate.click(item)
                }
            }

            Glide.with(view).load(item?.imagePath).into(image)
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

@GlideModule
class MyAppGlideModule : AppGlideModule() {
    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        // Register FirebaseImageLoader to handle StorageReference
        registry.append(
            StorageReference::class.java, InputStream::class.java,
            FirebaseImageLoader.Factory()
        )
    }
}

class NavigateCardAction(private val onClick: (Game) -> Unit) {
    fun click(game: Game) = onClick(game)
}