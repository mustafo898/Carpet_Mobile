package dark.composer.carpet.presentation.fragment.profile.add.factory

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
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
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import dark.composer.carpet.R
import dark.composer.carpet.data.retrofit.models.request.factory.FactoryAddRequest
import dark.composer.carpet.databinding.FragmentAddFactoryBinding
import dark.composer.carpet.presentation.fragment.BaseFragment
import dark.composer.carpet.utils.SharedPref
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject

class AddFactoryFragment :
    BaseFragment<FragmentAddFactoryBinding>(FragmentAddFactoryBinding::inflate) {
    lateinit var viewModel: AddFactoryViewModel
    private val REQUEST_CODE = 100

    @Inject
    lateinit var sharedPref: SharedPref

    override fun onViewCreate() {
        viewModel = ViewModelProvider(
            this,
            providerFactory
        )[AddFactoryViewModel::class.java]

        collect()
        textSend()

    }

    private fun textSend() {
        binding.factoryInput.isHelperTextEnabled = false
        binding.factory.addTextChangedListener {
            viewModel.validName(it.toString())
        }

        binding.accept.setOnClickListener {
            val t = binding.factory.toString().trim()
            print(t)
            viewModel.addFactory(binding.factory.text.toString().trim(), requireContext())
        }

        binding.add.setOnClickListener {
            checkPermission()
        }
    }

    private fun collect() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.whenStarted {
                viewModel.successFlow.collect {
                    findNavController().navigate(R.id.action_addFactoryFragment_to_profileFragment)
                }
            }
        }

        viewModel.liveDataAddFactory.observe(viewLifecycleOwner) {
            if (imagePath.isNotEmpty()){
                it?.let { t ->
                    viewModel.uploadFile(body, t.key)
                    print(body)
                }
            }else{
                findNavController().navigate(R.id.action_addFactoryFragment_to_profileFragment)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.whenStarted {
                viewModel.errorFlow.collect {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewModel.nameFlow.observe(viewLifecycleOwner) {
            if (it == "Correct") {
                binding.factoryInput.isHelperTextEnabled = false
            } else {
                binding.factoryInput.helperText = it
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
                imagePath = uri?.let { uploadFile(it) }.toString()
                Toast.makeText(requireContext(), imagePath, Toast.LENGTH_SHORT).show()
                Glide.with(requireContext()).load(imagePath).into(binding.image)
                val file = File(imagePath)
                val requestBody =
                    RequestBody.create("multipart/form-date".toMediaTypeOrNull(), file)
                body = MultipartBody.Part.createFormData("file", file.name, requestBody)
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