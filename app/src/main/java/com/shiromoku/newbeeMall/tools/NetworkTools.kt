package com.shiromoku.newbeeMall.tools

import android.content.Context
import java.net.HttpURLConnection
import java.net.URL

object NetworkTools {
    fun downloadFile(url: String, path: String, content: Context?): Int {
        var code = 0
        try {
            lateinit var data: ByteArray
            val fileName = url.substring(url.lastIndexOf("/") + 1)
            val connection = URL(url).openConnection() as HttpURLConnection
            code = connection.responseCode
            if (code == 200) {
                data = connection.inputStream.readBytes()
                FileTools.saveFile(content, path, fileName, data)
            }
        } catch (e: Exception) {

        } finally {

            return code
        }
    }
}