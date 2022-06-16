package dark.composer.carpet.fragments

import dark.composer.carpet.R
import dark.composer.carpet.databinding.FragmentDefaultBinding

class DefaultFragment : BaseFragment<FragmentDefaultBinding>(FragmentDefaultBinding::inflate) {
    override fun onViewCreate() {
        binding.txt.setOnClickListener {
            navController.navigate(R.id.action_defaultFragment_to_logInFragment)
        }
    }
}