package dark.composer.carpet.presentation.fragment.product.veiwpager_fragments.deatils

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.loader.content.CursorLoader
import dark.composer.carpet.R
import dark.composer.carpet.data.retrofit.models.request.product.ProductCreateRequest
import dark.composer.carpet.databinding.FragmentProductDetailsBinding
import dark.composer.carpet.presentation.fragment.BaseFragment
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class ProductDetailsFragment :
    BaseFragment<FragmentProductDetailsBinding>(FragmentProductDetailsBinding::inflate) {
    lateinit var viewModel: ProductDetailsViewModel
    private val REQUEST_CODE = 100
    var attachId = ""

    override fun onViewCreate() {
        viewModel = ViewModelProvider(
            this,
            providerFactory
        )[ProductDetailsViewModel::class.java]

        var id = ""
        var type = ""
        val bundle: Bundle? = this.arguments
        bundle?.let {
            id = it.getString("ID", "")
            type = it.getString("TYPE", "")
        }

        viewModel.productLiveData.observe(requireActivity()) {
            it?.let { t ->
                binding.name.text = "${t.createDate.substring(11, 16)}  ${t.createDate.substring(0, 10)}"
                binding.design.text = t.design
                binding.name.text = t.name
                binding.factoryName.text = t.factory.name
                binding.createDate.text = "${t.factory.createdDate.substring(11, 16)}  ${t.factory.createdDate.substring(0, 10)}"
                binding.pon.text = t.amount.toString()
                attachId = t.attachUUID
                Log.d("QQQQQQ", "onViewCreate: $attachId")
                binding.visible.text = t.colour
                binding.size.text = "${t.weight} x ${t.height}"
            }
        }

        viewModel.productDetails(id, type)

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
                            viewModel.updateProduct(
                                id,
                                type,
                                ProductCreateRequest(1, "White", "Mex", 5, 9.0, "Vinicius", "152", 5.6, "COUNTABLE", 6.0))
                        }
                        R.id.delete_menu->{
                            viewModel.deleteProduct(id, type)
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
        if (ContextCompat.checkSelfPermission(activity!!.applicationContext, permission[0]) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(activity!!.applicationContext, permission[1]) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(activity!!.applicationContext, permission[2]) == PackageManager.PERMISSION_GRANTED
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
                Toast.makeText(requireContext(), imagePath, Toast.LENGTH_SHORT).show()

                val file = File(imagePath)
                val requestBody =
                    RequestBody.create("multipart/form-date".toMediaTypeOrNull(), file)
                val body = MultipartBody.Part.createFormData("file", file.name, requestBody)
//                viewModel.uploadFile(body, attachId)
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
}