package dark.composer.carpet.fragments.registrFragments

import androidx.lifecycle.ViewModelProvider
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStarted
import dark.composer.carpet.R
import dark.composer.carpet.databinding.FragmentLogInBinding
import dark.composer.carpet.fragments.BaseFragment
import dark.composer.carpet.mvvm.LogInViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class LogInFragment : BaseFragment<FragmentLogInBinding>(FragmentLogInBinding::inflate) {
    lateinit var viewModel: LogInViewModel

    override fun onViewCreate() {
        viewModel = ViewModelProvider(this,providerFactory)[LogInViewModel::class.java]

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.whenStarted {
                viewModel.logInFlow.collect {
                    if (it) {
                        Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show()
                    }
                }

                viewModel.errorFlow.collect {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.logIn.setOnClickListener {
            viewModel.logIn(binding.phoneNumber.text.toString().trim(),binding.password.text.toString().trim())
        }

        binding.sigUp.setOnClickListener {
            navController.navigate(R.id.action_logInFragment_to_sigUpFragment)
        }
    }
}