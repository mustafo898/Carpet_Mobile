package dark.composer.carpet.presentation.fragment.product

import android.util.Log
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStarted
import dark.composer.carpet.R
import dark.composer.carpet.data.remote.models.request.filter.ProductFilterRequest
import dark.composer.carpet.databinding.FragmentProductNewBinding
import dark.composer.carpet.presentation.fragment.BaseFragment
import dark.composer.carpet.presentation.fragment.adapters.FilterAdapter
import dark.composer.carpet.presentation.fragment.adapters.ProductAdapter
import dark.composer.carpet.utils.BaseNetworkResult
import dark.composer.carpet.utils.navigateP
import dark.composer.carpet.utils.navigateType
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductFragment :
    BaseFragment<FragmentProductNewBinding>(FragmentProductNewBinding::inflate) {
    @Inject
    lateinit var viewModel: ProductViewModel

    private val productAdapter by lazy {
        ProductAdapter()
    }

    private val filterAdapter by lazy {
        FilterAdapter()
    }

    override fun onViewCreate() {
        viewModel = ViewModelProvider(
            requireActivity(), providerFactory
        )[ProductViewModel::class.java]

        setUpUi()
        observe()
        send()
        actions()
    }

    private fun setUpUi() {
        binding.productList.adapter = productAdapter
        binding.filterList.adapter = filterAdapter
    }

    private fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.whenStarted {
                viewModel.productList.collect {
                    when (it) {
                        is BaseNetworkResult.Success -> {
                            productAdapter.setList(it.data ?: emptyList())
                        }
                        is BaseNetworkResult.Error -> {
                            Toast.makeText(
                                requireContext(),
                                it.message ?: "An unexpected error occurred",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        is BaseNetworkResult.Loading -> {}
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.whenStarted {
                viewModel.filter.collect {
                    when (it) {
                        is BaseNetworkResult.Success -> {
                            productAdapter.setList(it.data ?: emptyList())
                        }
                        is BaseNetworkResult.Error -> {
                            Toast.makeText(
                                requireContext(),
                                it.message ?: "An unexpected error occurred",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        is BaseNetworkResult.Loading -> {}
                    }
                }
            }
        }
    }

    var type = "COUNTABLE"

    private fun send() {
        binding.search.addTextChangedListener {
            viewModel.filterProduct(ProductFilterRequest(name = it.toString(), type = type))
        }
        viewModel.filterProduct(ProductFilterRequest(type = type))
    }

    private fun actions() {
        binding.addProduct.setOnClickListener {
            navController.navigate(R.id.action_productFragment_to_addProductFragment)
        }

        productAdapter.setClickListener {
            navController.navigateP(type,it)
        }
    }
}