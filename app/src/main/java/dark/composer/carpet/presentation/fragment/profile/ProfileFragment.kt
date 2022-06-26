package dark.composer.carpet.presentation.fragment.profile

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.provider.MediaStore
import dark.composer.carpet.R
import dark.composer.carpet.databinding.FragmentProfileBinding
import dark.composer.carpet.presentation.fragment.BaseFragment

class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {
    private val REQUEST_CODE = 1000
    override fun onViewCreate() {
        binding.backBtn.setOnClickListener {
            navController.navigate(R.id.action_profileFragment_to_settingsFragment)
        }

        binding.changeImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            startActivityForResult(intent,REQUEST_CODE)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode==RESULT_OK){
            if (requestCode==REQUEST_CODE){
                binding.image.setImageURI(data!!.data)
            }
        }
    }

}