package dark.composer.carpet.presentasion.fragment.settings

import dark.composer.carpet.BuildConfig
import dark.composer.carpet.R
import dark.composer.carpet.a.BaseFragment
import dark.composer.carpet.databinding.FragmentSettingsBinding

class SettingsFragment : BaseFragment<FragmentSettingsBinding>(FragmentSettingsBinding::inflate) {
    override fun onViewCreate() {

        binding.backBtn.setOnClickListener {
            navController.navigate(R.id.action_settingsFragment_to_defaultFragment)
        }

        binding.version.text = BuildConfig.VERSION_NAME

    }

}