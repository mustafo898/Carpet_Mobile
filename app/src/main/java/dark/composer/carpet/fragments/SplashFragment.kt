package dark.composer.carpet.fragments

import dark.composer.carpet.R
import dark.composer.carpet.databinding.FragmentSplashBinding

class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {
    override fun onViewCreate() {
        binding.txt.setOnClickListener {
            navController.navigate(R.id.action_splashFragment_to_defaultFragment)
        }
    }
}