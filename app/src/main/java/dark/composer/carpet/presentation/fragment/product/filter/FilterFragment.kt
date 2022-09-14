package dark.composer.carpet.presentation.fragment.product.filter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import dark.composer.carpet.R
import dark.composer.carpet.databinding.FilterSheetBinding
import dark.composer.carpet.presentation.fragment.BaseFragment
import dark.composer.carpet.presentation.fragment.factory.FactoryViewModel

class FilterFragment : BaseFragment<FilterSheetBinding>(FilterSheetBinding::inflate) {
    private lateinit var viewModel: FilterViewModel
    private lateinit var s: ArrayList<String>

    override fun onViewCreate() {
        viewModel = ViewModelProvider(
            this,
            providerFactory
        )[FilterViewModel::class.java]
        s = ArrayList()
        binding.btn.setOnClickListener {
            s.add(binding.productName.text.toString())
            s.add(binding.pon.text.toString())
            s.add(binding.design.text.toString())
            s.add(binding.color.text.toString())
            s.add(binding.width.text.toString())
            s.add(binding.height.text.toString())
            s.add(binding.type.text.toString())
            val bundle = Bundle()
            bundle.putStringArrayList("FILTER", s)
            navController.navigate(R.id.action_filterFragment_to_productFragment, bundle)
        }

        binding.up.setOnClickListener {
            navController.popBackStack()
        }
    }
}