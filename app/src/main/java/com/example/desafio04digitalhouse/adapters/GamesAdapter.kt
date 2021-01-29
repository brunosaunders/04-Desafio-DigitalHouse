package com.example.desafio04digitalhouse.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.desafio04digitalhouse.R

class GamesAdapter: RecyclerView.Adapter<GamesAdapter.GamesViewHolder>() {
    val data = listOf("oi")
    override fun getItemCount() = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GamesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return GamesViewHolder(inflater.inflate(R.layout.item_list_game, parent, false))
    }

    override fun onBindViewHolder(holder: GamesViewHolder, position: Int) {

    }

    class GamesViewHolder(val view: View) : RecyclerView.ViewHolder(view)
}