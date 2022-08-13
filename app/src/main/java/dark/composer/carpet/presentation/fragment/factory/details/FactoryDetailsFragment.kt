package dark.composer.carpet.presentation.fragment.factory.details

import android.os.Bundle
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStarted
import com.bumptech.glide.Glide
import dark.composer.carpet.R
import dark.composer.carpet.data.remote.models.request.factory.update.FactoryUpdateRequest
import dark.composer.carpet.databinding.FragmentFactoryDatailsNewBinding
import dark.composer.carpet.presentation.fragment.adapters.ProductAdapter
import dark.composer.carpet.presentation.fragment.BaseFragment
import dark.composer.carpet.presentation.fragment.dialog.MenuSettings
import dark.composer.carpet.presentation.fragment.dialog.MenuType
import dark.composer.carpet.utils.BaseNetworkResult
import kotlinx.coroutines.launch
import javax.inject.Inject

class FactoryDetailsFragment :
    BaseFragment<FragmentFactoryDatailsNewBinding>(FragmentFactoryDatailsNewBinding::inflate) {
    @Inject
    lateinit var viewModel: FactoryDetailsViewModel
    private val productAdapter by lazy {
        ProductAdapter()
    }

    private val menuType by lazy {
        MenuType(requireContext(),binding.type)
    }

    private val menuSettings by lazy {
        MenuSettings(requireContext(),binding.more)
    }

    private var d = 0
    private var type = "UNCOUNTABLE"

    override fun onViewCreate() {
        viewModel = ViewModelProvider(
            this,
            providerFactory
        )[FactoryDetailsViewModel::class.java]

        setUpUi()
        observe()
        send()
        action()
    }

    private fun setUpUi() {
        val bundle: Bundle? = this.arguments
        bundle?.let {
            d = it.getInt("ID", 0)
        }
        binding.productList.adapter = productAdapter
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
                viewModel.factory.collect {
                    when (it) {
                        is BaseNetworkResult.Success -> {
                            if (it.data?.photoUrl.isNullOrEmpty()) {
                                binding.image.setImageResource(R.drawable.image)
                            } else {
                                Glide.with(requireContext()).load(it.data?.photoUrl)
                                    .into(binding.image)
                            }
                            binding.name.text = it.data?.name
                            binding.status.text = it.data?.status
                            binding.visible.text = it.data?.visible.toString()
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

    private fun send() {
        viewModel.getFactory(d)
        viewModel.getProductList(type, 0, 20)
    }

    private fun action() {
        binding.back.setOnClickListener {
            navController.popBackStack()
        }

        binding.more.setOnClickListener {
            menuSettings.show()
        }

        binding.type.setOnClickListener {
            menuType.show()
        }

        menuSettings.setDeleteClickListener {
            Toast.makeText(requireContext(), "Delete", Toast.LENGTH_SHORT).show()
            viewModel.deleteFactory(d)
        }

        menuSettings.setEditClickListener {
            navController.navigate(R.id.action_factoryDetailsFragment_to_updateFactoryFragment,
                bundleOf("ID" to d))
            Toast.makeText(requireContext(), "Edit", Toast.LENGTH_SHORT).show()
        }

        menuSettings.setShareClickListener {
            Toast.makeText(requireContext(), "Share", Toast.LENGTH_SHORT).show()
        }

        menuType.setCountableClickListener {
            getPagination("COUNTABLE")
            Toast.makeText(requireContext(), "Countable", Toast.LENGTH_SHORT).show()
        }

        menuType.setUncountableClickListener {
            getPagination("UNCOUNTABLE")
            Toast.makeText(requireContext(), "Uncountable", Toast.LENGTH_SHORT).show()
        }

        productAdapter.setClickListener {
            navController.navigate(
                R.id.action_factoryDetailsFragment_to_productDetailsFragment,
                bundleOf("ID" to it, "TYPE" to type)
            )
        }
    }

    private fun getPagination(type:String){
        this.type = type
        viewModel.getProductList(type, 0, 20)
    }
}