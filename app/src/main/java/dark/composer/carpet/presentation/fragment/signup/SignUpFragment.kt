package dark.composer.carpet.presentation.fragment.signup

import android.transition.TransitionInflater
import android.view.Gravity
import android.view.LayoutInflater
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
import dark.composer.carpet.databinding.FragmentSigUpBinding
import dark.composer.carpet.presentation.fragment.BaseFragment
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SignUpFragment : BaseFragment<FragmentSigUpBinding>(FragmentSigUpBinding::inflate) {
    lateinit var viewModel: SignUpViewModel

    override fun onViewCreate() {
        viewModel = ViewModelProvider(
            this,
            providerFactory
        )[SignUpViewModel::class.java]

        collect()
        textListener()



        binding.register.setOnClickListener {
            viewModel.signUp(
                binding.name.text.toString().trim(),
                binding.name.text.toString().trim(),
                binding.phoneNumber.text.toString().trim(),
                binding.password.text.toString().trim(),
                binding.confirmPassword.text.toString().trim()
            )
        }

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
                R.id.action_sigUpFragment_to_logInFragment,
                null,
                null,
                extras
            )
        }

        val animation =
            TransitionInflater.from(requireContext()).inflateTransition(R.transition.animate)
        sharedElementEnterTransition = animation
        sharedElementReturnTransition = animation
    }

    private fun successToast(text:String){
        val inflate: LayoutInflater = layoutInflater
        val layout: View =
            inflate.inflate(
                R.layout.custom_toast_green,
                view?.findViewById(R.id.custom_toast_container)
            )

        val toast = Toast(requireContext())
        toast.setGravity(Gravity.TOP, 0, 0)
        toast.duration = Toast.LENGTH_SHORT
        toast.setText(text)
        toast.view = layout
        toast.show()
    }

    private fun collect() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.whenStarted {
                viewModel.signUpFlow.collect {
                    if (it) {
//                        navController.navigate(R.id.action_sigUpFragment_to_customerFragment)
                        activity?.findViewById<BottomNavigationView>(R.id.bottomNavigation)?.visibility = View.VISIBLE
                        successToast("Now you are user, Thanks!")
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