package com.happymesport.merchant.utils

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.core.content.FileProvider
import com.google.firebase.Timestamp
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object Utils {
    fun saveBitmapToCacheAndGetUri(
        context: Context,
        bitmap: Bitmap,
        fileName: String,
    ): Uri? {
        val cachePath = File(context.cacheDir, "hp_img")
        cachePath.mkdirs() // Ensure the directory exists
        val file = File(cachePath, "$fileName.png")
        val outputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        outputStream.flush()
        outputStream.close()

        return FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider", // Must match authority in AndroidManifest
            file,
        )
    }

    fun convertStringToTimestamp(
        dateString: String,
        dateFormat: String,
    ): Timestamp? {
        return try {
            val formatter = SimpleDateFormat(dateFormat, Locale.getDefault())
            val date: Date = formatter.parse(dateString) ?: return null
            Timestamp(date)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
