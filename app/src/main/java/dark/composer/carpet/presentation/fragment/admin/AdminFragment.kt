package dark.composer.carpet.presentation.fragment.admin

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStarted
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import dark.composer.carpet.R
import dark.composer.carpet.data.remote.models.request.filter.ProductFilterRequest
import dark.composer.carpet.databinding.FragmentAdminNewBinding
import dark.composer.carpet.presentation.fragment.BaseFragment
import dark.composer.carpet.presentation.fragment.adapters.FactoryAdminAdapter
import dark.composer.carpet.presentation.fragment.adapters.ProductAdapter
import dark.composer.carpet.presentation.fragment.dialog.MenuType
import dark.composer.carpet.utils.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class AdminFragment : BaseFragment<FragmentAdminNewBinding>(FragmentAdminNewBinding::inflate) {
    @Inject
    lateinit var viewModel: AdminViewModel

    @Inject
    lateinit var sharedPref: SharedPref

    private val productAdapter by lazy {
        ProductAdapter()
    }

    private val menuType by lazy {
        MenuType(requireContext(),binding.type)
    }

    private val factoryAdminAdapter by lazy {
        FactoryAdminAdapter()
    }

    private var type = "COUNTABLE"

    override fun onViewCreate() {
        viewModel = ViewModelProvider(
            this,
            providerFactory
        )[AdminViewModel::class.java]
        Toast.makeText(requireContext(), "Admin", Toast.LENGTH_SHORT).show()
        setupUi()
        send()
        observe()
        action()
    }

    private fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.whenStarted {
                viewModel.profile.collect {
                    when (it) {
                        is BaseNetworkResult.Error -> {
                            binding.scroll.visibility = View.VISIBLE
                            binding.progress.visibility = View.GONE
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                        }
                        is BaseNetworkResult.Loading -> {
                            binding.progress.visibility = View.VISIBLE
                            binding.scroll.visibility = View.GONE
                        }
                        is BaseNetworkResult.Success -> {
                            binding.progress.visibility = View.GONE
                            binding.scroll.visibility = View.VISIBLE
                            binding.name.text = it.data?.name ?: "Carpet Mobile"
                            it.data?.let { t ->
                                Glide.with(requireContext()).load(t.url).into(binding.image)
                            }
                        }
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.whenStarted {
                viewModel.product.collect {
                    when (it) {
                        is BaseNetworkResult.Success -> {
                            Log.d("EEEEE", "observe: ${it.data}")
                            binding.progress.visibility = View.GONE
                            binding.scroll.visibility = View.VISIBLE
                            productAdapter.setList(it.data ?: emptyList())
                        }
                        is BaseNetworkResult.Loading -> {
                            binding.progress.visibility = View.VISIBLE
                            binding.scroll.visibility = View.GONE
                        }
                        is BaseNetworkResult.Error -> {
                            binding.progress.visibility = View.GONE
                            binding.scroll.visibility = View.VISIBLE
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.whenStarted {
                viewModel.factory.collect {
                    when (it) {
                        is BaseNetworkResult.Success -> {
                            Log.d("EEEEE", "observe: ${it.data}")
                            binding.progress.visibility = View.GONE
                            binding.scroll.visibility = View.VISIBLE
                            factoryAdminAdapter.setList(it.data ?: emptyList())
                        }
                        is BaseNetworkResult.Loading -> {
                            binding.progress.visibility = View.VISIBLE
                            binding.scroll.visibility = View.GONE
                        }
                        is BaseNetworkResult.Error -> {
                            binding.progress.visibility = View.GONE
                            binding.scroll.visibility = View.VISIBLE
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }

    private fun send() {
        if (!sharedPref.getToken().isNullOrEmpty()) {
            viewModel.getProfile()
            viewModel.getProductList(type, 0, 20)
            viewModel.getFactoryList(0, 20)
        }
    }

    private fun action() {
        binding.type.setOnClickListener {
            menuType.show()
        }

        menuType.setCountableClickListener {
            getPagination("COUNTABLE")
            Toast.makeText(requireContext(), "Countable", Toast.LENGTH_SHORT).show()
        }

        menuType.setUncountableClickListener {
            getPagination("UNCOUNTABLE")
            Toast.makeText(requireContext(), "Uncountable", Toast.LENGTH_SHORT).show()
        }

        binding.seeAll.setOnClickListener {
            navController.navigate(R.id.action_adminFragment_to_factoryFragment)
        }

        factoryAdminAdapter.setClickListener {
            navController.navigate(
                R.id.action_adminFragment_to_factoryDetailsFragment,
                bundleOf("ID" to it)
            )
        }

        productAdapter.setClickListener {
            Log.d("RRRRR", "adapter: $it")
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            navController.navigateA(type, it)
        }

        binding.image.setOnClickListener {
//            sharedPref.clearAllCache()
            if (sharedPref.getToken().isNullOrEmpty()) {
                navController.navigate(R.id.action_adminFragment_to_logInFragment)
            } else {
                navController.navigate(R.id.action_adminFragment_to_profileFragment)
            }

        }
    }

    private fun setupUi() {
        activity?.findViewById<BottomNavigationView>(R.id.bottomNavigation)?.visibility =
            View.VISIBLE
        binding.list.layoutManager =
            GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        binding.list.adapter = productAdapter

        binding.factoriesList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.factoriesList.adapter = factoryAdminAdapter
        val snapper = LinearSnapHelper()
        snapper.attachToRecyclerView(binding.factoriesList)
    }

    private fun getPagination(type:String){
        this.type = type
        viewModel.getProductList(type = type, page = 0, size = 20)
    }
}