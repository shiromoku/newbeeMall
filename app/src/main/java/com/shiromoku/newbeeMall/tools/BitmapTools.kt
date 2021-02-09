package com.shiromoku.newbeeMall.tools

import android.graphics.*


class BitmapTools {
    /**
     * 感谢qq_33306480大大的图片转换代码
     * code from https://blog.csdn.net/qq_33306480/article/details/86302494
     */
    companion object {
        fun getRoundBitmapByShader(
            bitmap: Bitmap?,
            outWidth: Int,
            outHeight: Int,
            radius: Int,
            boarder: Int
        ): Bitmap? {
            if (bitmap == null) {
                return null
            }
            val width = bitmap.width
            val height = bitmap.height
            val widthScale = outWidth * 1f / width
            val heightScale = outHeight * 1f / height
            val matrix = Matrix()
            matrix.setScale(widthScale, heightScale)
            //创建输出的bitmap
            val desBitmap = Bitmap.createBitmap(outWidth, outHeight, Bitmap.Config.ARGB_8888)
            //创建canvas并传入desBitmap，这样绘制的内容都会在desBitmap上
            val canvas = Canvas(desBitmap)
            val paint = Paint(Paint.ANTI_ALIAS_FLAG)
            //创建着色器
            val bitmapShader = BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
            //给着色器配置matrix
            bitmapShader.setLocalMatrix(matrix)
            paint.setShader(bitmapShader)
            //创建矩形区域并且预留出border
            val rect = RectF(
                boarder.toFloat(), boarder.toFloat(),
                (outWidth - boarder).toFloat(), (outHeight - boarder).toFloat()
            )
            //把传入的bitmap绘制到圆角矩形区域内
            canvas.drawRoundRect(rect, radius.toFloat(), radius.toFloat(), paint)
            if (boarder > 0) {
                //绘制boarder
                val boarderPaint = Paint(Paint.ANTI_ALIAS_FLAG)
                boarderPaint.setColor(Color.GREEN)
                boarderPaint.setStyle(Paint.Style.STROKE)
                boarderPaint.setStrokeWidth(boarder.toFloat())
                canvas.drawRoundRect(rect, radius.toFloat(), radius.toFloat(), boarderPaint)
            }
            return desBitmap
        }
    }
}