package dark.composer.carpet.presentation.fragment.settings

import dark.composer.carpet.BuildConfig
import dark.composer.carpet.R
import dark.composer.carpet.databinding.FragmentSettingsBinding
import dark.composer.carpet.presentation.fragment.BaseFragment
import dark.composer.carpet.utils.SharedPref
import javax.inject.Inject

class SettingsFragment : BaseFragment<FragmentSettingsBinding>(FragmentSettingsBinding::inflate) {

    @Inject
    lateinit var sharedPref: SharedPref

    override fun onViewCreate() {
        binding.profile.setOnClickListener {
            if (sharedPref.getToken().isNullOrEmpty()){
                navController.navigate(R.id.action_settingsFragment_to_logInFragment)
            }else{
                navController.navigate(R.id.action_settingsFragment_to_profileFragment)
            }
        }

        binding.version.text = BuildConfig.VERSION_NAME
    }
}