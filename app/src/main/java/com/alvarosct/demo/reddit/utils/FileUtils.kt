package com.alvarosct.demo.reddit.utils

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.provider.MediaStore
import java.io.IOException


class FileUtils(
    private val context: Context
) {

    fun saveBitmapToFile(bitmap: Bitmap, filename: String): Boolean {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, filename)
        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis())
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")

        try {
            val filepath =
                context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
            filepath?.let { uri ->
                val outputStream = context.contentResolver.openOutputStream(uri)
                bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream)
                outputStream?.close()
                return true
            }
            return false
        } catch (e: IOException) {
            e.printStackTrace()
            return false
        }

    }
}