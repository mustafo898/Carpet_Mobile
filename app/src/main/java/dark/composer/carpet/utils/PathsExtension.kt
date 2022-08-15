package dark.composer.carpet.utils

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import androidx.core.os.bundleOf
import androidx.loader.content.CursorLoader
import androidx.navigation.NavController
import dark.composer.carpet.R
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

fun uploadFile(uri: Uri,context: Context): String? {
    val projection = arrayOf(MediaStore.Images.Media.DATA)
    val loader = CursorLoader(context, uri, projection, null, null, null)
    val cursor = loader.loadInBackground()
    val columnIdx = cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
    cursor?.moveToFirst()
    val result = cursor?.getString(columnIdx!!)
    cursor?.close()
    return result
}

fun createRequest(path:String): MultipartBody.Part {
    val file = File(path)
    val requestBody = RequestBody.create("multipart/form-date".toMediaTypeOrNull(), file)
    return MultipartBody.Part.createFormData("file", file.name, requestBody)
}

fun NavController.navigateP(type:String,id: String){
    if (type == "COUNTABLE") {
        navigate(
            R.id.action_productFragment_to_productDetailsFragment,
            bundleOf("ID" to id, "TYPE" to type)
        )
    }else if (type == "UNCOUNTABLE"){
        navigate(
            R.id.action_productFragment_to_productUncountableFragment,
            bundleOf("ID" to id, "TYPE" to type)
        )
    }
}

fun NavController.navigateA(type:String,id: String){
    if (type == "COUNTABLE") {
        navigate(
            R.id.action_adminFragment_to_productDetailsFragment,
            bundleOf("ID" to id, "TYPE" to type)
        )
    }else if (type == "UNCOUNTABLE"){
        navigate(
            R.id.action_adminFragment_to_productUncountableFragment,
            bundleOf("ID" to id, "TYPE" to type)
        )
    }
}