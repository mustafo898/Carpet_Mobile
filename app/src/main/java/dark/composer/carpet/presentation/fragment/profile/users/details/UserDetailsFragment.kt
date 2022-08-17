package dark.composer.carpet.presentation.fragment.profile.users.details

import android.os.Bundle
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStarted
import com.bumptech.glide.Glide
import dark.composer.carpet.R
import dark.composer.carpet.databinding.FragmentUserDetailsBinding
import dark.composer.carpet.presentation.fragment.BaseFragment
import dark.composer.carpet.presentation.fragment.dialog.MenuProfile
import dark.composer.carpet.utils.BaseNetworkResult
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserDetailsFragment :
    BaseFragment<FragmentUserDetailsBinding>(FragmentUserDetailsBinding::inflate) {
    @Inject
    lateinit var viewModel: UserDetailsViewModel

    private val menuSettings by lazy {
        MenuProfile(requireContext(),binding.more)
    }

    private var d = 0

    override fun onViewCreate() {
        viewModel = ViewModelProvider(
            requireActivity(), providerFactory
        )[UserDetailsViewModel::class.java]

        val bundle: Bundle? = this.arguments
        bundle?.let {
            d = it.getInt("ID", 0)
        }

        observe()
        action()
    }

    private fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.whenStarted {
                viewModel.profile.collect {
                    when (it) {
                        is BaseNetworkResult.Error -> {
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                        }
                        is BaseNetworkResult.Loading -> {
                            Toast.makeText(requireContext(), "Loading...", Toast.LENGTH_SHORT)
                                .show()
                        }
                        is BaseNetworkResult.Success -> {
                            it.data?.let { t ->
                                binding.name.text = t.name + " " + t.surname
                                binding.number.text ="+998 " + t.phoneNumber
                                Glide.with(requireContext()).load(t.url)
                                    .error(R.drawable.ic_person)
                                    .into(binding.image)
                            }
                        }
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.whenStarted {
                viewModel.delete.collect {
                    when (it) {
                        is BaseNetworkResult.Error -> {
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                        }
                        is BaseNetworkResult.Loading -> {
                            Toast.makeText(requireContext(), "Loading...", Toast.LENGTH_SHORT)
                                .show()
                        }
                        is BaseNetworkResult.Success -> {
                            it.data?.let { t ->
                                Toast.makeText(requireContext(), "Success log out${t.name}", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun action() {
        viewModel.getProfile(d)

        binding.more.setOnClickListener {
            menuSettings.show()
        }

        menuSettings.setEditClickListener {
            navController.navigate(R.id.action_userDetailsFragment_to_updateUserFragment, bundleOf("ID" to d))
        }

        menuSettings.setDeleteClickListener {
            viewModel.deleteProfile(d)
        }

        binding.back.setOnClickListener {
            navController.popBackStack()
        }
    }
}