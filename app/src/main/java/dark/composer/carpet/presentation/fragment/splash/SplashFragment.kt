package dark.composer.carpet.presentation.fragment.splash

import android.os.CountDownTimer
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import dark.composer.carpet.R
import dark.composer.carpet.databinding.FragmentSplashBinding
import dark.composer.carpet.presentation.fragment.BaseFragment
import dark.composer.carpet.utils.SharedPref
import javax.inject.Inject

class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {
    @Inject
    lateinit var shared: SharedPref
    override fun onViewCreate() {
        object : CountDownTimer(3000, 100) {
            override fun onFinish() {
                if (shared.getRole() == "ADMIN") {
                    navController.navigate(R.id.action_splashFragment_to_adminFragment2)
                    activity?.findViewById<BottomNavigationView>(R.id.bottomNavigation)?.visibility = View.VISIBLE
                } else {
                    navController.navigate(R.id.action_splashFragment_to_defaultFragment)
                }
            }

            override fun onTick(value: Long) {

            }
        }.start()
    }
}