package com.example.desafio04digitalhouse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        view.findViewById<SearchView>(R.id.searchView).apply {
            setOnSearchClickListener {
                hide(view)
            }
            setOnCloseListener {
                show(view)
                false
            }
        }

        return view
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
}