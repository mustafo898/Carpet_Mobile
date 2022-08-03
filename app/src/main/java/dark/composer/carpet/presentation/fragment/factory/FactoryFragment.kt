package dark.composer.carpet.presentation.fragment.factory

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import dark.composer.carpet.databinding.FragmentFactoryBinding
import dark.composer.carpet.presentation.fragment.BaseFragment
import dark.composer.carpet.presentation.fragment.admin.FactoryAdapter
import javax.inject.Inject

class FactoryFragment : BaseFragment<FragmentFactoryBinding>(FragmentFactoryBinding::inflate){
    @Inject
    lateinit var viewModel: FactoryViewModel

    private val factoryAdapter by lazy {
        FactoryAdapter(requireContext())
    }

    override fun onViewCreate() {
        viewModel = ViewModelProvider(
            this,
            providerFactory
        )[FactoryViewModel::class.java]

        binding.list.layoutManager =
            GridLayoutManager(requireContext(),2, GridLayoutManager.VERTICAL, false)
        binding.list.adapter = factoryAdapter
        binding.list.showShimmerAdapter()

        viewModel.liveDataListPagination.observe(requireActivity()) {
            it?.let { t ->
                binding.list.hideShimmerAdapter()
                factoryAdapter.setListFactory(t.content)
            }
        }

        viewModel.getPagination(0,10)
    }
}