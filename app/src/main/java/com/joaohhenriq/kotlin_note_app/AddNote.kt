package com.joaohhenriq.kotlin_note_app

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.joaohhenriq.kotlin_note_app.db.DbManager
import kotlinx.android.synthetic.main.activity_add_note.*

class AddNote : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)
    }

    fun addItem(view: View) {
        var dbManager = DbManager(this)

        var values = ContentValues()
        values.put("Title", edtTitle.text.toString())
        values.put("Description", eftDescription.text.toString())

        val id = dbManager.insert(values)

        if(id > 0) {
            Toast.makeText(this, "Note added", Toast.LENGTH_LONG).show()
            finish()
        } else {
            Toast.makeText(this, "Fail to add note", Toast.LENGTH_LONG).show()
        }

    }
}