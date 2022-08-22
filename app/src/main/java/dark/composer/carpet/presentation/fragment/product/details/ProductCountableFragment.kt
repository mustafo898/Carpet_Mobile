package dark.composer.carpet.presentation.fragment.product.details

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStarted
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import dark.composer.carpet.R
import dark.composer.carpet.data.remote.models.request.basket.BasketCreateRequest
import dark.composer.carpet.databinding.FragmentCountProductDetailsBinding
import dark.composer.carpet.presentation.fragment.BaseFragment
import dark.composer.carpet.presentation.fragment.adapters.ProductAdapter
import dark.composer.carpet.presentation.fragment.dialog.MenuSettings
import dark.composer.carpet.utils.BaseNetworkResult
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductCountableFragment : BaseFragment<FragmentCountProductDetailsBinding>(FragmentCountProductDetailsBinding::inflate) {
    @Inject
    lateinit var viewModel: ProductDetailsViewModel

    private val productAdapter by lazy {
        ProductAdapter()
    }

    private val menuSettings by lazy {
        MenuSettings(requireContext(),binding.more)
    }

    var id = ""
    var type = ""
    var page = 0
    var size = 40

    override fun onViewCreate() {
        viewModel = ViewModelProvider(
            requireActivity(), providerFactory
        )[ProductDetailsViewModel::class.java]

        setUpUi()
        observe()
        action()
        send()
    }

    private fun setUpUi(){
        val bundle: Bundle? = this.arguments
        bundle?.let {
            id = it.getString("ID", "")
            type = it.getString("TYPE", "")
        }

        binding.productList.adapter = productAdapter
    }

    private fun observe(){
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.whenStarted {
                viewModel.productList.collect{
                    when(it){
                        is BaseNetworkResult.Success -> {productAdapter.setList(it.data?: emptyList())}
                        is BaseNetworkResult.Error -> { Toast.makeText(requireContext(), it.message.toString(), Toast.LENGTH_SHORT).show()}
                        is BaseNetworkResult.Loading -> {}
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.whenStarted {
                viewModel.product.collect{
                    when(it){
                        is BaseNetworkResult.Success -> {
                            it.data?.let {t->
                                binding.name.text = t.name
                                binding.pon.text = t.pon
                                binding.design.text = t.design
                                binding.color.text = t.colour
                                binding.amount.text = t.amount.toString()
                                binding.price.text = t.price.toString()
                                binding.visible.text = t.visible.toString()
                                binding.size.text = t.weight.toString() + "x" +t.height.toString()
                                binding.date.text = "${t.createDate.substring(11,15)} ${t.createDate.substring(0,10)}"
                                imageSlider(t.urlImageList?: emptyList(),t.name)
                            }
                        }
                        is BaseNetworkResult.Error -> { Toast.makeText(requireContext(), it.message?:"An unexpected error occurred", Toast.LENGTH_SHORT).show()}
                        is BaseNetworkResult.Loading -> {}
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.whenStarted {
                viewModel.create.collect{
                    when(it){
                        is BaseNetworkResult.Success -> {
                            it.data?.let {t->
                                Log.d("EEEE", "observe: $t")
                                Toast.makeText(requireContext(), t.createdDate, Toast.LENGTH_SHORT).show()
                            }
                        }
                        is BaseNetworkResult.Error -> { Toast.makeText(requireContext(), it.message?:"An unexpected error occurred", Toast.LENGTH_SHORT).show()}
                        is BaseNetworkResult.Loading -> {Toast.makeText(requireContext(), "Loading..", Toast.LENGTH_SHORT).show()}
                    }
                }
            }
        }
    }

    private fun send(){
        viewModel.getProduct(id, type)
        viewModel.getProductList(type,0,20)
    }

    private fun imageSlider(list: List<String>, name: String) {
        val imageList = ArrayList<SlideModel>()
        val position = ArrayList<Int>()
        list.forEachIndexed { i, images ->
            Log.d("RRRRRRRR", "imageSlider: $images")
            position.add(i)
            imageList.add(SlideModel(images, name))
        }
        imageList.size
        binding.imageSlider.setImageList(imageList, ScaleTypes.FIT)
    }

    private fun action(){
        binding.more.setOnClickListener {
            menuSettings.show()
        }

        binding.add.setOnClickListener {
            viewModel.createBasket(BasketCreateRequest(amount = binding.amountBasket.text.toString().toInt(), type = type, productId = id, info = "Basket"))
        }

        menuSettings.setDeleteClickListener {
            viewModel.deleteProduct(type, id)
            Toast.makeText(requireContext(), "Delete", Toast.LENGTH_SHORT).show()
        }

        menuSettings.setEditClickListener {
            navController.navigate(R.id.action_productDetailsFragment_to_updateProductFragment, bundleOf("ID" to id, "TYPE" to type))
            Toast.makeText(requireContext(), type+id, Toast.LENGTH_SHORT).show()
            Log.d("RRRRR", "action: $id")
        }

        menuSettings.setShareClickListener {
            Toast.makeText(requireContext(), "Share", Toast.LENGTH_SHORT).show()
        }

        productAdapter.setClickListener {
            id = it
            navController.navigate(R.id.productDetailsFragment, bundleOf("ID" to id, "TYPE" to type))
        }

        binding.back.setOnClickListener {
            navController.popBackStack()
        }
    }
}