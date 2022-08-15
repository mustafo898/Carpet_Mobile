package dark.composer.carpet.presentation.fragment.profile

import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStarted
import com.bumptech.glide.Glide
import dark.composer.carpet.R
import dark.composer.carpet.databinding.FragmentProfileNewBinding
import dark.composer.carpet.presentation.fragment.BaseFragment
import dark.composer.carpet.presentation.fragment.dialog.MenuProfile
import dark.composer.carpet.presentation.fragment.dialog.MenuSettings
import dark.composer.carpet.presentation.fragment.product.ProductViewModel
import dark.composer.carpet.utils.BaseNetworkResult
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileFragment : BaseFragment<FragmentProfileNewBinding>(FragmentProfileNewBinding::inflate) {
    @Inject
    lateinit var viewModel: ProfileViewModel

    private val menuSettings by lazy {
        MenuProfile(requireContext(),binding.more)
    }

    override fun onViewCreate() {
        viewModel = ViewModelProvider(
            requireActivity(), providerFactory
        )[ProfileViewModel::class.java]

        observe()
        action()
    }

    private fun action(){
        binding.more.setOnClickListener {
            menuSettings.show()
        }

        menuSettings.setEditClickListener {

        }

        menuSettings.setDeleteClickListener {

        }

        binding.next.setOnClickListener {
            navController.navigate(R.id.action_profileFragment_to_usersFragment)
        }

        binding.add.setOnClickListener {
            navController.navigate(R.id.action_profileFragment_to_createUserFragment)
        }

        binding.back.setOnClickListener {
            navController.popBackStack()
        }
    }

    private fun observe(){
        viewLifecycleOwner.lifecycleScope.launch{
            viewLifecycleOwner.lifecycle.whenStarted {
                viewModel.profile.collect{
                    when(it){
                        is BaseNetworkResult.Error -> { Toast.makeText(requireContext(), "${it.message}", Toast.LENGTH_SHORT).show() }
                        is BaseNetworkResult.Loading -> {
                            Toast.makeText(requireContext(), "Loading..", Toast.LENGTH_SHORT).show() }
                        is BaseNetworkResult.Success -> {
                            it.data?.let {t->
                                binding.name.text = t.name
                                binding.number.text = "+998${t.phoneNumber}"
                                Glide.with(requireContext()).load(t.url).into(binding.image)
                            }
                        }
                    }
                }
            }
        }
    }
}