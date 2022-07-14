package dark.composer.carpet.presentation.fragment.profile.list.customer

import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dark.composer.carpet.R
import dark.composer.carpet.databinding.FragmentCusomersListBinding
import dark.composer.carpet.presentation.fragment.BaseFragment
import dark.composer.carpet.presentation.fragment.admin.FactoryAdapter
import dark.composer.carpet.presentation.fragment.profile.list.ListAdapter

class ListFragment : BaseFragment<FragmentCusomersListBinding>(FragmentCusomersListBinding::inflate) {
    lateinit var viewModel: ListViewModel
    private val listAdapter by lazy {
        ListAdapter(requireContext())
    }
    override fun onViewCreate() {
        viewModel = ViewModelProvider(
            this,
            providerFactory
        )[ListViewModel::class.java]

        binding.list.layoutManager =
            LinearLayoutManager(requireContext())
        binding.list.adapter = listAdapter
        binding.list.showShimmerAdapter()

        viewModel.liveDataList.observe(requireActivity()) {
            it?.let { it1 ->
                binding.list.hideShimmerAdapter()
                listAdapter.setProductListProduct(it1)
            }
        }

        listAdapter.setClickListener {
            navController.navigate(R.id.action_customersListFragment_to_listDetailsFragment,bundleOf("PROFILE_ID" to it))
        }

        viewModel.getProfileList()
    }
}