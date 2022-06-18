package dark.composer.carpet.fragments.mainfragments

import dark.composer.carpet.R
import dark.composer.carpet.databinding.FragmentDefaultBinding
import dark.composer.carpet.fragments.BaseFragment

class DefaultFragment : BaseFragment<FragmentDefaultBinding>(FragmentDefaultBinding::inflate) {
    override fun onViewCreate() {
        binding.addBtn.setOnClickListener {
            navController.navigate(R.id.action_defaultFragment_to_logInFragment)
        }
    }
}