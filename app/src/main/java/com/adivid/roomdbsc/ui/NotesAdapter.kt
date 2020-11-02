package com.adivid.roomdbsc.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.adivid.roomdbsc.R
import com.adivid.roomdbsc.db.Note
import kotlinx.android.synthetic.main.note_layout.view.*

class NotesAdapter(val notes: List<Note>) : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.note_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.view.tv_title.text = notes[position].title
        holder.view.tv_body.text = notes[position].note

        holder.view.setOnClickListener{
            val action = HomeFragmentDirections.actionHomeFragmentToAddNoteFragment()
            action.note = notes[position]
            Navigation.findNavController(it).navigate(action)
        }

    }

    override fun getItemCount() = notes.size


    class NoteViewHolder(val view: View) : RecyclerView.ViewHolder(view)
}