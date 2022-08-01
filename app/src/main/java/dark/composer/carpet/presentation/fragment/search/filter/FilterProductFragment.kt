package dark.composer.carpet.presentation.fragment.search.filter

import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dark.composer.carpet.R
import dark.composer.carpet.databinding.FragmentFilterProductBinding
import dark.composer.carpet.presentation.fragment.BaseFragment
import dark.composer.carpet.presentation.fragment.profile.add.FactorySelectAdapter
import dark.composer.carpet.presentation.fragment.profile.add.product.AddProductViewModel
import dark.composer.carpet.presentation.fragment.search.SearchViewModel
import javax.inject.Inject

class FilterProductFragment :
    BaseFragment<FragmentFilterProductBinding>(FragmentFilterProductBinding::inflate) {
    @Inject
    lateinit var viewModel: FilterProductViewModel

    private val adapter by lazy {
        FactorySelectAdapter(requireContext())
    }

    override fun onViewCreate() {
        viewModel = ViewModelProvider(
            this,
            providerFactory
        )[FilterProductViewModel::class.java]

        binding.factoryList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.factoryList.itemAnimator = DefaultItemAnimator()
        binding.factoryList.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                LinearLayoutManager.HORIZONTAL
            )
        )
        binding.countable.isChecked = true

        binding.factoryList.adapter = adapter

        var t = "COUNTABLE"
        var s = ""
        adapter.setClickListener { position, name ->
            s = name
        }

        viewModel.typeFlow.observe(requireActivity()) {
            viewModel.typeFlow.observe(viewLifecycleOwner) {
                if (it == binding.unCountable.text.toString()
                        .uppercase() || it == binding.countable.text.toString().uppercase()
                ) {
                    t = it
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                    binding.requireType.visibility = View.GONE
                } else {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                    binding.requireType.visibility = View.VISIBLE
                    binding.requireType.text = it
                }
            }
        }

        viewModel.liveDataListPagination.observe(requireActivity()) {
            it?.content?.let { it1 -> adapter.setListFactory(it1) }
        }

        binding.imageBack.setOnClickListener {
            navController.navigate(R.id.action_filterProductFragment_to_searchFragment)
        }

        viewModel.getPagination(0, 15)

        viewModel.validType(binding.countable.text.toString().uppercase())

        binding.countable.setOnClickListener {
            viewModel.validType(binding.countable.text.toString().uppercase())
        }

        binding.unCountable.setOnClickListener {
            viewModel.validType(binding.unCountable.text.toString().uppercase())
        }

        binding.accept.setOnClickListener {
            navController.navigate(
                R.id.action_filterProductFragment_to_searchFragment,
                bundleOf(
                    "PON" to binding.pon.text.toString(),
                    "NAME" to binding.name.text.toString(),
                    "DESIGN" to binding.design.text.toString(),
                    "WIDTH" to binding.width.text.toString(),
                    "HEIGHT" to binding.height.text.toString(),
                    "TYPE" to t,
                    "FACTORY_NAME" to s,
                    "COLOR" to binding.colour.text.toString()
                )
            )
        }
    }
}