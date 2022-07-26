package dark.composer.carpet.presentation.fragment.search

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import dark.composer.carpet.R
import dark.composer.carpet.data.retrofit.models.request.filter.ProductFilterRequest
import dark.composer.carpet.databinding.FragmentSearchBinding
import dark.composer.carpet.presentation.fragment.BaseFragment
import dark.composer.carpet.presentation.fragment.product.ProductViewModel
import dark.composer.carpet.presentation.fragment.product.veiwpager_fragments.ProductAdapter
import okhttp3.internal.assertThreadDoesntHoldLock
import javax.inject.Inject

class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate){
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

        viewModel.liveDataListProductPagination.observe(requireActivity()){
            it?.let {
                binding.list.hideShimmerAdapter()
                productAdapter.setProductListProduct(it)
            }
        }

        var type = "COUNTABLE"
        var name = ""
        var color = ""
        var design = ""
        var factoryName = ""
        var pon = ""
        var width = 0.0
        var height = 0.0
        val bundle: Bundle? = this.arguments
        bundle?.let {
            type = it.getString("TYPE", "")
            pon = it.getString("PON", "")
            factoryName = it.getString("FACTORY_NAME", "")
            design = it.getString("DESIGN", "")
            color = it.getString("COLOR", "")
//            width = it.getString("WIDTH","")
//            height = it.getString("HEIGHT", "")
            name = it.getString("NAME", "")
        }

//        if (type.isEmpty() || factoryName.isEmpty() || pon.isEmpty() || design.isEmpty() || width.isEmpty() || height.isEmpty() || name.isEmpty() || color.isEmpty()){
            viewModel.filterProduct(ProductFilterRequest(factoryName, name, design, color, height, width, pon, "","", type))
//        }

        binding.filter.setOnClickListener {
            navController.navigate(R.id.action_searchFragment_to_filterProductFragment)
        }
    }
}