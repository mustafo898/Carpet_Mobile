package dark.composer.carpet.presentation.fragment.profile

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.view.MenuItem
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
import dark.composer.carpet.data.retrofit.models.request.factory.FactoryAddRequest
import dark.composer.carpet.data.retrofit.models.request.profile.ProfileRequest
import dark.composer.carpet.data.retrofit.models.request.profile.create_customer.ProfileCreateRequest
import dark.composer.carpet.databinding.FragmentProfileBinding
import dark.composer.carpet.presentation.dialog.AddDialog
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

    var name = ""
    var lastname = ""

    override fun onViewCreate() {
        viewModel = ViewModelProvider(this, providerFactory)[ProfileViewModel::class.java]

        observe()

        binding.changeImage.setOnClickListener {
            checkPermission()
        }

        viewModel.getProfile()

        binding.more.setOnClickListener {
            val popup = PopupMenu(requireContext(), binding.more)
            popup.menuInflater.inflate(R.menu.pop_up_menu, popup.menu)
            val d = popup.menu.findItem(R.id.delete_menu)
            d.isVisible = false
            popup.setOnMenuItemClickListener(object : MenuItem.OnMenuItemClickListener,
                PopupMenu.OnMenuItemClickListener {
                override fun onMenuItemClick(item: MenuItem): Boolean {
                    when (item.itemId) {
                        R.id.editName -> {
                            val dialog = UpdateProfileDialog(requireContext(), name, lastname)
                            dialog.setPhoneVisible(false)
                            viewModel.liveDataProfile.observe(requireActivity()) {
                                dialog.dismiss()
                            }
                            dialog.setOnAddListener { name, lastname, password, phone ,role->
                                viewModel.updateProfile(ProfileRequest(password, name, lastname))
                            }
                            dialog.show()
                        }
                        R.id.logout -> {
                            Toast.makeText(
                                requireContext(),
                                "You Clicked : " + item.title,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        R.id.share -> {

                        }
                    }
                    return true
                }
            })
            popup.show()
        }

        binding.listCustomers.setOnClickListener {
            navController.navigate(R.id.action_profileFragment_to_customersListFragment)
        }

        binding.addProduct.setOnClickListener {
            navController.navigate(R.id.action_profileFragment_to_addProductFragment)
        }

        binding.addFactory.setOnClickListener {
            navController.navigate(R.id.action_profileFragment_to_addFactoryFragment)
        }

        binding.addCustomer.setOnClickListener {
            val dialog = UpdateProfileDialog(requireContext(), "", "")
            dialog.setPhoneVisible(true)
            dialog.setOnAddListener { name1, lastname1, password, phone, role ->
                viewModel.createProfile(
                    ProfileCreateRequest(
                        name1,
                        password,
                        phone,
                        role,
                        lastname1
                    )
                )
                dialog.dismiss()
            }
            dialog.show()
        }
    }

    private fun observe() {
        viewModel.liveDataProfile.observe(viewLifecycleOwner) {
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
