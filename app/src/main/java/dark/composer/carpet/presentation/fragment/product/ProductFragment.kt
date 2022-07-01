package dark.composer.carpet.presentation.fragment.product

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import dark.composer.carpet.R
import dark.composer.carpet.data.retrofit.models.response.factory.FactoryResponse
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
    @Inject
    lateinit var viewModel: ProductViewModel

    override fun onViewCreate() {

        viewModel = ViewModelProvider(
            this,
            providerFactory
        )[ProductViewModel::class.java]

        binding.list.layoutManager =
            GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        binding.list.adapter = productAdapter
        productAdapter.setListFactory(getData())

        binding.back.setOnClickListener {
            navController.navigate(R.id.action_productFragment_to_adminFragment)
        }
    }

    private fun getData():ArrayList<FactoryResponse>{
        val list = ArrayList<FactoryResponse>()
        for (i in 0 until 14){
            list.add(FactoryResponse("12",i,"key_$i","Name",null,"Active",true))
        }
        return list
    }
}