package dark.composer.carpet.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStarted
import androidx.navigation.fragment.findNavController
import dagger.android.support.DaggerFragment
import dark.composer.carpet.R
import dark.composer.carpet.databinding.FragmentSigUpBinding
import dark.composer.carpet.mvvm.DaggerViewModelFactory
import dark.composer.carpet.mvvm.SignUpViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignUpFragment : DaggerFragment() {
    private lateinit var binding: FragmentSigUpBinding
    lateinit var viewModel: SignUpViewModel

    @Inject
    lateinit var providerFactory: DaggerViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSigUpBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(
            this,
            providerFactory
        )[SignUpViewModel::class.java]

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.whenStarted {
                viewModel.signUpFlow.collect {
                    if (it) {
                        Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_sigUpFragment_to_customerFragment)
                    }
                }
                viewModel.errorFlow.collect {
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

        binding.logIn.setOnClickListener {
            findNavController().navigate(R.id.action_sigUpFragment_to_logInFragment)
        }
    }
}