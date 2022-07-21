package dark.composer.carpet.presentation.fragment.profile.add.product

import android.opengl.Visibility
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
import androidx.navigation.fragment.findNavController
import dark.composer.carpet.R
import dark.composer.carpet.data.retrofit.models.request.product.ProductCreateRequest
import dark.composer.carpet.databinding.FragmentAddProductBinding
import dark.composer.carpet.presentation.fragment.BaseFragment
import dark.composer.carpet.presentation.fragment.profile.add.factory.AddFactoryViewModel
import kotlinx.coroutines.launch

class AddProductFragment : BaseFragment<FragmentAddProductBinding>(FragmentAddProductBinding::inflate) {
    lateinit var viewModel: AddProductViewModel
    private val adapter by lazy {

    }
    override fun onViewCreate() {
        viewModel = ViewModelProvider(
            this,
            providerFactory
        )[AddProductViewModel::class.java]

        collect()
        textSend()

        binding.accept.setOnClickListener {
//            viewModel.createProduct(ProductCreateRequest(binding.amount.text.toString().trim().toInt(),binding.colour.text.toString().trim(),binding.design.text.toString().trim(),id,binding.height.text.toString().trim().toDouble(),binding.name.text.toString().trim(),binding.pon.text.toString().trim(),binding..text.toString().trim()))
        }
    }

    private fun textSend(){
        binding.nameInput.isHelperTextEnabled = false
        binding.name.addTextChangedListener {
            viewModel.validName(it.toString())
        }

        binding.designInput.isHelperTextEnabled = false
        binding.design.addTextChangedListener {
            viewModel.validDesign(it.toString())
        }

        binding.ponInput.isHelperTextEnabled = false
        binding.pon.addTextChangedListener {
            viewModel.validPon(it.toString())
        }

        binding.widthInput.isHelperTextEnabled = false
        binding.width.addTextChangedListener {
            viewModel.validWidth(it.toString())
        }

        binding.heightInput.isHelperTextEnabled = false
        binding.height.addTextChangedListener {
            viewModel.validHeight(it.toString())
        }

        binding.amountInput.isHelperTextEnabled = false
        binding.amount.addTextChangedListener {
            viewModel.validAmount(it.toString())
        }

//        binding.requireFactory.visibility = View.GONE

        binding.requireType.visibility = View.GONE
        if (binding.countable.isActivated){
            viewModel.validType(1)
        }else if (binding.unCountable.isActivated){
            viewModel.validType(2)
        }
    }

    private fun collect(){
//        viewModel.liveDataAddFactory.observe(viewLifecycleOwner){
//            findNavController().navigate(R.id.action_profileFragment_to_addFactoryFragment)
//        }

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
                        binding.nameInput.isHelperTextEnabled = false
                    } else {
                        binding.nameInput.helperText = it
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.whenStarted {
                viewModel.amountFlow.collect {
                    if (it == "Correct") {
                        binding.amountInput.isHelperTextEnabled = false
                    } else {
                        binding.amountInput.helperText = it
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.whenStarted {
                viewModel.heightFlow.collect {
                    if (it == "Correct") {
                        binding.heightInput.isHelperTextEnabled = false
                    } else {
                        binding.heightInput.helperText = it
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.whenStarted {
                viewModel.widthFlow.collect {
                    if (it == "Correct") {
                        binding.widthInput.isHelperTextEnabled = false
                    } else {
                        binding.widthInput.helperText = it
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.whenStarted {
                viewModel.colorFlow.collect {
                    if (it == "Correct") {
                        binding.colourInput.isHelperTextEnabled = false
                    } else {
                        binding.colourInput.helperText = it
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.whenStarted {
                viewModel.ponFlow.collect {
                    if (it == "Correct") {
                        binding.ponInput.isHelperTextEnabled = false
                    } else {
                        binding.ponInput.helperText = it
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.whenStarted {
                viewModel.designFlow.collect {
                    if (it == "Correct") {
                        binding.designInput.isHelperTextEnabled = false
                    } else {
                        binding.designInput.helperText = it
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.whenStarted {
                viewModel.factoryIdFlow.collect {
                    if (it == "Correct") {
                        binding.requireFactory.visibility = View.GONE
                    } else {
                        binding.requireFactory.visibility = View.VISIBLE
                        binding.requireFactory.text = it
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.whenStarted {
                viewModel.typeFlow.collect {
                    if (it == "Correct") {
                        binding.requireType.visibility = View.GONE
                    } else {
                        binding.requireType.visibility = View.VISIBLE
                        binding.requireType.text = it
                    }
                }
            }
        }
    }
}