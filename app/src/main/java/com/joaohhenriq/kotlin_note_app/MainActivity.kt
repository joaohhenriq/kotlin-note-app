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
import com.joaohhenriq.kotlin_note_app.db.DbManager
import com.joaohhenriq.kotlin_note_app.model.NoteModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.card_component.view.*

class MainActivity : AppCompatActivity() {

    private var noteList = ArrayList<NoteModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadQuery("%")
    }

    override fun onResume() {
        super.onResume()
        loadQuery("%")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        val searcMenu = menu?.findItem(R.id.search_note)?.actionView as SearchView
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searcMenu.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searcMenu.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                Toast.makeText(applicationContext, query, Toast.LENGTH_LONG).show()
                loadQuery("%$query%")
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText!!.isEmpty()) {
                    loadQuery("%")
                }
                return false
            }

        })

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.addNote -> {
                val intent = Intent(this, AddNote::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun loadQuery(title: String) {
        this.noteList.clear()

        val dbManager = DbManager(this)
        val projection = arrayOf("ID", "Title", "Description")
        val selectionArgs = arrayOf(title)
        val cursor = dbManager.query(projection, "Title like ?", selectionArgs, "Description")

        if(cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex("ID"))
                val title = cursor.getString(cursor.getColumnIndex("Title"))
                val desc = cursor.getString(cursor.getColumnIndex("Description"))

                noteList.add(NoteModel(id, title, desc))

            } while(cursor.moveToNext())
        }

        val noteAdapter = NotesAdapter(this, noteList)
        lstViewNotes.adapter = noteAdapter
    }

    inner class NotesAdapter: BaseAdapter {
        private var noteList = ArrayList<NoteModel>()
        private var context: Context
        constructor(context: Context, noteList : ArrayList<NoteModel>):super() {
            this.noteList = noteList
            this.context = context
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view = layoutInflater.inflate(R.layout.card_component, null)
            val note = noteList[position]

            view.txtTitle.text = note.name
            view.txtContent.text = note.description

            view.btnDelete.setOnClickListener(View.OnClickListener {
                val dbManager = DbManager(context)
                val selectionArgs = arrayOf(note.id.toString())
                dbManager.delete("ID = ?", selectionArgs)
                loadQuery("%")
            })

            view.btnEdit.setOnClickListener(View.OnClickListener {
                goToUpdate(note)
            })

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

    fun goToUpdate(note: NoteModel) {
        var intent = Intent(this, AddNote::class.java)
        intent.putExtra("ID", note.id)
        intent.putExtra("Title", note.name)
        intent.putExtra("Description", note.description)
        startActivity(intent)
    }
}