package dark.composer.carpet.presentation.fragment.factory_detail

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
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
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import dark.composer.carpet.R
import dark.composer.carpet.data.retrofit.models.request.factory.update.FactoryUpdateRequest
import dark.composer.carpet.data.retrofit.models.request.product.ProductCreateRequest
import dark.composer.carpet.databinding.FragmentFactoryDetailsBinding
import dark.composer.carpet.presentation.fragment.BaseFragment
import dark.composer.carpet.presentation.fragment.admin.FactoryAdapter
import dark.composer.carpet.utils.SharedPref
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject

class FactoryDetailsFragment : BaseFragment<FragmentFactoryDetailsBinding>(FragmentFactoryDetailsBinding::inflate) {
    private lateinit var viewModel: FactoryDetailsViewModel
    private val REQUEST_CODE = 100

    private val factoryAdapter by lazy {
        FactoryAdapter(requireContext())
    }

    @Inject
    lateinit var sharedPref: SharedPref
    var key = ""
    override fun onViewCreate() {
        viewModel = ViewModelProvider(
            this,
            providerFactory
        )[FactoryDetailsViewModel::class.java]

        var id = 0
        val bundle: Bundle? = this.arguments
        bundle?.let {
            id = it.getInt("ID")
        }

        binding.list.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.list.adapter = factoryAdapter
        binding.list.showShimmerAdapter()

        viewModel.liveDataListPagination.observe(requireActivity()) {
            it?.let { t ->
                binding.list.hideShimmerAdapter()
                factoryAdapter.setListFactory(t.content)
            }
        }

        var visible = false

        viewModel.liveDataInfoFactory.observe(requireActivity()) {
            if (it != null) {
                binding.date.text = "${it.createdDate.substring(0, 10)} ${it.createdDate.substring(11, 16)}"
                binding.status.text = it.status
                binding.name.text = it.name
                key = it.key
                visible = it.visible
                Glide.with(requireContext()).load(it.photoUrl).into(binding.image)
            } else {
                binding.date.text = "..."
                binding.status.text = "..."
                binding.name.text = "..."
            }
        }

        binding.more.setOnClickListener {
            val popup = PopupMenu(requireContext(), binding.more)

            //Inflating the Popup using xml file
            popup.menuInflater.inflate(R.menu.pop_up_menu, popup.menu)
            popup.menu.findItem(R.id.editName).title = "Edit Product"
            popup.menu.findItem(R.id.logout).isVisible = false

            //registering popup with OnMenuItemClickListener
            popup.setOnMenuItemClickListener(object : MenuItem.OnMenuItemClickListener,
                PopupMenu.OnMenuItemClickListener {
                override fun onMenuItemClick(item: MenuItem): Boolean {
                    when (item.itemId) {
                        R.id.editName -> {
                            viewModel.updateInfoFactory(FactoryUpdateRequest(id, "Name", "ACTIVE", true), id)
                        }
                        R.id.delete_menu->{

                        }
                        R.id.changeImage_menu->{
                            checkPermission()
                        }
                        R.id.share -> {

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

        viewModel.getPagination(0, 10)

        viewModel.getInfoFactory(id)
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
                viewLifecycleOwner.lifecycleScope.launch {
                    viewLifecycleOwner.lifecycle.whenStarted {
                        viewModel.successFlow.catch {
                            Log.d("EEEEEE", "onActivityResult: $this")
                        }.collect {
                            Glide.with(requireContext()).load(it.url).into(binding.image)
                        }
                    }
                }
                val file = File(imagePath)
                val requestBody =
                    RequestBody.create("multipart/form-date".toMediaTypeOrNull(), file)
                val body = MultipartBody.Part.createFormData("file", file.name, requestBody)
                viewModel.uploadFile(body, key)
            }
        }
    }

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

    //        binding.update.setOnClickListener {
//            val dialog = AlertDialog.Builder(requireContext())
//
//            dialog.setTitle("Update Factory")
//            dialog.setMessage("Name")
//            dialog.setPositiveButton("Ok") { _, _ ->
//            }
//            dialog.setNegativeButton("No") { dialog, _ ->
//                dialog.cancel()
//                dialog.dismiss()
//            }
//            dialog.show()
//        }

//        binding.changeImage.setOnClickListener {
//            checkPermission()
//        }

//        binding.delete.setOnClickListener {
//            val dialog = AlertDialog.Builder(requireContext())
//
//            dialog.setTitle("Update Factory")
//            if (visible) {
//                dialog.setMessage("${!visible}")
//            } else {
//                dialog.setMessage("$visible")
//            }
//            dialog.setPositiveButton("Ok") { dialog, d ->
//                viewModel.updateInfoFactory(FactoryUpdateRequest(id, "Name", "ACTIVE", false), id)
//            }
//            dialog.setNegativeButton("No") { dialog, d ->
//                dialog.cancel()
//                dialog.dismiss()
//            }
//            dialog.show()
//        }
}