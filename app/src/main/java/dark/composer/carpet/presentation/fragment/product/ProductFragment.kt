package dark.composer.carpet.presentation.fragment.product

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.tabs.TabLayoutMediator
import dark.composer.carpet.R
import dark.composer.carpet.databinding.FragmentProductBinding
import dark.composer.carpet.presentation.fragment.BaseFragment
import dark.composer.carpet.presentation.fragment.product.veiwpager_fragments.PagerAdapter
import dark.composer.carpet.presentation.fragment.product.veiwpager_fragments.ProductAdapter
import dark.composer.carpet.utils.SharedPref
import javax.inject.Inject

class ProductFragment : BaseFragment<FragmentProductBinding>(FragmentProductBinding::inflate) {
    private val pagerAdapter by lazy {
        PagerAdapter(this)
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

        binding.viewPager.adapter = pagerAdapter
        TabLayoutMediator(binding.tabLayout,binding.viewPager){tab,pos ->
            when(pos){
                0-> tab.text="COUNTABLE"
                1-> tab.text="UNCOUNTABLE"
            }
        }.attach()

        binding.back.setOnClickListener {
            navController.navigate(R.id.action_productFragment_to_adminFragment)
        }
    }
}