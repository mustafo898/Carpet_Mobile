package dark.composer.carpet.presentation.fragment.product

import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dark.composer.carpet.R
import dark.composer.carpet.data.retrofit.models.response.factory.PaginationResponse
import dark.composer.carpet.data.retrofit.models.response.product.pagination.ProductPaginationResponse
import dark.composer.carpet.databinding.FragmentProductBinding
import dark.composer.carpet.presentation.fragment.BaseFragment
import dark.composer.carpet.utils.SharedPref
import javax.inject.Inject

class ProductFragment : BaseFragment<FragmentProductBinding>(FragmentProductBinding::inflate) {
    @Inject
    lateinit var shared: SharedPref

    private val productAdapter by lazy {
        ProductAdapter(requireContext())
    }

    @Inject
    lateinit var viewModel: ProductViewModel
    lateinit var list: List<PaginationResponse>
    var page = 0
    var isLast = false
    override fun onViewCreate() {

        viewModel = ViewModelProvider(
            this,
            providerFactory
        )[ProductViewModel::class.java]

        list = ArrayList()

        binding.list.layoutManager =
            GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        binding.list.adapter = productAdapter
        binding.list.showShimmerAdapter()

        viewModel.liveDataListPagination.observe(requireActivity()) {
            it?.let { it1 ->
                binding.list.hideShimmerAdapter()
                productAdapter.setProductListProduct(it1)
            }
        }

        viewModel.liveDataListUncountablePagination.observe(requireActivity()) {
            it?.let { it1 ->
                binding.list.hideShimmerAdapter()
                productAdapter.setProductListProduct(it1)
            }
        }

        productAdapter.setClickListener {
            navController.navigate(
                R.id.action_adminFragment_to_productDetailsFragment,
                bundleOf("ID" to it, "TYPE" to "COUNTABLE")
            )
        }

        binding.list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val lm = recyclerView.layoutManager as GridLayoutManager
                if (!isLast) {
                    if (lm.findLastVisibleItemPosition() == productAdapter.itemCount - 1) {
                        isLast = true
                        page += 1
//                        viewModel.getPagination(page,20,"UNCOUNTABLE")
                    }
                }
            }
        })

        var isClick = false

//        binding.uncountable.setOnClickListener {
//            if (!isClick) {
//                isClick = true
//                binding.uncountable.setBackgroundResource(R.drawable.bg_round)
//                viewModel.getUncountablePagination(page, 20, "UNCOUNTABLE")
//                binding.countable.setBackgroundColor(R.color.white)
//            }else{
//
//            }
//        }
//
//        binding.countable.setOnClickListener {
//            if (!isClick){
//                isClick = true
//                binding.countable.setBackgroundResource(R.drawable.bg_round)
//                viewModel.getCountPagination(page, 20, "COUNTABLE")
//                binding.uncountable.setBackgroundColor(R.color.white)
//            }
//        }

        viewModel.getCountPagination(page, 20, "COUNTABLE")
    }
}