package dark.composer.carpet.presentation.fragment.search

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import dark.composer.carpet.R
import dark.composer.carpet.data.retrofit.models.request.filter.ProductFilterRequest
import dark.composer.carpet.databinding.FragmentSearchBinding
import dark.composer.carpet.presentation.fragment.BaseFragment
import dark.composer.carpet.presentation.fragment.product.ProductAdapter
import javax.inject.Inject

class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {
    @Inject
    lateinit var viewModel: SearchViewModel

    private val productAdapter by lazy {
        ProductAdapter(requireContext())
    }

    override fun onViewCreate() {
        viewModel = ViewModelProvider(
            this,
            providerFactory
        )[SearchViewModel::class.java]

        binding.list.layoutManager =
            GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        binding.list.adapter = productAdapter
        binding.list.showShimmerAdapter()

        viewModel.liveDataListProductPagination.observe(requireActivity()) {
            it?.let {
                binding.list.hideShimmerAdapter()
                productAdapter.setProductListProduct(it)
            }
        }

        var type = "UNCOUNTABLE"
        var name: String? = null
        var color: String? = null
        var design: String? = null
        var factoryName:String? = null
        var pon: String? = null
        var width: String? = null
        var height: String? = null
        val bundle: Bundle? = this.arguments
        bundle?.let {
            type = it.getString("TYPE", "UNCOUNTABLE")
            pon = it.getString("PON")
            factoryName = it.getString("FACTORY_NAME")
            design = it.getString("DESIGN")
            color = it.getString("COLOR")
            width = it.getString("WIDTH")
            height = it.getString("HEIGHT")
            name = it.getString("NAME")
        }

        viewModel.filterProduct(
            ProductFilterRequest(
                if (factoryName.isNullOrEmpty()) null else factoryName,
                if (name.isNullOrEmpty()) null else name,
                if (design.isNullOrEmpty()) null else design,
                if (color.isNullOrEmpty()) null else color,
                if (height.isNullOrEmpty()) null else height?.toDouble(),
                if (width.isNullOrEmpty()) null else width?.toDouble(),
                if (pon.isNullOrEmpty()) null else pon,
                null,
                null,
                type
            )
        )

        binding.filter.setOnClickListener {
            navController.navigate(R.id.action_searchFragment_to_filterProductFragment)
        }
    }
}