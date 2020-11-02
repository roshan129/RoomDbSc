package com.adivid.roomdbsc.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.adivid.roomdbsc.R
import com.adivid.roomdbsc.db.NoteDatabase
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.launch


class HomeFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        recyclerView.setHasFixedSize(true)

        recyclerView.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)



        launch {
            context?.let {
                val notes = NoteDatabase(it).getNoteDao().getAllNotes()
                recyclerView.adapter = NotesAdapter(notes)

            }
        }


        floatingActionButton.setOnClickListener{

            val  action =  HomeFragmentDirections.actionHomeFragmentToAddNoteFragment()
            Navigation.findNavController(it).navigate(action)
        }


    }

    companion object {

        private const val TAG = "HomeFragment"
    }
}