package dark.composer.carpet.fragments.registrFragments

import android.app.ActivityOptions
import android.os.Build
import android.util.Pair
import android.view.View
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.ViewCompat.animate
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStarted
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.transition.TransitionInflater
import dark.composer.carpet.R
import dark.composer.carpet.databinding.FragmentLogInBinding
import dark.composer.carpet.fragments.BaseFragment
import dark.composer.carpet.mvvm.LogInViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class LogInFragment : BaseFragment<FragmentLogInBinding>(FragmentLogInBinding::inflate) {
    lateinit var viewModel: LogInViewModel

    override fun onViewCreate() {
        viewModel = ViewModelProvider(this,providerFactory)[LogInViewModel::class.java]

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.whenStarted {
                viewModel.logInFlow.collect {
                    if (it) {
                        Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show()
                    }
                }

                viewModel.errorFlow.collect {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.logIn.setOnClickListener {
            viewModel.logIn(binding.phoneNumber.text.toString().trim(),binding.password.text.toString().trim())
        }

        binding.signUp.setOnClickListener {
            ViewCompat.setTransitionName(binding.welcome, "welcome");
            ViewCompat.setTransitionName(binding.logInText, "signInText");
            ViewCompat.setTransitionName(binding.phoneNumber, "phone");
            ViewCompat.setTransitionName(binding.password, "password");
            ViewCompat.setTransitionName(binding.logIn, "go");
            ViewCompat.setTransitionName(binding.signUp, "logIn");

            val extras = FragmentNavigatorExtras(binding.welcome to "welcome",binding.logInText to "signInText",binding.phoneNumber to "phone", binding.password to "password", binding.logIn to "go", binding.signUp to "logIn")
            navController.navigate(R.id.action_logInFragment_to_sigUpFragment,null,null,extras)
        }

        val animation = android.transition.TransitionInflater.from(requireContext()).inflateTransition(R.transition.animate)
        sharedElementEnterTransition = animation
        sharedElementReturnTransition = animation
    }
}