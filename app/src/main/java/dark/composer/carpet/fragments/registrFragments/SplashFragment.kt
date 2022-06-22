package dark.composer.carpet.fragments.registrFragments

import dark.composer.carpet.R
import dark.composer.carpet.databinding.FragmentSplashBinding
import dark.composer.carpet.fragments.BaseFragment

class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {
    override fun onViewCreate() {
        binding.txt.setOnClickListener {
            navController.navigate(R.id.action_splashFragment_to_settingsFragment)
        }
    }
}