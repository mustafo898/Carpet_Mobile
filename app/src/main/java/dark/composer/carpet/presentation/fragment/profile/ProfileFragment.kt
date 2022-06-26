package dark.composer.carpet.presentation.fragment.profile

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore.Images
import android.util.Log
import android.widget.Toast
import dark.composer.carpet.R
import dark.composer.carpet.databinding.FragmentProfileBinding
import dark.composer.carpet.presentation.fragment.BaseFragment


class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {
    private val REQUEST_CODE = 200
    override fun onViewCreate() {
        binding.backBtn.setOnClickListener {
            navController.navigate(R.id.action_profileFragment_to_settingsFragment)
        }

        binding.changeImage.setOnClickListener {
//            val intent = Intent(Intent.ACTION_PICK)
//            intent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
//            startActivityForResult(intent,REQUEST_CODE)
            val i = Intent();
            i.type = "image/*";
            i.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(i, "Select Picture"), REQUEST_CODE);
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("RRRRRRR", "onActivityResult: ${data!!.data}")
//        binding.image.setImageURI(data.data)

//        if (resultCode==RESULT_OK){
//            if (requestCode==REQUEST_CODE){
//                binding.image.setImageURI(data.data)
//                Log.d("RRRRRRR", "onActivityResult: ${data.data}")
//            }
//        }
        if (resultCode == RESULT_OK) {
            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == REQUEST_CODE) {
                // Get the url of the image from data
                val selectedImageUri: Uri? = data.data
                Log.d("RRRRR", "onActivityResult: ${data.data?.path}")
                if (null != selectedImageUri) {
                    // update the preview image in the layout
                    binding.image.setImageURI(selectedImageUri)
//                    Toast.makeText(requireContext(), "${selectedImageUri.query}", Toast.LENGTH_LONG).show()
                    Toast.makeText(requireContext(), "$selectedImageUri", Toast.LENGTH_LONG).show()
                    Log.d("SSSS", "onActivityResult: ${binding.image.resources}")
                }
            }
        }
    }
//    fun getImageUri(inContext: Context, inImage: Bitmap): Uri? {
//        val bytes = ByteArrayOutputStream()
//        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
//        val path = Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null)
//        return Uri.parse(path)
//    }
//
//    fun getRealPathFromURI(uri: Uri?): String? {
//        var path = ""
//        if (getContentResolver() != null) {
//            val cursor: Cursor = getContentResolver().query(uri, null, null, null, null)
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