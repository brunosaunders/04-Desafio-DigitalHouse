package com.example.desafio04digitalhouse

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.desafio04digitalhouse.domain.Game
import com.google.android.material.card.MaterialCardView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference

import com.google.firebase.storage.ktx.storage
import de.hdodenhof.circleimageview.CircleImageView

class RegisterGameFragment: Fragment() {
    private lateinit var uri: Uri
    private lateinit var vieww: View
    private val CODE_IMG = 1000
    private lateinit var URL: String
    private lateinit var storageReference: StorageReference
    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vieww = inflater.inflate(R.layout.fragment_register_game, container, false)

        database = Firebase.database.reference
        storageReference = Firebase.storage.reference.child("images")
        vieww.findViewById<MaterialCardView>(R.id.btn_add_photo).setOnClickListener {
            sendIntent()
        }

        vieww.findViewById<Button>(R.id.btn_save_game).setOnClickListener {
            addGame(vieww)

        }

        return vieww
    }

    fun addGame(view: View) {
        if (validateForm(view)) {
            val title = view.findViewById<EditText>(R.id.edt_name).text.toString()
            val launchYear = view.findViewById<EditText>(R.id.edt_created_at).text.toString()
            val description = view.findViewById<EditText>(R.id.edt_description).text.toString()


            val imagesReference = storageReference.parent?.child("images/${title}")
            imagesReference?.putFile(uri)?.continueWithTask {

                imagesReference.downloadUrl
            }?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    URL = task.result.toString()
                    val game = Game(title, launchYear, description, URL)
                    database.child("games/${title}").setValue(game)
                }
            }

            activity?.onBackPressed()
        }
    }

    fun sendIntent(){
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Captura IMG"), CODE_IMG)
    }

    private fun validateForm(view: View): Boolean {
        var valid = true

        val name = view.findViewById<EditText>(R.id.edt_name)
        val createdAt = view.findViewById<EditText>(R.id.edt_created_at)
        val description = view.findViewById<EditText>(R.id.edt_description)

        if (name.text.toString().isEmpty()) {
            name.error = "Required"
            valid = false
        }

        if (createdAt.text.toString().isEmpty()) {
            createdAt.error = "Required"
            valid = false
        }
        if (description.text.toString().isEmpty()) {
            description.error = "Required"
            valid = false
        }

        return valid
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == CODE_IMG){
            val uploadTask = data?.data?.let {
                uri = it
                storageReference.putFile(it)
            }
            uploadTask?.continueWithTask {task ->
                if (task.isSuccessful) {
                    Toast.makeText(context, "Loading", Toast.LENGTH_SHORT).show()
                }
                storageReference.downloadUrl
            }?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val downloadUri = task.result
                    Log.i("RegisterGame,", downloadUri.toString())

                    context?.let {
                        Glide.with(it)
                            .load(downloadUri.toString())
                            .into(vieww.findViewById(R.id.civ_game_image))
                        vieww.findViewById<CircleImageView>(R.id.civ_game_image).visibility = View.VISIBLE
                    }
                } else {
                    Log.i("RegisterGame,", task.exception.toString())
                }
            }
        }
    }
}