package dark.composer.carpet.presentation.fragment.product.veiwpager_fragments.deatils

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStarted
import androidx.loader.content.CursorLoader
import com.bumptech.glide.Glide
import dark.composer.carpet.R
import dark.composer.carpet.data.retrofit.models.request.product.ProductCreateRequest
import dark.composer.carpet.databinding.FragmentProductDetailsBinding
import dark.composer.carpet.presentation.fragment.BaseFragment
import dark.composer.carpet.presentation.fragment.product.veiwpager_fragments.uncountable.UncountableViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class ProductDetailsFragment : BaseFragment<FragmentProductDetailsBinding>(FragmentProductDetailsBinding::inflate) {
    lateinit var viewModel: ProductDetailsViewModel
    private val REQUEST_CODE = 100

    override fun onViewCreate() {
        viewModel = ViewModelProvider(
            this,
            providerFactory
        )[ProductDetailsViewModel::class.java]

        var id  = ""
        var type = ""
        val bundle: Bundle? = this.arguments
        bundle?.let {
            id = it.getString("ID","")
            type = it.getString("TYPE","")
        }

        viewModel.productLiveData.observe(requireActivity()){
            it?.let {t->
                binding.date.text = "${t.createDate.substring(11,16)}  ${t.createDate.substring(0,10)}"
                binding.design.text = t.design
                binding.name.text = t.name
                binding.factoryName.text = t.factory.name
                binding.createDate.text = "${t.factory.createdDate.substring(11,16)}  ${t.factory.createdDate.substring(0,10)}"
                binding.pon.text = t.pon
                binding.visible.text = t.uuid
                binding.size.text = "${t.weight} x ${t.height}"
            }
        }

        viewModel.productDetails(id,type)

        binding.update.setOnClickListener {
            viewModel.updateProduct(id,type, ProductCreateRequest(1,"White","Mex",5,9,"Vinicius", "152",5.6,"COUNTABLE",6))
        }

        binding.delete.setOnClickListener {
            viewModel.deleteProduct(id,type)
        }

        binding.changeImage.setOnClickListener {
            Intent(Intent.ACTION_PICK).also {
                it.type = "image/*"
                startActivityForResult(it, REQUEST_CODE)
            }
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
                        viewModel.successChangeFlow.catch {
                            Log.d("EEEEEE", "onActivityResult: $this")
                        }.collect {
                            Glide.with(requireContext()).load(it.urlImageList[0]).into(binding.image)
                        }
                    }
                }
                val file = File(imagePath)
                val requestBody = RequestBody.create("multipart/form-date".toMediaTypeOrNull(), file)
                val body = MultipartBody.Part.createFormData("file", file.name, requestBody)
//                viewModel.uploadFile(body,)
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