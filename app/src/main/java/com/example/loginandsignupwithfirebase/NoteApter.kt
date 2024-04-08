package com.example.loginandsignupwithfirebase

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.loginandsignupwithfirebase.databinding.NotesitemBinding

class NoteApter(private val notes: List<NoteItem>)
    : RecyclerView.Adapter<NoteApter.NoteViewHolder>() {
    class NoteViewHolder(private val binding: NotesitemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(note: NoteItem) {
            binding.textView9.text = note.title
            binding.textView10.text = note.description
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = NotesitemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return NoteViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.bind(note)
    }
}