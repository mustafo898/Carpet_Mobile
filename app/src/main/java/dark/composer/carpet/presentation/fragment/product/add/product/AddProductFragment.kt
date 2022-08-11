package dark.composer.carpet.presentation.fragment.product.add.product

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.loader.content.CursorLoader
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dark.composer.carpet.databinding.FragmentAddProductBinding
import dark.composer.carpet.presentation.fragment.BaseFragment
import dark.composer.carpet.presentation.fragment.product.add.AddImageAdapter
import dark.composer.carpet.presentation.fragment.product.add.FactorySelectAdapter
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


class AddProductFragment :
    BaseFragment<FragmentAddProductBinding>(FragmentAddProductBinding::inflate) {
    lateinit var viewModel: AddProductViewModel
    private val adapter by lazy {
        FactorySelectAdapter(requireContext())
    }

    private val adapterImage by lazy {
        AddImageAdapter(requireContext())
    }

    private val REQUEST_CODE = 100
    var t = ""
    private var factoryId = -1

    override fun onViewCreate() {
        viewModel = ViewModelProvider(
            this,
            providerFactory
        )[AddProductViewModel::class.java]

        binding.factoryList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.factoryList.itemAnimator = DefaultItemAnimator()
        binding.factoryList.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                LinearLayoutManager.HORIZONTAL
            )
        )
        binding.factoryList.adapter = adapter

        binding.photoList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.photoList.adapter = adapterImage

//        collect()
//        textSend()
//
//        adapter.setClickListener {it,name->
//            viewModel.validFactoryId(it)
//            factoryId = it
//        }
    }

//    private fun textSend() {
//        viewModel.getPagination(0, 10)
//
//        binding.add.setOnClickListener {
//            checkPermission()
//        }
//
//        binding.accept.setOnClickListener {
//            viewModel.createProduct(
//                binding.amount.text.toString().trim(),
//                binding.colour.text.toString().trim(),
//                binding.design.text.toString().trim(),
//                factoryId,
//                binding.height.text.toString().trim(),
//                binding.name.text.toString().trim(),
//                binding.pon.text.toString().trim(),
//                binding.price.text.toString().trim(),
//                t,
//                binding.width.text.toString().trim(),
//                requireContext()
//            )
//        }
//
//        binding.nameInput.isHelperTextEnabled = false
//        binding.name.addTextChangedListener {
//            viewModel.validName(it.toString())
//        }
//
//        binding.designInput.isHelperTextEnabled = false
//        binding.design.addTextChangedListener {
//            viewModel.validDesign(it.toString())
//        }
//
//        binding.ponInput.isHelperTextEnabled = false
//        binding.pon.addTextChangedListener {
//            viewModel.validPon(it.toString())
//        }
//
//        binding.widthInput.isHelperTextEnabled = false
//        binding.width.addTextChangedListener {
//            viewModel.validWidth(it.toString())
//        }
//
//        binding.heightInput.isHelperTextEnabled = false
//        binding.height.addTextChangedListener {
//            viewModel.validHeight(it.toString())
//        }
//
//        binding.amountInput.isHelperTextEnabled = false
//        binding.amount.addTextChangedListener {
//            viewModel.validAmount(it.toString())
//        }
//
//        binding.colourInput.isHelperTextEnabled = false
//        binding.colour.addTextChangedListener {
//            viewModel.validColor(it.toString())
//        }
//
//        binding.priceInput.isHelperTextEnabled = false
//        binding.price.addTextChangedListener {
//            viewModel.validPrice(it.toString())
//        }
//
//        binding.requireFactory.visibility = View.GONE
//
//        binding.requireType.visibility = View.GONE
//
//        binding.countable.setOnClickListener {
//            viewModel.validType(binding.countable.text.toString().uppercase())
//        }
//
//        binding.unCountable.setOnClickListener {
//            viewModel.validType(binding.unCountable.text.toString().uppercase())
//        }
//    }
//
//    private fun collect() {
//        viewLifecycleOwner.lifecycleScope.launch {
//            viewLifecycleOwner.lifecycle.whenStarted {
//                viewModel.errorFlow.collect {
//                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
//
//        viewLifecycleOwner.lifecycleScope.launch {
//            viewLifecycleOwner.lifecycle.whenStarted {
//                viewModel.successChangeFlow.collect {
//                    Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show()
//                    navController.navigate(R.id.action_addProductFragment_to_profileFragment)
//                }
//            }
//        }
//
//        viewModel.createProductLiveData.observe(requireActivity()) {
//            if (list.isNotEmpty()) {
//                list.forEach { list1->
//                    Toast.makeText(requireContext(), list1, Toast.LENGTH_SHORT).show()
//                    viewModel.uploadFile(createRequest(list1),it.attachUUID)
//                }
//            } else {
//                navController.navigate(R.id.action_addProductFragment_to_profileFragment)
//            }
//        }
//
//        viewModel.factoryIdFlow.observe(viewLifecycleOwner) {
//            if (it == "Correct") {
//                binding.requireFactory.visibility = View.GONE
//            } else {
//                binding.requireFactory.visibility = View.VISIBLE
//                binding.requireFactory.text = it
//            }
//        }
//
//        viewModel.liveDataListPagination.observe(viewLifecycleOwner) {
//            it?.let { it1 ->
//                adapter.setListFactory(it1.content)
//            }
//        }
//
//        viewModel.nameFlow.observe(viewLifecycleOwner) {
//            if (it == "Correct") {
//                binding.nameInput.isHelperTextEnabled = false
//            } else {
//                binding.nameInput.helperText = it
//            }
//        }
//
//        viewModel.amountFlow.observe(viewLifecycleOwner) {
//            if (it == "Correct") {
//                binding.amountInput.isHelperTextEnabled = false
//            } else {
//                binding.amountInput.helperText = it
//            }
//        }
//
//        viewModel.heightFlow.observe(viewLifecycleOwner) {
//            if (it == "Correct") {
//                binding.heightInput.isHelperTextEnabled = false
//            } else {
//                binding.heightInput.helperText = it
//            }
//        }
//
//        viewModel.widthFlow.observe(viewLifecycleOwner) {
//            if (it == "Correct") {
//                binding.widthInput.isHelperTextEnabled = false
//            } else {
//                binding.widthInput.helperText = it
//            }
//        }
//
//        viewModel.colorFlow.observe(viewLifecycleOwner) {
//            if (it == "Correct") {
//                binding.colourInput.isHelperTextEnabled = false
//            } else {
//                binding.colourInput.helperText = it
//            }
//        }
//
//        viewModel.ponFlow.observe(viewLifecycleOwner) {
//            if (it == "Correct") {
//                binding.ponInput.isHelperTextEnabled = false
//            } else {
//                binding.ponInput.helperText = it
//            }
//        }
//
//        viewModel.designFlow.observe(viewLifecycleOwner) {
//            if (it == "Correct") {
//                binding.designInput.isHelperTextEnabled = false
//            } else {
//                binding.designInput.helperText = it
//            }
//        }
//
//        viewModel.factoryIdFlow.observe(viewLifecycleOwner) {
//            if (it == "Correct") {
//                binding.requireFactory.visibility = View.GONE
//            } else {
//                binding.requireFactory.visibility = View.VISIBLE
//                binding.requireFactory.text = it
//            }
//        }
//
//        viewModel.priceFlow.observe(viewLifecycleOwner) {
//            if (it == "Correct") {
//                binding.priceInput.isHelperTextEnabled = false
//            } else {
//                binding.priceInput.helperText = it
//            }
//        }
//
//        viewModel.typeFlow.observe(viewLifecycleOwner) {
//            if (it == binding.unCountable.text.toString().uppercase() || it == binding.countable.text.toString().uppercase()) {
//                t = it
//                Toast.makeText(requireContext(), "True", Toast.LENGTH_SHORT).show()
//                binding.requireType.visibility = View.GONE
//            } else {
//                Toast.makeText(requireContext(), "False", Toast.LENGTH_SHORT).show()
//                binding.requireType.visibility = View.VISIBLE
//                binding.requireType.text = it
//            }
//        }
//    }

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
                Toast.makeText(requireContext(), imagePath, Toast.LENGTH_SHORT).show()
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