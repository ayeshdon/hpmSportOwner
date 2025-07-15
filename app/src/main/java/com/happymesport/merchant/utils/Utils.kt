package com.happymesport.merchant.utils

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream

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
}
