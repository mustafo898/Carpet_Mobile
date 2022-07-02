package dark.composer.carpet.presentation.fragment.product

import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dark.composer.carpet.R
import dark.composer.carpet.data.retrofit.models.response.factory.FactoryResponse
import dark.composer.carpet.databinding.FragmentProductBinding
import dark.composer.carpet.presentation.fragment.BaseFragment
import dark.composer.carpet.utils.SharedPref
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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

        binding.list.layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        binding.list.adapter = productAdapter
        binding.list.showShimmerAdapter()

        binding.list.postDelayed({getData()},5000)

        var f = 1
        binding.list.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val lm = recyclerView.layoutManager as GridLayoutManager
                if(!d){
                    if (lm.findLastVisibleItemPosition() == productAdapter.itemCount-1){
                        d = true
                        f+=1
                        Toast.makeText(requireContext(), "Oxiri $f", Toast.LENGTH_SHORT).show()
                        productAdapter.setListFactory(moreData())
                    }
                }
            }
        })

        binding.back.setOnClickListener {
            navController.navigate(R.id.action_productFragment_to_adminFragment)
        }
    }

    fun moreData():ArrayList<FactoryResponse>{
        val r = productAdapter.itemCount
        val list = ArrayList<FactoryResponse>()
        for (i in r until r+10){
            list.add(FactoryResponse("date $i",i,"key_$i","Name",null,"Active",true))
        }
        d = false
        return list
    }

    var d = false
    private fun getData(){
        val list = ArrayList<FactoryResponse>()
        for (i in 0 until 14){
            list.add(FactoryResponse("12",i,"key_$i","Name",null,"Active",true))
        }
        productAdapter.setListFactory(list)
        binding.list.hideShimmerAdapter()
    }
}