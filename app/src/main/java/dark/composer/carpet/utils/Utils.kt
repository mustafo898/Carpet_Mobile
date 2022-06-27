package dark.composer.carpet.utils

import android.content.ContentResolver
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.provider.OpenableColumns
import androidx.customview.widget.Openable

fun ContentResolver.getFileName(uri: Uri):String{
    var name = ""
    val cursor = query(uri,null,null,null,null)
    cursor?.use {
        it.moveToFirst()
        name = cursor.getString(it.getColumnIndex((OpenableColumns.DISPLAY_NAME)))
    }
    return name
}

