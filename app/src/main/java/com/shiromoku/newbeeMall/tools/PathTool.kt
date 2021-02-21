package com.shiromoku.shiromokulearn.tool

import android.content.Context
import androidx.core.content.ContextCompat.getDataDir
import java.io.File

object PathTool {
        fun getExternalStorageDataAbsolutePath(context: Context?, path: String): String? {
            return context?.getExternalFilesDir(path)?.absolutePath
        }

        fun getInternalStorageDataAbsolutePath(context: Context?): File? {
//        return Environment.getDataDirectory().absolutePath
            return getDataDir(context!!)
        }
}