package dark.composer.carpet.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStarted
import dark.composer.carpet.R
import dark.composer.carpet.databinding.FragmentSigUpBinding
import dark.composer.carpet.mvvm.LogInViewModel
import dark.composer.carpet.mvvm.SigUpViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SigUpFragment : BaseFragment<FragmentSigUpBinding>(FragmentSigUpBinding::inflate) {
    lateinit var viewModel: SigUpViewModel

    override fun onViewCreate() {
        viewModel = ViewModelProvider(this, providerFactory)[SigUpViewModel::class.java]

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.whenStarted {
                viewModel.signUpFlow.collect {
                    if (it) {
                        Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show()
                    }
                }
                viewModel.errorFlow.collect{
//                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                    Log.d("rrrr", "onViewCreate: $it")
                }
            }
        }

        binding.register.setOnClickListener {
            Toast.makeText(requireContext(), "dd", Toast.LENGTH_SHORT).show()
            viewModel.signUp(
                binding.name.text.toString().trim(),
                binding.name.text.toString().trim(),
                binding.phoneNumber.text.toString().trim(),
                binding.password.text.toString().trim(),
                binding.configPassword.text.toString().trim()
            )
        }
    }
}