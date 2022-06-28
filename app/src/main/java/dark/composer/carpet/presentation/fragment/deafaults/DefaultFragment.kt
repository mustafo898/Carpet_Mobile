package dark.composer.carpet.presentation.fragment.deafaults

import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import dark.composer.carpet.R
import dark.composer.carpet.databinding.FragmentDefaultBinding
import dark.composer.carpet.data.dto.CategoryModel
import dark.composer.carpet.presentation.fragment.BaseFragment
import dark.composer.carpet.presentation.fragment.admin.FactoryAdapter
import dark.composer.carpet.utils.SharedPref
import javax.inject.Inject

class DefaultFragment : BaseFragment<FragmentDefaultBinding>(FragmentDefaultBinding::inflate) {
    private val factoryAdapter by lazy {
        FactoryAdapter(requireContext())
    }

    private val adapterPopular by lazy {
        PopularAdapter(requireContext())
    }

    @Inject
    lateinit var shared:SharedPref
    lateinit var viewModel: DefaultViewModel

    override fun onViewCreate() {
        viewModel = ViewModelProvider(
            this,
            providerFactory
        )[DefaultViewModel::class.java]

        binding.listSale.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.HORIZONTAL,false)
        binding.listPopular.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.HORIZONTAL,false)
        binding.listSale.adapter = factoryAdapter
        binding.listPopular.adapter = adapterPopular

        adapterPopular.set(setPopular())

        viewModel.liveDataListPagination.observe(requireActivity()) {
            factoryAdapter.setListFactory(it!!.content)
        }

        viewModel.getPagination(0,10)

        if (shared.getToken()!!.isNotEmpty()){
            Glide.with(requireContext()).load(shared.getImage()).into(binding.image)
            binding.userName.text = "${shared.getName()} ${shared.getSurName()}"
            binding.phoneNumber.text = "${shared.getPhoneNumber()}"
        }else{
            binding.image.visibility = View.GONE
            binding.userName.visibility = View.GONE
            binding.phoneNumber.visibility = View.GONE
        }

        binding.order.setOnClickListener {
            navController.navigate(R.id.action_defaultFragment_to_logInFragment)
        }

        factoryAdapter.setClickListener {
            navController.navigate(R.id.action_adminFragment_to_factoryDetailsFragment, bundleOf("ID" to it))
        }
    }

    private fun setPopular():List<CategoryModel>{
        val list = mutableListOf<CategoryModel>()
        for (i in 0 until 10){
            list.add(CategoryModel("Shu zor chiqibti",R.drawable.carpet,"1203369 cym"))
        }
        return list
    }
}