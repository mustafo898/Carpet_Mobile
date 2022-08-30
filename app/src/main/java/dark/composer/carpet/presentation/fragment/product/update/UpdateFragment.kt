package dark.composer.carpet.presentation.fragment.product.update

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStarted
import androidx.loader.content.CursorLoader
import androidx.recyclerview.widget.LinearLayoutManager
import dark.composer.carpet.databinding.FragmentUpdateProductBinding
import dark.composer.carpet.presentation.fragment.BaseFragment
import dark.composer.carpet.presentation.fragment.product.add.ImageAdapter
import dark.composer.carpet.utils.BaseNetworkResult
import dark.composer.carpet.utils.createRequest
import dark.composer.carpet.utils.uploadFile
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class UpdateProductFragment :
    BaseFragment<FragmentUpdateProductBinding>(FragmentUpdateProductBinding::inflate) {
    lateinit var viewModel: UpdateViewModel

    private val adapterImage by lazy {
        ImageAdapter(requireContext())
    }

    private val REQUEST_CODE = 100
    private var factoryId = -1
    private var id = ""
    private var type = ""

    override fun onViewCreate() {
        viewModel = ViewModelProvider(
            this,
            providerFactory
        )[UpdateViewModel::class.java]

        setUpUi()
        collect()
        textSend()
    }

    private fun setUpUi(){
        binding.photoList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.photoList.adapter = adapterImage

        val bundle: Bundle? = this.arguments
        bundle?.let {
            id = it.getString("ID", "")
            type = it.getString("TYPE", "")
        }
    }

    private fun textSend() {
        viewModel.getProduct(id,type)

        binding.add.setOnClickListener {
            checkPermission()
        }

        binding.accept.setOnClickListener {
            viewModel.update(
                binding.amount.text.toString().trim(),
                binding.colour.text.toString().trim(),
                binding.design.text.toString().trim(),
                factoryId,
                binding.height.text.toString().trim(),
                binding.name.text.toString().trim(),
                binding.pon.text.toString().trim(),
                binding.price.text.toString().trim(),
                type,
                binding.width.text.toString().trim(),
                id,
                requireContext()
            )
        }

        binding.nameInput.isHelperTextEnabled = false
        binding.name.addTextChangedListener {
            viewModel.validName(it.toString())
        }

        binding.designInput.isHelperTextEnabled = false
        binding.design.addTextChangedListener {
            viewModel.validDesign(it.toString())
        }

        binding.ponInput.isHelperTextEnabled = false
        binding.pon.addTextChangedListener {
            viewModel.validPon(it.toString())
        }

        binding.widthInput.isHelperTextEnabled = false
        binding.width.addTextChangedListener {
            viewModel.validWidth(it.toString())
        }

        binding.heightInput.isHelperTextEnabled = false
        binding.height.addTextChangedListener {
            viewModel.validHeight(it.toString())
        }

        binding.amountInput.isHelperTextEnabled = false
        binding.amount.addTextChangedListener {
            viewModel.validAmount(it.toString())
        }

        binding.colourInput.isHelperTextEnabled = false
        binding.colour.addTextChangedListener {
            viewModel.validColor(it.toString())
        }

        binding.priceInput.isHelperTextEnabled = false
        binding.price.addTextChangedListener {
            viewModel.validPrice(it.toString())
        }

        binding.requireFactory.visibility = View.GONE
    }

    private fun collect() {
        binding.back.setOnClickListener {
            navController.popBackStack()
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.whenStarted {
                viewModel.uploadImage.collect {
                    when(it){
                        is BaseNetworkResult.Error -> Toast.makeText(requireContext(), "${it.message} file error", Toast.LENGTH_SHORT).show()
                        is BaseNetworkResult.Loading -> Toast.makeText(requireContext(), "${it.isLoading} file load", Toast.LENGTH_SHORT).show()
                        is BaseNetworkResult.Success -> {
                            Toast.makeText(requireContext(), "Success File", Toast.LENGTH_SHORT).show()
                            navController.popBackStack()
                        }
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch{
            viewLifecycleOwner.lifecycle.whenStarted {
                viewModel.updateProduct.collect {
                    when(it){
                        is BaseNetworkResult.Error -> Toast.makeText(requireContext(), "${it.message}", Toast.LENGTH_SHORT).show()
                        is BaseNetworkResult.Loading -> Toast.makeText(requireContext(), "${it.isLoading}", Toast.LENGTH_SHORT).show()
                        is BaseNetworkResult.Success -> {
                            if (list.isNotEmpty()) {
                                list.forEach { list1->
//                                    Toast.makeText(requireContext(), list1, Toast.LENGTH_SHORT).show()
                                    viewModel.uploadFile(createRequest(list1),it.data?.attachUUID!!)
                                }
                            } else {
                                navController.popBackStack()
                            }
                        }
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch{
            viewLifecycleOwner.lifecycle.whenStarted {
                viewModel.product.collect {
                    when(it){
                        is BaseNetworkResult.Error -> Toast.makeText(requireContext(), "${it.message}", Toast.LENGTH_SHORT).show()
                        is BaseNetworkResult.Loading -> Toast.makeText(requireContext(), "${it.isLoading}", Toast.LENGTH_SHORT).show()
                        is BaseNetworkResult.Success -> {
                            it.data?.let {t->
                                binding.name.setText(t.name)
                                binding.design.setText(t.design)
                                binding.colour.setText(t.colour)
                                binding.width.setText(t.weight.toString())
                                binding.height.setText(t.height.toString())
                                binding.pon.setText(t.pon)
                                binding.amount.setText(t.amount.toString())
                                binding.price.setText(t.price.toString())
                                factoryId = t.factory.id
                                type = t.type
                                id = t.uuid
                                if (t.type == "UNCOUNTABLE"){
                                    binding.amount.setText("1")
                                    binding.amountInput.visibility = View.GONE
                                }else{
                                    binding.amountInput.visibility = View.VISIBLE
                                }
                            }
                        }
                    }
                }
            }
        }

        viewModel.factoryIdFlow.observe(viewLifecycleOwner) {
            if (it == "Correct") {
                binding.requireFactory.visibility = View.GONE
            } else {
                binding.requireFactory.visibility = View.VISIBLE
                binding.requireFactory.text = it
            }
        }

        viewModel.nameFlow.observe(viewLifecycleOwner) {
            if (it == "Correct") {
                binding.nameInput.isHelperTextEnabled = false
            } else {
                binding.nameInput.helperText = it
            }
        }

        viewModel.amountFlow.observe(viewLifecycleOwner) {
            if (it == "Correct") {
                binding.amountInput.isHelperTextEnabled = false
            } else {
                binding.amountInput.helperText = it
            }
        }

        viewModel.heightFlow.observe(viewLifecycleOwner) {
            if (it == "Correct") {
                binding.heightInput.isHelperTextEnabled = false
            } else {
                binding.heightInput.helperText = it
            }
        }

        viewModel.widthFlow.observe(viewLifecycleOwner) {
            if (it == "Correct") {
                binding.widthInput.isHelperTextEnabled = false
            } else {
                binding.widthInput.helperText = it
            }
        }

        viewModel.colorFlow.observe(viewLifecycleOwner) {
            if (it == "Correct") {
                binding.colourInput.isHelperTextEnabled = false
            } else {
                binding.colourInput.helperText = it
            }
        }

        viewModel.ponFlow.observe(viewLifecycleOwner) {
            if (it == "Correct") {
                binding.ponInput.isHelperTextEnabled = false
            } else {
                binding.ponInput.helperText = it
            }
        }

        viewModel.designFlow.observe(viewLifecycleOwner) {
            if (it == "Correct") {
                binding.designInput.isHelperTextEnabled = false
            } else {
                binding.designInput.helperText = it
            }
        }

        viewModel.factoryIdFlow.observe(viewLifecycleOwner) {
            if (it == "Correct") {
                binding.requireFactory.visibility = View.GONE
            } else {
                binding.requireFactory.visibility = View.VISIBLE
                binding.requireFactory.text = it
            }
        }

        viewModel.priceFlow.observe(viewLifecycleOwner) {
            if (it == "Correct") {
                binding.priceInput.isHelperTextEnabled = false
            } else {
                binding.priceInput.helperText = it
            }
        }
    }

    private fun createRequest(path:String): MultipartBody.Part {
        val file = File(path)
        val requestBody = RequestBody.create("multipart/form-date".toMediaTypeOrNull(), file)
        return MultipartBody.Part.createFormData("file", file.name, requestBody)
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
                imagePath = uri?.let {
                    uploadFile(it)
                }.toString()
                list.add(imagePath)
//                Toast.makeText(requireContext(), imagePath, Toast.LENGTH_SHORT).show()
                adapterImage.setImage(imagePath)
            }
        }
    }

    private val list = mutableListOf<String>()

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