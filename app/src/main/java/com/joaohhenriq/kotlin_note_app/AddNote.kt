package com.joaohhenriq.kotlin_note_app

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.joaohhenriq.kotlin_note_app.db.DbManager
import kotlinx.android.synthetic.main.activity_add_note.*
import java.lang.Exception

class AddNote : AppCompatActivity() {

    private var mId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        try {
            var bundle: Bundle = intent.extras!!
            mId = bundle.getInt("ID", -1)

            if (mId != -1) {
                edtTitle.setText(bundle.getString("Title").toString())
                eftDescription.setText(bundle.getString("Description").toString())
            }
        } catch (e: Exception) {

        }

    }

    fun addItem(view: View) {
        var dbManager = DbManager(this)

        var values = ContentValues()
        values.put("Title", edtTitle.text.toString())
        values.put("Description", eftDescription.text.toString())

        if (mId == -1) {
            // insert
            val id = dbManager.insert(values)

            if (id > 0) {
                Toast.makeText(this, "Note added", Toast.LENGTH_LONG).show()
                finish()
            } else {
                Toast.makeText(this, "Fail to add note", Toast.LENGTH_LONG).show()
            }
        } else {
            // update
            val selectionArguments = arrayOf(mId.toString())
            val id = dbManager.update(values, "ID = ?", selectionArguments)

            if (id > 0) {
                Toast.makeText(this, "Note updated", Toast.LENGTH_LONG).show()
                finish()
            } else {
                Toast.makeText(this, "Fail to update note", Toast.LENGTH_LONG).show()
            }
        }

    }
}