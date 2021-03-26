package com.joaohhenriq.kotlin_note_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class AddNote : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)
    }

    fun addItem(view: View) {
        finish()
    }
}