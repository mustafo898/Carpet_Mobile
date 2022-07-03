package dark.composer.carpet.presentation.fragment.product.veiwpager_fragments.uncountable

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dark.composer.carpet.R
import dark.composer.carpet.databinding.FragmentUncountableBinding
import dark.composer.carpet.presentation.fragment.BaseFragment
import dark.composer.carpet.presentation.fragment.product.ProductViewModel
import dark.composer.carpet.presentation.fragment.product.veiwpager_fragments.ProductAdapter

class UncountableFragment : BaseFragment<FragmentUncountableBinding>(FragmentUncountableBinding::inflate){
    private val productAdapter by lazy {
        ProductAdapter(requireContext())
    }

    lateinit var viewModel: UncountableViewModel

    var page = 0
    var isLast = false

    override fun onViewCreate() {
        viewModel = ViewModelProvider(
            this,
            providerFactory
        )[UncountableViewModel::class.java]


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
        viewModel.getPagination(page,20,"UNCOUNTABLE")
    }
}