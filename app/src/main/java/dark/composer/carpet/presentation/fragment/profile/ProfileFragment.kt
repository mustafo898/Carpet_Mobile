package dark.composer.carpet.presentation.fragment.profile

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import dark.composer.carpet.R
import dark.composer.carpet.databinding.FragmentProfileBinding
import dark.composer.carpet.presentation.fragment.BaseFragment
import dark.composer.carpet.utils.SharedPref
import javax.inject.Inject


class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {
    private val REQUEST_CODE = 100
    @Inject
    lateinit var sharedPref: SharedPref
    override fun onViewCreate() {
        binding.backBtn.setOnClickListener {
            navController.navigate(R.id.action_profileFragment_to_settingsFragment)
        }

        binding.changeImage.setOnClickListener {
//            val intent = Intent(Intent.ACTION_PICK)
//            intent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
//            startActivityForResult(intent,REQUEST_CODE)
            uploadImage()
        }
        binding.name.setText(sharedPref.getName().toString())
        binding.lastName.setText(sharedPref.getSurName().toString())
    }

    var selectedImageUri: Uri? = null

    private fun uploadImage() {
        Intent(Intent.ACTION_PICK).also {
            it.type = "image/*"
            val mimeTypes = arrayOf("image/jpeg", "image/png")
            it.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
            startActivityForResult(it, REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {

            if (requestCode == REQUEST_CODE) {
                selectedImageUri = data?.data
                binding.image.setImageURI(selectedImageUri)
                Log.d("WWWWW", "onActivityResult: ${selectedImageUri?.path}")
                Toast.makeText(requireContext(), "$selectedImageUri", Toast.LENGTH_SHORT).show()
            }
        }
    }


//    var imagePath = ""
//    private fun getPath(uri: Uri?): String {
//        val projection = arrayOf(MediaStore.MediaColumns.DATA)
//        val cursor: Cursor? = query(uri, null, projection,  null, null)
//        column_index = cursor?.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
//        cursor!!.moveToFirst()
//        imagePath = cursor.getString(column_index)
//        return cursor.getString(column_index)
//    }

//    @Deprecated("Deprecated in Java")
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        Log.d("RRRRRRR", "onActivityResult: ${data!!.data}")
//        val photo = data.extras?.get("data")
//
////        val tempUri = getImageUri(ApplicationProvider.getApplicationContext(), photo as Bitmap)
//
////        val finalFile = File(getRealPathFromURI(tempUri!!))
////        Toast.makeText(requireContext(), "$finalFile", Toast.LENGTH_SHORT).show()
//        if (resultCode == RESULT_OK) {
//            if (requestCode == REQUEST_CODE) {
//                val selectedImageUri: Uri? = data.data
//                Log.d("RRRRR", "onActivityResult: ${data.data?.path}")
//                if (null != selectedImageUri) {
//                    binding.image.setImageURI(selectedImageUri)
//                    Toast.makeText(requireContext(), "$selectedImageUri", Toast.LENGTH_LONG).show()
//                    Log.d("SSSS", "onActivityResult: ${binding.image.resources}")
//                }
//            }
//        }
//    }

//    private fun getImageUri(inContext: Context, inImage: Bitmap?): Uri? {
//        val outImage = Bitmap.createScaledBitmap(inImage!!, 1000, 1000, true)
//        val path = Images.Media.insertImage(inContext.contentResolver, outImage, "Title", null)
//        return Uri.parse(path)
//    }
//
//    private fun getRealPathFromURI(uri: Uri): String {
//        var path = ""
//        Log.d("PATHHH","path: $path")
//
//        if (context?.contentResolver != null) {
//            val cursor: Cursor? = context?.contentResolver?.query(uri, null, null, null, null)
//            if (cursor != null) {
//                cursor.moveToFirst()
//                val idx: Int = cursor.getColumnIndex(Images.ImageColumns.DATA)
//                path = cursor.getString(idx)
//                cursor.close()
//            }
//        }
//        return path
//    }
}