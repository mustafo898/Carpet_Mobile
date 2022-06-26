package dark.composer.carpet.presentation.fragment.admin

import com.bumptech.glide.Glide
import dark.composer.carpet.R
import dark.composer.carpet.databinding.FragmentAdminBinding
import dark.composer.carpet.presentation.fragment.BaseFragment
import dark.composer.carpet.utils.SharedPref
import javax.inject.Inject

class AdminFragment : BaseFragment<FragmentAdminBinding>(FragmentAdminBinding::inflate) {

    @Inject
    lateinit var shared:SharedPref

    override fun onViewCreate() {
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
    }
}