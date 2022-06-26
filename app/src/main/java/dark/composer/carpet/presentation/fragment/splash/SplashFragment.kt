package dark.composer.carpet.presentation.fragment.splash

import android.os.CountDownTimer
import dark.composer.carpet.R
import dark.composer.carpet.databinding.FragmentSplashBinding
import dark.composer.carpet.presentation.fragment.BaseFragment

class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {
    override fun onViewCreate() {
        object : CountDownTimer(3000, 100) {
            override fun onFinish() {
                navController.navigate(R.id.action_splashFragment_to_defaultFragment)
            }

            override fun onTick(value: Long) {

            }
        }.start()
    }
}