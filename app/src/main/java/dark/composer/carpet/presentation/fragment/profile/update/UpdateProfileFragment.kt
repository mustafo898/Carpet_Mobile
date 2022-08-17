package dark.composer.carpet.presentation.fragment.profile.update

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStarted
import dark.composer.carpet.R
import dark.composer.carpet.databinding.FragmentUpdateProfileBinding
import dark.composer.carpet.presentation.fragment.BaseFragment
import dark.composer.carpet.presentation.fragment.profile.users.update.UpdateUserViewModel
import dark.composer.carpet.utils.BaseNetworkResult
import kotlinx.coroutines.launch
import javax.inject.Inject

class UpdateProfileFragment : BaseFragment<FragmentUpdateProfileBinding>(FragmentUpdateProfileBinding::inflate) {
    @Inject
    lateinit var viewModel: UpdateProfileViewModel

    private var d = 0
    private var role = "CUSTOMER"

    override fun onViewCreate() {
        viewModel = ViewModelProvider(
            this,
            providerFactory
        )[UpdateProfileViewModel::class.java]

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
                            }
                        }
                    }
                }
            }
        }
    }

    private fun send(){
        viewModel.getProfile()

        binding.update.setOnClickListener {
            viewModel.update(
                binding.name.text.toString().trim(),
                binding.lastName.text.toString().trim(),
                binding.password.text.toString().trim(),
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
    }
}