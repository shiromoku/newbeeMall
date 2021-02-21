package com.shiromoku.shiromokulearn.tool

import java.security.MessageDigest

object EncryptTool {
    fun md5(string: String): String {
        val messageDigest = MessageDigest.getInstance("MD5")
        var cache = messageDigest.digest(string.toByteArray())

        var sb: StringBuffer = StringBuffer()
        for (b in cache) {
            //获取低八位有效值
            var i: Int = b.toInt() and 0xff
            //将整数转化为16进制
            var hexString = Integer.toHexString(i)
            if (hexString.length < 2) {
                //如果是一位的话，补0
                hexString = "0" + hexString
            }
            sb.append(hexString)
        }
        return sb.toString()
    }
}