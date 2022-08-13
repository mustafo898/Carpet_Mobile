package dark.composer.carpet.presentation.fragment.factory.update

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStarted
import androidx.loader.content.CursorLoader
import com.bumptech.glide.Glide
import dark.composer.carpet.data.remote.models.request.factory.update.FactoryUpdateRequest
import dark.composer.carpet.databinding.FragmentAddFactoryNewBinding
import dark.composer.carpet.databinding.FragmentUpdateFactoryNewBinding
import dark.composer.carpet.presentation.fragment.BaseFragment
import dark.composer.carpet.utils.BaseNetworkResult
import dark.composer.carpet.utils.SharedPref
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject

class UpdateFactoryFragment :
    BaseFragment<FragmentUpdateFactoryNewBinding>(FragmentUpdateFactoryNewBinding::inflate) {
    lateinit var viewModel: UpdateFactoryViewModel
    private val REQUEST_CODE = 100

    @Inject
    lateinit var sharedPref: SharedPref
    var d = 0
    override fun onViewCreate() {
        viewModel = ViewModelProvider(
            this,
            providerFactory
        )[UpdateFactoryViewModel::class.java]

        setUpUi()
        observe()
        send()
        binding.add.setOnClickListener {
            checkPermission()
        }
    }

    private fun setUpUi() {
        val bundle: Bundle? = this.arguments
        bundle?.let {
            d = it.getInt("ID", 0)
        }
    }

    private fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.whenStarted {
                viewModel.name.collect {
                    Log.d("UUUUU", "observe: $it")
                    it.forEach { t ->
                        if (!t.value) {
                            binding.factoryInput.isHelperTextEnabled = true
                            binding.factoryInput.helperText = it.keys.toString()
                        } else {
                            binding.factoryInput.isHelperTextEnabled = false
                        }
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.whenStarted {
                viewModel.factory.collect {
                    when (it) {
                        is BaseNetworkResult.Success -> {
                            if (!it.data?.photoUrl.isNullOrEmpty()) {
                                Glide.with(requireContext()).load(it.data?.photoUrl)
                                    .into(binding.image)
                            }
                            binding.visible.isChecked = it.data?.visible!!
                            binding.status.isChecked = it.data.status == "ACTIVE"
                            binding.factory.setText(it.data.name)
                            Log.d("EEEEE", "observe: ${it.data}")
                        }
                        is BaseNetworkResult.Loading -> {}
                        is BaseNetworkResult.Error -> {
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.whenStarted {
                viewModel.update.collect {
                    when (it) {
                        is BaseNetworkResult.Success -> {
                            Toast.makeText(requireContext(), "${it.data?.key}", Toast.LENGTH_SHORT)
                                .show()
                            if (imagePath.isNotEmpty()) {
                                viewModel.uploadFile(body, it.data?.key!!)
                            }
                            Log.d("EEEEE", "observe: ${it.data}")
                        }
                        is BaseNetworkResult.Loading -> {}
                        is BaseNetworkResult.Error -> {
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.whenStarted {
                viewModel.fileUpload.collect {
                    when (it) {
                        is BaseNetworkResult.Success -> {
                            navController
                        }
                        is BaseNetworkResult.Loading -> {}
                        is BaseNetworkResult.Error -> {
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }

    private fun send() {
        binding.factory.addTextChangedListener {
            viewModel.validName(it.toString())
        }

        binding.accept.setOnClickListener {
            val status = if (binding.status.isChecked){
                "ACTIVE"
            }else{
                "BLOCKED"
            }
            Toast.makeText(requireContext(), "${binding.visible.isChecked}", Toast.LENGTH_SHORT).show()
            Toast.makeText(requireContext(), status, Toast.LENGTH_SHORT).show()
            viewModel.updateFactory(
                FactoryUpdateRequest(
                    binding.factory.text.toString().trim(),
                    status,
                    binding.visible.isChecked
                ), d
            )
        }
        viewModel.getFactory(d)
        binding.add.setOnClickListener {
            Toast.makeText(requireContext(), "Click", Toast.LENGTH_SHORT).show()
            checkPermission()
        }
    }

    private fun clickStatus(): String {
        return if (binding.status.isChecked) {
            Toast.makeText(requireContext(), "ACTIVE", Toast.LENGTH_SHORT).show()
            "ACTIVE"
        } else {
            Toast.makeText(requireContext(), "BLOCKED", Toast.LENGTH_SHORT).show()
            "BLOCKED"
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
                imagePath = uri?.let { uploadFile(it) }.toString()
                Glide.with(requireContext()).load(imagePath).into(binding.image)
                val file = File(imagePath)
                val requestBody =
                    RequestBody.create("multipart/form-date".toMediaTypeOrNull(), file)
                body = MultipartBody.Part.createFormData("file", file.name, requestBody)
                Toast.makeText(requireContext(), "$body", Toast.LENGTH_SHORT).show()
            }
        }
    }

    lateinit var body: MultipartBody.Part

    var imagePath = ""

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