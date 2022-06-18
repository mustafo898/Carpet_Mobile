package dark.composer.carpet.fragments.registrfragments

import dark.composer.carpet.R
import dark.composer.carpet.databinding.FragmentSplashBinding
import dark.composer.carpet.fragments.BaseFragment

class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {
    override fun onViewCreate() {
        binding.txt.setOnClickListener {
            navController.navigate(R.id.action_splashFragment_to_defaultFragment)
            navController.popBackStack(R.id.action_splashFragment_to_defaultFragment,true)
        }
    }

//    private fun startTimer() {
//        Handler().postDelayed({
//            if (shared.getToken() != "") {
//                extention.controller?.startMainFragment(TrainerFragment())
//            } else {
//                extention.controller?.startMainFragment(RegisterFragment())
//            }
//        }, 3000)
//    }
}