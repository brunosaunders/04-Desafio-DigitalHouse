package com.example.desafio04digitalhouse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.desafio04digitalhouse.domain.Game
import com.google.android.material.card.MaterialCardView


class CardFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_card, container, false)

        val game = arguments?.getSerializable("game") as Game

        view.findViewById<TextView>(R.id.tv_card_title).text = game.title
        view.findViewById<TextView>(R.id.tv_card_year).text = "Lançamento: ${game.launchYear}"
        view.findViewById<TextView>(R.id.tv_card_description).text = game.description

        Glide.with(view).load(game.imagePath).into(view.findViewById(R.id.iv_card_image))
        view.findViewById<MaterialCardView>(R.id.mcv_edit_card).setOnClickListener {
            val action = CardFragmentDirections.actionCardFragmentToEditGameFragment(game)
            findNavController().navigate(action)
        }

        return view
    }
}