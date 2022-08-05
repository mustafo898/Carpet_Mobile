package dark.composer.carpet.presentation.fragment.product

import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dark.composer.carpet.R
import dark.composer.carpet.data.retrofit.models.response.factory.PaginationResponse
import dark.composer.carpet.data.retrofit.models.response.product.pagination.ProductPaginationResponse
import dark.composer.carpet.databinding.FragmentProductBinding
import dark.composer.carpet.presentation.dialog.ProgressDialog
import dark.composer.carpet.presentation.fragment.BaseFragment
import dark.composer.carpet.utils.SharedPref
import javax.inject.Inject

class ProductFragment : BaseFragment<FragmentProductBinding>(FragmentProductBinding::inflate) {
    @Inject
    lateinit var shared: SharedPref

    private val productAdapter by lazy {
        ProductAdapter(requireContext())
    }

    private val loadingDialog by lazy {
        ProgressDialog(requireContext())
    }

    @Inject
    lateinit var viewModel: ProductViewModel
    lateinit var list: List<PaginationResponse>
    var page = 0
    var isLast = false
    var type = "COUNTABLE"
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
                loadingDialog.dismiss()
                binding.list.hideShimmerAdapter()
                productAdapter.setProductListProduct(it1)
            }
        }

        productAdapter.setClickListener {
            navController.navigate(
                R.id.action_adminFragment_to_productDetailsFragment,
                bundleOf("ID" to it, "TYPE" to type)
            )
        }

        binding.list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val lm = recyclerView.layoutManager as GridLayoutManager
                if (!isLast) {
                    if (lm.findLastVisibleItemPosition() == productAdapter.itemCount - 1 && productAdapter.itemCount >= 5) {
                        isLast = true
                        page += 1
                        if (binding.countable.isActivated) {
                            viewModel.getCountPagination(page, 20, "COUNTABLE", "")
                        }
                        if (binding.uncountable.isActivated) {
                            viewModel.getCountPagination(page, 20, "UNCOUNTABLE", "")
                        }
                    }
                }
            }
        })

        binding.countable.isChecked = true

        binding.countable.setOnClickListener {
            isClick("COUNTABLE")
        }

        binding.uncountable.setOnClickListener {
//            viewModel.getCountPagination(page, 20, "UNCOUNTABLE", "")
            isClick("UNCOUNTABLE")

//            type = "UNCOUNTABLE"
        }
        loadingDialog.show()
        viewModel.getCountPagination(page, 20, "COUNTABLE", "")
    }

    private fun isClick(s:String){
        viewModel.getCountPagination(page, 20, s, "")
        productAdapter.clear()
        page = 0
        loadingDialog.show()
        type = s
    }
}