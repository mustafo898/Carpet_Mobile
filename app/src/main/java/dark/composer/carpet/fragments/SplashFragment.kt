package dark.composer.carpet.fragments

import android.os.Handler
import dark.composer.carpet.R
import dark.composer.carpet.databinding.FragmentSplashBinding

class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {
    override fun onViewCreate() {
        binding.txt.setOnClickListener {
            navController.navigate(R.id.action_splashFragment_to_defaultFragment)
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