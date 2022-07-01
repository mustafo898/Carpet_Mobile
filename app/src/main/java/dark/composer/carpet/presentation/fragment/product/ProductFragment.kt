package dark.composer.carpet.presentation.fragment.product

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import dark.composer.carpet.databinding.FragmentProductBinding
import dark.composer.carpet.presentation.fragment.BaseFragment
import dark.composer.carpet.utils.SharedPref
import javax.inject.Inject

class ProductFragment : BaseFragment<FragmentProductBinding>(FragmentProductBinding::inflate) {
    private val productAdapter by lazy {
        ProductAdapter(requireContext())
    }

    @Inject
    lateinit var shared: SharedPref
    lateinit var viewModel: ProductViewModel

    override fun onViewCreate() {

        viewModel = ViewModelProvider(
            this,
            providerFactory
        )[ProductViewModel::class.java]

        binding.list.layoutManager =
            GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        binding.list.adapter = productAdapter





    }
}