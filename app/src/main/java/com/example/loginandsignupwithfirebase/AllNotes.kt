package com.example.loginandsignupwithfirebase

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.loginandsignupwithfirebase.databinding.ActivityAllNotesBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AllNotes : AppCompatActivity() {

    private val binding: ActivityAllNotesBinding by lazy {
        ActivityAllNotesBinding.inflate(layoutInflater)
    }

    private lateinit var databaseReference: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var recyclerView: RecyclerView

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
        recyclerView = binding.noteRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)

        val currentUser = auth.currentUser
        currentUser?.let { user ->
            val noteReference = databaseReference.child("users").child(user.uid)
                .child("notes")
            noteReference.addValueEventListener(object : ValueEventListener {


                override fun onDataChange(snapshot: DataSnapshot) {
                    val noteList = mutableListOf<NoteItem>()
                    for(noteSnapshot in snapshot.children) {
                        val note = noteSnapshot.getValue(NoteItem::class.java)
                        note?.let {
                            noteList.add(it)
                        }
                    }
                    noteList.reverse()
                    val adapter = NoteApter(noteList)
                    recyclerView.adapter = adapter
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
        }
    }
}