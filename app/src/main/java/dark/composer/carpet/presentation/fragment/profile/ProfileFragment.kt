package dark.composer.carpet.presentation.fragment.profile

import dark.composer.carpet.R
import dark.composer.carpet.databinding.FragmentProfileBinding
import dark.composer.carpet.presentation.fragment.BaseFragment

class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {
    override fun onViewCreate() {

        binding.backBtn.setOnClickListener {
            navController.navigate(R.id.action_profileFragment_to_settingsFragment)

        }
    }

}