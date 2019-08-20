package org.codepantheon.takenotes.model

import java.io.Serializable

data class NoteInfo(var id: Long = INVALID_ID,
                    var title: String = DEFAULT_TITLE,
                    var content: String = "",
                    var modifiedDate: String = "") : Serializable {

    init {
        title = if (title == "") DEFAULT_TITLE else title
    }

    fun getSummary() : String {
        return content
    }

    fun isEmpty(): Boolean {
        return (title.isEmpty() || title == DEFAULT_TITLE) && content.isEmpty()
    }

    fun isNewNote(): Boolean {
        return id == INVALID_ID
    }

    companion object  {
        const val INVALID_ID: Long = -1
        const val DEFAULT_TITLE: String = "New Note"
    }
}
