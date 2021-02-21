package com.shiromoku.newbeeMall.tools;

import android.content.Context
import com.shiromoku.shiromokulearn.tool.PathTool
import java.io.FileInputStream
import java.io.FileOutputStream

object FileTools {
    fun saveFile(context: Context?, path: String, fileName: String, fileData: ByteArray):Int {
        return try {
            val realPath = PathTool.getExternalStorageDataAbsolutePath(context, path)
            val fout = FileOutputStream("$realPath/$fileName")
            fout.write(fileData)
            1
        } catch (e:Exception){
            e.printStackTrace()
            -1
        }
    }

    fun readFile(context: Context?, path: String):ByteArray? {
        return  try{
            val realPath = PathTool.getExternalStorageDataAbsolutePath(context, path)
            val fin = FileInputStream("$realPath")
            fin.readBytes()
        }catch (e:java.lang.Exception){
            e.printStackTrace()
            null
        }
    }
}
