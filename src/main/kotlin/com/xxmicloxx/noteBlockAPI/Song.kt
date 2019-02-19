package com.xxmicloxx.noteBlockAPI

import java.io.File
import java.util.*

class Song(val speed: Float, val layerHashMap: HashMap<Int, Layer>, val songHeight: Short, val length: Short, val title: String, val author: String, val description: String, val path: File) {
    val delay: Float = 20 / speed
}