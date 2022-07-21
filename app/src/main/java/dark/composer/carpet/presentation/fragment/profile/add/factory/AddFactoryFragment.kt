package dark.composer.carpet.presentation.fragment.profile.add.factory

import androidx.lifecycle.ViewModelProvider
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStarted
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import dark.composer.carpet.R
import dark.composer.carpet.data.retrofit.models.request.factory.FactoryAddRequest
import dark.composer.carpet.databinding.FragmentAddFactoryBinding
import dark.composer.carpet.presentation.fragment.BaseFragment
import kotlinx.coroutines.launch

class AddFactoryFragment : BaseFragment<FragmentAddFactoryBinding>(FragmentAddFactoryBinding::inflate){
    lateinit var viewModel: AddFactoryViewModel

    override fun onViewCreate() {
        viewModel = ViewModelProvider(
            this,
            providerFactory
        )[AddFactoryViewModel::class.java]

        collect()
        textSend()

        binding.accept.setOnClickListener {
            viewModel.addFactory(FactoryAddRequest(binding.factory.toString().trim()))
        }
    }

    private fun textSend(){
        binding.factoryInput.isHelperTextEnabled = false
        binding.factory.addTextChangedListener {
            viewModel.validName(it.toString())
        }
    }

    private fun collect(){
        viewModel.liveDataAddFactory.observe(viewLifecycleOwner){
            findNavController().navigate(R.id.action_profileFragment_to_addFactoryFragment)
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
                viewModel.nameFlow.collect {
                    if (it == "Correct") {
                        binding.factoryInput.isHelperTextEnabled = false
                    } else {
                        binding.factoryInput.helperText = it
                    }
                }
            }
        }
    }
}