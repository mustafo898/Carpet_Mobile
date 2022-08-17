package dark.composer.carpet.presentation.fragment.profile

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStarted
import com.bumptech.glide.Glide
import dark.composer.carpet.R
import dark.composer.carpet.databinding.FragmentProfileNewBinding
import dark.composer.carpet.presentation.fragment.BaseFragment
import dark.composer.carpet.presentation.fragment.dialog.MenuProfile
import dark.composer.carpet.utils.BaseNetworkResult
import dark.composer.carpet.utils.SharedPref
import dark.composer.carpet.utils.uploadFile
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject

class ProfileFragment :
    BaseFragment<FragmentProfileNewBinding>(FragmentProfileNewBinding::inflate) {
    @Inject
    lateinit var viewModel: ProfileViewModel
    private val REQUEST_CODE = 100

    @Inject
    lateinit var shared: SharedPref

    private val menuSettings by lazy {
        MenuProfile(requireContext(), binding.more)
    }

    override fun onViewCreate() {
        viewModel = ViewModelProvider(
            requireActivity(), providerFactory
        )[ProfileViewModel::class.java]

        observe()
        action()
    }

    private fun action() {
        binding.more.setOnClickListener {
            menuSettings.show()
        }

        viewModel.getProfile()

        menuSettings.setEditClickListener {
            navController.navigate(R.id.action_profileFragment_to_updateProfileFragment)
        }

        menuSettings.setDeleteClickListener {
            viewModel.deleteProfile()
        }

        menuSettings.setChangeClickListener {
            checkPermission()
        }

        menuSettings.setDeleteClickListener {
            viewModel.deleteProfile()
        }

        binding.next.setOnClickListener {
            navController.navigate(R.id.action_profileFragment_to_usersFragment)
        }

        binding.add.setOnClickListener {
            navController.navigate(R.id.action_profileFragment_to_createUserFragment)
        }

        binding.back.setOnClickListener {
            navController.popBackStack()
        }
    }

    private fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.whenStarted {
                viewModel.profile.collect {
                    when (it) {
                        is BaseNetworkResult.Error -> {
                            Toast.makeText(requireContext(), "${it.message}", Toast.LENGTH_SHORT)
                                .show()
                        }
                        is BaseNetworkResult.Loading -> {
                            Toast.makeText(requireContext(), "Loading..", Toast.LENGTH_SHORT).show()
                        }
                        is BaseNetworkResult.Success -> {
                            it.data?.let { t ->
                                Log.d("PROFILEFFFFF", "profile data: ${t.name}")
                                binding.name.text = t.name
                                binding.number.text = "+998 ${t.phoneNumber}"
                                Glide.with(requireContext()).load(t.url).into(binding.image)
                            }
                        }
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.whenStarted {
                viewModel.delete.collect {
                    when (it) {
                        is BaseNetworkResult.Error -> {
                            Toast.makeText(requireContext(), "${it.message}", Toast.LENGTH_SHORT)
                                .show()
                        }
                        is BaseNetworkResult.Loading -> {
                            Toast.makeText(requireContext(), "Loading..", Toast.LENGTH_SHORT).show()
                        }
                        is BaseNetworkResult.Success -> {
                            it.data?.let { t ->
                                Toast.makeText(
                                    requireContext(),
                                    "Success Log Out ${t.name}",
                                    Toast.LENGTH_SHORT
                                ).show()
                                shared.clearAllCache()
                                navController.navigate(R.id.action_profileFragment_to_adminFragment)
                            }
                        }
                    }
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CODE) {
                val uri = data?.data
                val imagePath = uri?.let { uploadFile(it, requireContext()) }.toString()
                Glide.with(requireContext()).load(imagePath).into(binding.image)
                val file = File(imagePath)
                val requestBody =
                    RequestBody.create("multipart/form-date".toMediaTypeOrNull(), file)
                val body = MultipartBody.Part.createFormData("file", file.name, requestBody)
                viewModel.fileUploadProfile(body)
            }
        }
    }
}
