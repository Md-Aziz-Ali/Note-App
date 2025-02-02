package com.example.loginandsignupwithfirebase

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.loginandsignupwithfirebase.databinding.ActivityAddNoteBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddNote : AppCompatActivity() {
    private val binding: ActivityAddNoteBinding by lazy {
        ActivityAddNoteBinding.inflate(layoutInflater)
    }

    private lateinit var databaseReference: DatabaseReference
    private lateinit  var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        databaseReference = FirebaseDatabase.getInstance().reference
        auth = FirebaseAuth.getInstance()

        binding.button1.setOnClickListener {
            val title = binding.editTitle.text.toString()
            val description = binding.editDescription.text.toString()

            if(title.isEmpty() && description.isEmpty()) {
                Toast.makeText(this, "Fill both field", Toast.LENGTH_SHORT).show()
            }
            else {
                val currentUser = auth.currentUser
                currentUser?. let{ user->
                    val noteKey: String? = databaseReference.child("users").child(user.uid)
                        .child("notes").push().key

                    val noteItem = NoteItem(title,description)
                    if(noteKey != null) {
                        databaseReference.child("users").child(user.uid).child("notes")
                            .child(noteKey).setValue(noteItem)
                            .addOnCompleteListener {task->
                                if(task.isSuccessful) {
                                    Toast.makeText(this, "Note Save Successful", Toast.LENGTH_SHORT).show()
                                    finish()
                                }
                                else {
                                    Toast.makeText(this, "Failed to Save Note", Toast.LENGTH_SHORT).show()
                                }
                            }
                    }
                }
            }
        }
    }
}