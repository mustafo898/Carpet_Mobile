package dark.composer.carpet.presentation.fragment.profile

import android.Manifest
import android.app.Activity.RESULT_OK
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.view.MenuItem
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStarted
import androidx.loader.content.CursorLoader
import com.bumptech.glide.Glide
import dark.composer.carpet.R
import dark.composer.carpet.data.retrofit.models.request.profile.ProfileRequest
import dark.composer.carpet.databinding.FragmentProfileBinding
import dark.composer.carpet.presentation.dialog.CustomDialog
import dark.composer.carpet.presentation.dialog.UpdateProfileDialog
import dark.composer.carpet.presentation.fragment.BaseFragment
import dark.composer.carpet.utils.SharedPref
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject

class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {
    private val REQUEST_CODE = 100
    lateinit var viewModel: ProfileViewModel

    @Inject
    lateinit var sharedPref: SharedPref

    override fun onViewCreate() {
        viewModel = ViewModelProvider(this, providerFactory)[ProfileViewModel::class.java]

        observe()

//        binding.backBtn.setOnClickListener {
//            navController.navigate(R.id.action_profileFragment_to_settingsFragment)
//        }

        binding.changeImage.setOnClickListener {
            checkPermission()
        }
        
        viewModel.getProfile()

        
        binding.more.setOnClickListener {
            val popup = PopupMenu(requireContext(), binding.more)

            //Inflating the Popup using xml file
            popup.menuInflater.inflate(R.menu.pop_up_menu, popup.menu)
            var d=popup.menu.findItem(R.id.delete_menu)
            d.isVisible = false
            //registering popup with OnMenuItemClickListener
            popup.setOnMenuItemClickListener(object : MenuItem.OnMenuItemClickListener,
                PopupMenu.OnMenuItemClickListener {
                override fun onMenuItemClick(item: MenuItem): Boolean {

                    when (item.itemId) {
                        R.id.editName -> {
                            val dialog =
                                UpdateProfileDialog(requireContext(), "Password", name, lastname)
                            dialog.setOnAddListener { name, lastname, password ->
                                viewModel.updateProfile(ProfileRequest(password, name, lastname))
                            }
                            CustomDialog(requireContext()).show()
                        }
                        R.id.logout -> {
                            Toast.makeText(
                                requireContext(),
                                "You Clicked : " + item.title,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        R.id.share ->{

                        }
                        else -> {
                            return false
                        }
                    }
                    return true
                }
            })

            popup.show() //showing popup menu

        }
    }

    var name = ""
    var lastname = ""
    private fun observe() {
        viewModel.liveDataProfile.observe(requireActivity()) {
            it?.let { t ->
                name = t.name
                lastname = t.surname
                binding.userFull.text = "${t.name} ${t.surname}"
                Glide.with(requireContext()).load(t.url).into(binding.image)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.whenStarted {
                viewModel.errorFlow.collect {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        checkPermission()
    }

    private fun checkPermission() {
        val permission = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

        if (ContextCompat.checkSelfPermission(
                activity!!.applicationContext,
                permission[0]
            ) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(
                activity!!.applicationContext,
                permission[1]
            ) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(
                activity!!.applicationContext,
                permission[2]
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            Log.d("SSSSS", "checkPermission: Otdi")
            Intent(Intent.ACTION_PICK).also {
                it.type = "image/*"
                startActivityForResult(it, REQUEST_CODE)
            }
        } else {
            ActivityCompat.requestPermissions(requireActivity(), permission, 1)
        }
    }

//    fun customDialog(type: String) {
//        val dialog = Dialog(requireContext())
//        dialog.setContentView(R.layout.dialog_update_profile)
//
//        dialog.window?.setBackgroundDrawable(ColorDrawable(0))
//
//        dialog.window?.setWindowAnimations(R.style.AnimationForDialog)
//        val name: EditText = dialog.findViewById(R.id.name_dialog)
//        val cancel: ImageButton = dialog.findViewById(R.id.cancelFB)
//        val accept: ImageButton = dialog.findViewById(R.id.acceptFB)
//        dialog.setCancelable(false)
//        cancel.setOnClickListener {
//            Toast.makeText(requireContext(), "cancel", Toast.LENGTH_SHORT).show()
//            //            dialog.cancel()
//            dialog.dismiss()
//        }
//
//        accept.setOnClickListener {
//            Toast.makeText(requireContext(), "data is changed", Toast.LENGTH_SHORT).show()
//        }
//
//        dialog.show()
//    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {

            if (requestCode == REQUEST_CODE) {
                val uri = data?.data
                imagePath = uri?.let { uploadFile(it) }.toString()
                Toast.makeText(requireContext(), imagePath, Toast.LENGTH_SHORT).show()
                viewLifecycleOwner.lifecycleScope.launch {
                    viewLifecycleOwner.lifecycle.whenStarted {
                        viewModel.successFlow.catch {
                            Log.d("EEEEEE", "onActivityResult: $this")
                        }.collect {
                            sharedPref.setImage(it.url)
                            Glide.with(requireContext()).load(sharedPref.getImage())
                                .into(binding.image)
                        }
                    }
                }
                val file = File(imagePath)
                val requestBody =
                    RequestBody.create("multipart/form-date".toMediaTypeOrNull(), file)
                val body = MultipartBody.Part.createFormData("file", file.name, requestBody)
                viewModel.changed(body)
            }
        }
    }

    private var imagePath = ""

    private fun uploadFile(uri: Uri): String? {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val loader = CursorLoader(requireContext(), uri, projection, null, null, null)
        val cursor = loader.loadInBackground()
        val columnIdx = cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor?.moveToFirst()
        val result = cursor?.getString(columnIdx!!)
        cursor?.close()
        return result
    }
}
