package dark.composer.carpet.presentation.fragment.signup

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStarted
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dark.composer.carpet.R
import dark.composer.carpet.databinding.FragmentSignNewBinding
import dark.composer.carpet.presentation.fragment.BaseFragment
import dark.composer.carpet.utils.BaseNetworkResult
import dark.composer.carpet.utils.SharedPref
import kotlinx.coroutines.launch
import javax.inject.Inject


class SignUpFragment : BaseFragment<FragmentSignNewBinding>(FragmentSignNewBinding::inflate) {
    lateinit var viewModel: SignUpViewModel

    @Inject
    lateinit var shared: SharedPref

    override fun onViewCreate() {
        viewModel = ViewModelProvider(
            this,
            providerFactory
        )[SignUpViewModel::class.java]

        binding.logIn.setOnClickListener {
            val extras = FragmentNavigatorExtras(
                binding.welcome to "welcome",
                binding.logInText to "signInText",
                binding.phoneNumber to "phone",
                binding.password to "password",
                binding.register to "go",
                binding.logIn to "logIn"
            )
            findNavController().navigate(
                R.id.action_signUpFragment_to_logInFragment,
                null,
                null,
                extras
            )
        }

        collect()
        textListener()
    }

    private fun collect() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.whenStarted {
                viewModel.signUpFlow.collect {
                    when(it){
                        is BaseNetworkResult.Success -> {
                            shared.setToken(it.data?.jwt?:"")
                            navController.navigate(R.id.action_signUpFragment_to_adminFragment)
                            activity?.findViewById<BottomNavigationView>(R.id.bottomNavigation)?.visibility = View.VISIBLE
                            Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show()
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

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.whenStarted {
                viewModel.nameFlow.collect {
                    if (it == "Correct") {
                        binding.nameInput.isHelperTextEnabled = false
                    } else {
                        binding.nameInput.helperText = it
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.whenStarted {
                viewModel.surNameFlow.collect {
                    if (it == "Correct") {
                        binding.lastNameInput.isHelperTextEnabled = false
                    } else {
                        binding.lastNameInput.helperText = it
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.whenStarted {
                viewModel.confirmPasswordFlow.collect {
                    if (it == "Correct") {
                        binding.confirmPasswordInput.isHelperTextEnabled = false
                    } else {
                        binding.confirmPasswordInput.helperText = it
                    }
                }
            }
        }
    }


    private fun textListener() {
        var confirm = ""
        binding.register.setOnClickListener {
            viewModel.signUp(
                binding.name.text.toString().trim(),
                binding.lastName.text.toString().trim(),
                binding.phoneNumber.text.toString().trim(),
                binding.password.text.toString().trim(),
                binding.confirmPassword.text.toString().trim()
            )
        }

        binding.passwordInput.isHelperTextEnabled = false
        binding.password.addTextChangedListener {
            viewModel.validPassword(it.toString())
            confirm = it.toString()
        }

        binding.nameInput.isHelperTextEnabled = false
        binding.name.addTextChangedListener {
            viewModel.validName(it.toString())
        }

        binding.lastNameInput.isHelperTextEnabled = false
        binding.lastName.addTextChangedListener {
            viewModel.validSurname(it.toString())
        }

        binding.confirmPasswordInput.isHelperTextEnabled = false
        binding.confirmPassword.addTextChangedListener {
            viewModel.validConfirmPassword(it.toString(), confirm)
        }

        binding.phoneNumberInput.isHelperTextEnabled = false
        binding.phoneNumber.addTextChangedListener {
            viewModel.validPhone(it.toString())
        }
    }
}