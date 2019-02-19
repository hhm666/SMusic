package com.xxmicloxx.noteBlockAPI

import java.io.*
import java.util.*

object NBSDecoder {
    fun parse(decodeFile: File, title: String): Song {
        return parse(FileInputStream(decodeFile), decodeFile, title)
    }

    private fun parse(inputStream: InputStream, decodeFile: File, title2: String): Song {
        val layerHashMap = HashMap<Int, Layer>()
        val dis = DataInputStream(inputStream)
        val length = readShort(dis)
        val songHeight = readShort(dis)
        var title = readString(dis)
        if (title == "") {
            title = title2
        }
        val author = readString(dis)
        readString(dis)
        val description = readString(dis)
        val speed = readShort(dis) / 100f
        dis.readBoolean()
        dis.readByte()
        dis.readByte()
        readInt(dis)
        readInt(dis)
        readInt(dis)
        readInt(dis)
        readInt(dis)
        readString(dis)
        var tick: Int = -1
        while (true) {
            val jumpTicks = readShort(dis)
            if (jumpTicks.toInt() == 0) {
                break
            }
            tick += jumpTicks
            var layer: Int = -1
            while (true) {
                val jumpLayers = readShort(dis)
                if (jumpLayers.toInt() == 0) {
                    break
                }
                layer += jumpLayers
                setNote(layer, tick, dis.readByte().toInt(), dis.readByte(), layerHashMap)
            }
        }
        return Song(speed, layerHashMap, songHeight, length, title, author, description, decodeFile)
    }

    private fun setNote(layer: Int, ticks: Int, instrument: Int, key: Byte, layerHashMap: HashMap<Int, Layer>) {
        var l: Layer? = layerHashMap[layer]
        if (l == null) {
            l = Layer()
            layerHashMap[layer] = l
        }
        l.setNote(ticks, Note(instrument, key))
    }

    @Throws(IOException::class)
    private fun readShort(dis: DataInputStream): Short {
        val byte1 = dis.readUnsignedByte()
        val byte2 = dis.readUnsignedByte()
        return (byte1 + (byte2 shl 8)).toShort()
    }

    @Throws(IOException::class)
    private fun readInt(dis: DataInputStream): Int {
        val byte1 = dis.readUnsignedByte()
        val byte2 = dis.readUnsignedByte()
        val byte3 = dis.readUnsignedByte()
        val byte4 = dis.readUnsignedByte()
        return byte1 + (byte2 shl 8) + (byte3 shl 16) + (byte4 shl 24)
    }

    @Throws(IOException::class)
    private fun readString(dis: DataInputStream): String {
        var length = readInt(dis)
        val sb = StringBuilder(length)
        while (length > 0) {
            var c = dis.readByte().toChar()
            if (c == 0x0D.toChar()) {
                c = ' '
            }
            sb.append(c)
            --length
        }
        return sb.toString()
    }
}
