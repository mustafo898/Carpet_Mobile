package dark.composer.carpet.presentation.fragment.login

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStarted
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.google.android.material.bottomnavigation.BottomNavigationView
import dark.composer.carpet.R
import dark.composer.carpet.databinding.FragmentLogInNewBinding
import dark.composer.carpet.presentation.fragment.BaseFragment
import dark.composer.carpet.utils.BaseNetworkResult
import dark.composer.carpet.utils.SharedPref
import kotlinx.coroutines.launch
import javax.inject.Inject

class LogInFragment : BaseFragment<FragmentLogInNewBinding>(FragmentLogInNewBinding::inflate) {
    lateinit var viewModel: LogInViewModel

    @Inject
    lateinit var shared: SharedPref

    override fun onViewCreate() {
        viewModel = ViewModelProvider(this, providerFactory)[LogInViewModel::class.java]

        collect()
        textListener()

        activity?.findViewById<BottomNavigationView>(R.id.bottomNavigation)?.visibility = View.GONE

        binding.signUp.setOnClickListener {
            val extras = FragmentNavigatorExtras(
                binding.welcome to "welcome",
                binding.logInText to "signInText",
                binding.phoneNumber to "phone",
                binding.password to "password",
                binding.logIn to "go",
                binding.signUp to "logIn"
            )
            navController.navigate(R.id.action_logInFragment_to_signUpFragment, null, null, extras)
        }

        binding.logIn.setOnClickListener {
            Log.d(
                "EEEEE",
                "onViewCreate: phone: ${binding.phoneNumber.text}, pass: ${binding.password.text}"
            )
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
                viewModel.loginFlow.collect {
                    when (it) {
                        is BaseNetworkResult.Success-> {
                            shared.setToken(it.data?.jwt?:"")
                            navController.navigate(R.id.action_logInFragment_to_adminFragment)
                            Toast.makeText(requireContext(), "${it.data?.name}", Toast.LENGTH_SHORT).show()
                        }
                        is BaseNetworkResult.Error -> {
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                        }
                        is BaseNetworkResult.Loading -> {
                            Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
                        }
                    }
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