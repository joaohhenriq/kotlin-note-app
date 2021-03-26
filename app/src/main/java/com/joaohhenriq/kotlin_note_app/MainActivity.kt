package com.joaohhenriq.kotlin_note_app

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.SearchView
import android.widget.Toast
import com.joaohhenriq.kotlin_note_app.model.NoteModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.card_component.view.*

class MainActivity : AppCompatActivity() {

    var noteList = ArrayList<NoteModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadInitialData()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        val searcMenu = menu?.findItem(R.id.search_note)?.actionView as SearchView
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searcMenu.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searcMenu.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                Toast.makeText(applicationContext, query, Toast.LENGTH_LONG).show()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.addNote -> {
                var intent = Intent(this, AddNote::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun loadInitialData() {
        noteList.add(NoteModel(1, "Note 1", "Description 1"))
        noteList.add(NoteModel(2, "Note 2", "Description 2"))
        noteList.add(NoteModel(3, "Note 3", "Description 3"))

        var noteAdapter = NotesAdapter(noteList)
        lstViewNotes.adapter = noteAdapter
    }

    inner class NotesAdapter: BaseAdapter {
        var noteList = ArrayList<NoteModel>()
        constructor(noteList : ArrayList<NoteModel>):super() {
            this.noteList = noteList
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var view = layoutInflater.inflate(R.layout.card_component, null)
            var note = noteList[position]

            view.txtTitle.text = note.name
            view.txtContent.text = note.description

            return view
        }

        override fun getItem(position: Int): Any {
            return noteList[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return noteList.size
        }

    }
}