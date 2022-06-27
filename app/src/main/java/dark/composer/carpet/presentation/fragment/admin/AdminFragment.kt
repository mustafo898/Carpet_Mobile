package dark.composer.carpet.presentation.fragment.admin

import android.util.Log
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import dark.composer.carpet.R
import dark.composer.carpet.databinding.FragmentAdminBinding
import dark.composer.carpet.presentation.fragment.BaseFragment
import dark.composer.carpet.presentation.fragment.itemfragment.FactoryDetailsFragment
import dark.composer.carpet.presentation.fragment.signup.SignUpViewModel
import dark.composer.carpet.utils.SharedPref
import javax.inject.Inject
import kotlin.math.log

class AdminFragment : BaseFragment<FragmentAdminBinding>(FragmentAdminBinding::inflate) {
    private val saleAdapter by lazy {
        SaleAdapter(requireContext())
    }
    @Inject
    lateinit var shared:SharedPref
    lateinit var viewModel: AdminViewModel

    override fun onViewCreate() {
        viewModel = ViewModelProvider(
            this,
            providerFactory
        )[AdminViewModel::class.java]

        binding.listSale.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL,false)
        binding.listSale.adapter = saleAdapter

        viewModel.liveDataListPagination.observe(requireActivity()) {
            saleAdapter.setListFactory(it!!.content)
            Log.d("WWWWWW", "onViewCreate: ${it.content}")
        }

        viewModel.getPagination(0,10)

        binding.txtCustomers.isSelected = true
        binding.txtFactory.isSelected = true
        binding.txtEmployee.isSelected = true

        Glide.with(requireContext()).load(shared.getImage()).into(binding.image)
        binding.userName.text = "${shared.getName()} ${shared.getSurName()}"
        binding.phoneNumber.text = "${shared.getPhoneNumber()}"

        binding.imageMore.setOnClickListener {
            navController.navigate(R.id.action_adminFragment_to_settingsFragment)
        }

        binding.product.setOnClickListener {
            navController.navigate(R.id.action_defaultFragment_to_logInFragment)
        }

        saleAdapter.setClickListener {
            navController.navigate(R.id.action_adminFragment_to_factoryDetailsFragment, bundleOf("ID" to it))
        }
    }
}