package dark.composer.carpet.presentation.fragment.factory

import android.util.Log
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStarted
import dark.composer.carpet.R
import dark.composer.carpet.databinding.FragmentFactoryNewBinding
import dark.composer.carpet.presentation.fragment.adapters.FactoryAdapter
import dark.composer.carpet.presentation.fragment.BaseFragment
import dark.composer.carpet.utils.BaseNetworkResult
import kotlinx.coroutines.launch
import javax.inject.Inject

class FactoryFragment : BaseFragment<FragmentFactoryNewBinding>(FragmentFactoryNewBinding::inflate) {
    @Inject
    lateinit var viewModel: FactoryViewModel
    private val factoryAdapter by lazy {
        FactoryAdapter()
    }
    override fun onViewCreate() {
        viewModel = ViewModelProvider(
            this,
            providerFactory
        )[FactoryViewModel::class.java]

        setUpUi()
        observe()
        send()
        action()
    }

    private fun setUpUi(){
        binding.factoryList.adapter = factoryAdapter
    }

    private fun observe(){
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.whenStarted {
                viewModel.factory.collect {
                    when (it) {
                        is BaseNetworkResult.Success -> {
                            Log.d("EEEEE", "observe: ${it.data}")
                            factoryAdapter.setList(
                                it.data?:emptyList()
                            )
                        }
                        is BaseNetworkResult.Loading -> {}
                        is BaseNetworkResult.Error -> {Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show() }
                    }
                }
            }
        }
    }

    private fun send(){
        viewModel.getFactoryList(0,20)
    }

    private fun action(){
        binding.back.setOnClickListener {
            navController.popBackStack()
        }

        factoryAdapter.setClickListener {
            navController.navigate(R.id.action_factoryFragment_to_factoryDetailsFragment, bundleOf("ID" to it))
        }

        binding.add.setOnClickListener {
            navController.navigate(R.id.action_factoryFragment_to_addFactoryFragment)
        }
    }
}