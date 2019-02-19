package com.xxmicloxx.noteBlockAPI

import java.util.*

class Layer {
    private val hashMap = HashMap<Int, Note>()

    fun getNote(tick: Int): Note? {
        return hashMap[tick]
    }

    fun setNote(tick: Int, note: Note) {
        hashMap[tick] = note
    }
}
