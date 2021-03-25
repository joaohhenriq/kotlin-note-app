package com.joaohhenriq.kotlin_note_app.model

class NoteModel {
    var id: Int
    var name: String
    var description: String

    constructor(id: Int,
                name: String,
                description: String) {
        this.id = id
        this.name = name
        this.description = description
    }
}