package dark.composer.carpet.presentasion.fragment.splash

import dark.composer.carpet.R
import dark.composer.carpet.a.BaseFragment
import dark.composer.carpet.databinding.FragmentSplashBinding

class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {
    override fun onViewCreate() {
        binding.txt.setOnClickListener {
            navController.navigate(R.id.action_splashFragment_to_settingsFragment)
        }
    }
}