package dark.composer.carpet.presentation.fragment.product.deatils

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStarted
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import dark.composer.carpet.R
import dark.composer.carpet.data.retrofit.models.request.basket.BasketCreateRequest
import dark.composer.carpet.databinding.FragmentProductDetailsBinding
import dark.composer.carpet.presentation.fragment.BaseFragment
import dark.composer.carpet.presentation.fragment.basket.BasketViewModel
import dark.composer.carpet.presentation.fragment.basket.dialog.BottomSheetDialog1
import dark.composer.carpet.presentation.fragment.product.ProductAdapter
import dark.composer.carpet.presentation.fragment.product.ProductDetailsAdapter
import dark.composer.carpet.presentation.fragment.product.ProductFragment
import dark.composer.carpet.presentation.fragment.product.ProductViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ProductDetailsFragment :
    BaseFragment<FragmentProductDetailsBinding>(FragmentProductDetailsBinding::inflate) {
    lateinit var viewModel: ProductViewModel
    lateinit var basketViewModel: BasketViewModel
    private val REQUEST_CODE = 100
    var attachId = ""

    private val productAdapter by lazy {
        ProductAdapter(requireContext())
    }

    private val bottomDialog by lazy {
        BottomSheetDialog1(requireContext())
    }

    override fun onViewCreate() {
        viewModel = ViewModelProvider(
            this,
            providerFactory
        )[ProductViewModel::class.java]

        basketViewModel = ViewModelProvider(
            this,
            providerFactory
        )[BasketViewModel::class.java]

        binding.list.layoutManager =
            GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        binding.list.adapter = productAdapter
        binding.list.showShimmerAdapter()

        var id = ""
        var type = ""
        val bundle: Bundle? = this.arguments
        bundle?.let {
            id = it.getString("ID", "")
            type = it.getString("TYPE", "")
        }
        Toast.makeText(requireContext(), type, Toast.LENGTH_SHORT).show()

        viewModel.liveDataListPagination.observe(requireActivity()) {
            it?.let { it1 ->
                binding.list.hideShimmerAdapter()
                productAdapter.setProductListProduct(it1)
            }
        }

        viewModel.productLiveData.observe(requireActivity()) {
            it?.let { t ->
                binding.name.text =
                    "${t.createDate.substring(11, 16)}  ${t.createDate.substring(0, 10)}"
                binding.design.text = t.design
                binding.name.text = t.name
                binding.factoryName.text = t.factory.name
                binding.createDate.text = "${
                    t.factory.createdDate.substring(
                        11,
                        16
                    )
                }  ${t.factory.createdDate.substring(0, 10)}"
                binding.pon.text = t.amount.toString()
                attachId = t.uuid
                Log.d("QQQQQQ", "onViewCreate: $attachId")
                binding.visible.text = t.colour
//                Toast.makeText(requireContext(), t.uuid, Toast.LENGTH_SHORT).show()
                binding.size.text = "${t.weight} x ${t.height}"
            }
        }

//        Toast.makeText(requireContext(), attachId, Toast.LENGTH_SHORT).show()

        val page = 0

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.whenStarted {
                viewModel.errorFlow.collect {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }

            }
        }

        viewModel.basketFlow.observe(viewLifecycleOwner) {
            bottomDialog.dismiss()
            Toast.makeText(requireContext(), "Success Add to Basket", Toast.LENGTH_SHORT)
                .show()
        }

        binding.addBasket.setOnClickListener {
            bottomDialog.setOnAddListener { amount, info ->
                viewModel.createBasket(BasketCreateRequest(amount, info, attachId, type))
            }
            bottomDialog.show()
        }

        productAdapter.setClickListener {
            id = it
            viewModel.productDetails(id, type)
        }

        viewModel.productDetails(id, type)
        viewModel.getCountPagination(page, 20, type,attachId)

        binding.more.setOnClickListener {
            val popup = PopupMenu(requireContext(), binding.more)
            popup.menuInflater.inflate(R.menu.main_menu, popup.menu)

            //registering popup with OnMenuItemClickListener
            popup.setOnMenuItemClickListener(object : MenuItem.OnMenuItemClickListener,
                PopupMenu.OnMenuItemClickListener {
                override fun onMenuItemClick(item: MenuItem): Boolean {
                    when (item.itemId) {
//                        R.id.delete_menu -> {
//                            viewModel.deleteProduct(id, type)
//                        }
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
}