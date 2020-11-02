package com.adivid.roomdbsc.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.adivid.roomdbsc.R
import com.adivid.roomdbsc.db.Note
import com.adivid.roomdbsc.db.NoteDatabase
import kotlinx.android.synthetic.main.fragment_add_note.*
import kotlinx.coroutines.launch


class AddNoteFragment : BaseFragment() {

    private var note: Note? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_add_note, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        arguments?.let {
            note = AddNoteFragmentArgs.fromBundle(it).note
            et_title.setText(note?.title)
            et_note.setText(note?.note)

        }

        floatingActionButton.setOnClickListener { view ->

            val noteTitle = et_title.text.toString()
            val noteBody = et_note.text.toString()

            if (noteTitle.isEmpty()) {
                et_title.error = "title required"
                et_title.requestFocus()
                return@setOnClickListener
            }
            if (noteBody.isEmpty()) {
                et_note.error = "note required"
                et_note.requestFocus()
                return@setOnClickListener
            }


            launch {

                context?.let {

                    val mNote = Note(noteTitle, noteBody)

                    if (note == null) {
                        NoteDatabase(it).getNoteDao().addNote(mNote)
                        it.toast("note saved")
                    } else {
                        mNote.id = note!!.id
                        NoteDatabase(it).getNoteDao().updateNote(mNote)
                        it.toast("note updated")
                    }

                    val action = AddNoteFragmentDirections.actionAddNoteFragmentToHomeFragment()
                    Navigation.findNavController(view).navigate(action)


                }
            }


        }


    }

    private fun deleteNote() {
        AlertDialog.Builder(context).apply {

            setTitle("Are you sure?")
            setMessage("You cannot undo this operation")
            setPositiveButton("Yes") { _, _ ->

                launch {
                    NoteDatabase(context).getNoteDao().deleteNote(note!!)
                    val action = AddNoteFragmentDirections.actionAddNoteFragmentToHomeFragment()
                    Navigation.findNavController(requireView()).navigate(action)
                }
            }
            setNegativeButton("No") { _, _ ->

            }

        }.create().show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.delete -> if (note != null) deleteNote() else context?.toast("Cannot Delete")

        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)

    }

    companion object {
        private const val TAG = "AddNoteFragment"
    }
}