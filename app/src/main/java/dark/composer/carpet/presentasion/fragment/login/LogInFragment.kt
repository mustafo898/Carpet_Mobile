package dark.composer.carpet.presentasion.fragment.login

import android.util.Log
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStarted
import androidx.navigation.fragment.FragmentNavigatorExtras
import dark.composer.carpet.R
import dark.composer.carpet.databinding.FragmentLogInBinding
import dark.composer.carpet.presentasion.fragment.BaseFragment
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class LogInFragment : BaseFragment<FragmentLogInBinding>(FragmentLogInBinding::inflate) {
    lateinit var viewModel: LogInViewModel
    override fun onViewCreate() {
        viewModel = ViewModelProvider(this, providerFactory)[LogInViewModel::class.java]

        collect()
        textListener()

        binding.signUp.setOnClickListener {
            val extras = FragmentNavigatorExtras(
                binding.welcome to "welcome",
                binding.logInText to "signInText",
                binding.phoneNumber to "phone",
                binding.password to "password",
                binding.logIn to "go",
                binding.signUp to "logIn"
            )
            navController.navigate(R.id.action_logInFragment_to_sigUpFragment, null, null, extras)
        }

        binding.logIn.setOnClickListener {
            Log.d("EEEEE", "onViewCreate: ${binding.phoneNumber.text}")
            viewModel.logIn(
                binding.phoneNumber.text.toString().trim(),
                binding.password.text.toString().trim()
            )
        }

        val animation = android.transition.TransitionInflater.from(requireContext())
            .inflateTransition(R.transition.animate)
        sharedElementEnterTransition = animation
        sharedElementReturnTransition = animation
    }

    private fun textListener() {
        binding.passwordInput.isHelperTextEnabled = false
        binding.password.addTextChangedListener {
            viewModel.validPassword(it.toString())
        }

        binding.phoneNumberInput.isHelperTextEnabled = false
        binding.phoneNumber.addTextChangedListener {
            viewModel.validPhone(it.toString())
        }
    }

    private fun collect() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.whenStarted {
                viewModel.logInFlow.collect {
                    if (it) {
                        navController.navigate(R.id.action_logInFragment_to_customerFragment)
                        Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.whenStarted {
                viewModel.errorFlow.collect {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.whenStarted {
                viewModel.passwordFlow.collect {
                    if (it == "Correct") {
                        binding.passwordInput.isHelperTextEnabled = false
                    } else {
                        binding.passwordInput.helperText = it
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.whenStarted {
                viewModel.phoneFlow.collect {
                    if (it == "Correct") {
                        binding.phoneNumberInput.isHelperTextEnabled = false
                    } else {
                        binding.phoneNumberInput.helperText = it
                    }
                }
            }
        }
    }
}