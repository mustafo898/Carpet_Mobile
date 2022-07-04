package dark.composer.carpet.presentation.fragment.product.veiwpager_fragments.deatils

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dark.composer.carpet.R
import dark.composer.carpet.databinding.FragmentProductDetailsBinding
import dark.composer.carpet.presentation.fragment.BaseFragment
import dark.composer.carpet.presentation.fragment.product.veiwpager_fragments.uncountable.UncountableViewModel

class ProductDetailsFragment : BaseFragment<FragmentProductDetailsBinding>(FragmentProductDetailsBinding::inflate) {
    lateinit var viewModel: ProductDetailsViewModel

    override fun onViewCreate() {
        viewModel = ViewModelProvider(
            this,
            providerFactory
        )[ProductDetailsViewModel::class.java]

        var id  = ""
        var type = ""
        val bundle: Bundle? = this.arguments
        bundle?.let {
            id = it.getString("ID","")
            type = it.getString("TYPE","")
        }

        viewModel.productLiveData.observe(requireActivity()){
            it?.let {t->
                binding.date.text = "${t.createDate.substring(11,16)}  ${t.createDate.substring(0,10)}"
                binding.design.text = t.design
                binding.name.text = t.name
                binding.factoryName.text = t.factory.name
                binding.createDate.text = "${t.factory.createdDate.substring(11,16)}  ${t.factory.createdDate.substring(0,10)}"
                binding.pon.text = t.pon
                binding.size.text = "${t.weight} x ${t.height}"
            }
        }

        viewModel.productDetails(id,type)
    }
}