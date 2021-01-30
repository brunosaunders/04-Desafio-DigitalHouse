package com.example.desafio04digitalhouse

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.desafio04digitalhouse.adapters.GamesAdapter
import com.example.desafio04digitalhouse.adapters.NavigateCardAction
import com.example.desafio04digitalhouse.domain.Game
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class HomeFragment : Fragment() {
    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        database = Firebase.database.reference
        val GamesAdapter = GamesAdapter(NavigateCardAction { game ->
            val action = HomeFragmentDirections.actionHomeFragmentToCardFragment(game)
            findNavController().navigate(action)
        })

        val gamesListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val games = mutableListOf<Game>()
                if (snapshot.exists()) {
                    snapshot.children.forEach {
                        it.getValue(Game::class.java)?.let {
                            games.add(it)
                        }
                    }
                }
                GamesAdapter.submitList(games)

//                val data = snapshot.getValue<List<Game>>()
//                Log.i("database", data.toString())
//                GamesAdapter.submitList(data?.filterIndexed { index, game -> index != 0 })
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("HomeFragment", "loadGame:onCancelled", error.toException())
            }
        }
        val games = database.child("games")
        games.addValueEventListener(gamesListener)

        view.findViewById<RecyclerView>(R.id.rv_games).apply {
            adapter = GamesAdapter
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
        }

        view.findViewById<SearchView>(R.id.searchView).apply {
            setOnSearchClickListener {
                hide(view)
            }
            setOnCloseListener {
                show(view)
                false
            }
        }

        view.findViewById<FloatingActionButton>(R.id.floatingActionButton).setOnClickListener {
            navigateToRegisterGame()
        }

        return view
    }

    private fun addGame(
        gameId: String,
        title: String,
        launchYear: String,
        description: String,
        imagePath: String
    ) {
        val game = Game(title, launchYear, description, imagePath)
        database.child("games").child(gameId).setValue(game)
    }

    private fun hide(view: View) {
        val hint = view.findViewById<TextView>(R.id.tv_query_hint)
        val micro = view.findViewById<ImageView>(R.id.icon_micro)

        hint.visibility = View.GONE
        micro.visibility = View.GONE
    }

    private fun show(view: View) {
        val hint = view.findViewById<TextView>(R.id.tv_query_hint)
        val micro = view.findViewById<ImageView>(R.id.icon_micro)

        hint.visibility = View.VISIBLE
        micro.visibility = View.VISIBLE
    }

    private fun navigateToRegisterGame() {
        findNavController().navigate(R.id.action_homeFragment_to_registerGameFragment)
    }
}