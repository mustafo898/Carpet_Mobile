package dark.composer.carpet.presentation.fragment.profile.update

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStarted
import dark.composer.carpet.R
import dark.composer.carpet.databinding.FragmentUpdateUserBinding
import dark.composer.carpet.presentation.fragment.BaseFragment
import dark.composer.carpet.presentation.fragment.profile.create.CreateUserViewModel
import dark.composer.carpet.utils.BaseNetworkResult
import kotlinx.coroutines.launch
import javax.inject.Inject

class UpdateUserFragment : BaseFragment<FragmentUpdateUserBinding>(FragmentUpdateUserBinding::inflate) {
    @Inject
    lateinit var viewModel: UpdateUserViewModel

    private var d = 0
    private var role = "CUSTOMER"

    override fun onViewCreate() {
        viewModel = ViewModelProvider(
            this,
            providerFactory
        )[UpdateUserViewModel::class.java]

        val bundle: Bundle? = this.arguments
        bundle?.let {
            d = it.getInt("ID", 0)
        }

        observe()
        send()
    }

    private fun observe(){
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
                viewModel.update.collect {
                    when(it){
                        is BaseNetworkResult.Error -> {
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()}
                        is BaseNetworkResult.Loading -> {
                            Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
                        }
                        is BaseNetworkResult.Success -> {
                            Toast.makeText(requireContext(), "Success added ${it.data?.name}", Toast.LENGTH_SHORT).show()
                            navController.popBackStack()
                        }
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
                viewModel.profile.collect {
                    when(it){
                        is BaseNetworkResult.Error -> {
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()}
                        is BaseNetworkResult.Loading -> {
                            Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
                        }
                        is BaseNetworkResult.Success -> {
                            it.data?.let {t->
                                binding.name.setText(t.name)
                                binding.lastName.setText(t.surname)
                                binding.phoneNumber.setText(t.phoneNumber)
                                if (t.role == "CUSTOMER"){
                                    binding.customer.isChecked = true
                                }else{
                                    binding.employee.isChecked = true
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun send(){
        viewModel.getProfile(d)
        binding.update.setOnClickListener {
            viewModel.update(
                d,
                binding.name.text.toString().trim(),
                binding.lastName.text.toString().trim(),
                binding.phoneNumber.text.toString().trim(),
                binding.password.text.toString().trim(),
                role
            )
        }

        binding.passwordInput.isHelperTextEnabled = false
        binding.password.addTextChangedListener {
            viewModel.validPassword(it.toString())
        }

        binding.nameInput.isHelperTextEnabled = false
        binding.name.addTextChangedListener {
            viewModel.validName(it.toString())
        }

        binding.lastNameInput.isHelperTextEnabled = false
        binding.lastName.addTextChangedListener {
            viewModel.validSurname(it.toString())
        }

        binding.phoneNumberInput.isHelperTextEnabled = false
        binding.phoneNumber.addTextChangedListener {
            viewModel.validPhone(it.toString())
        }

        binding.customer.isChecked = true
        binding.customer.setOnClickListener {
            role = "CUSTOMER"
        }
        binding.employee.setOnClickListener {
            role = "EMPLOYEE"
        }
    }
}