package dark.composer.carpet.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dark.composer.carpet.R
import dark.composer.carpet.databinding.FragmentSigUpBinding
import dark.composer.carpet.mvvm.SigUpViewModel

class SigUpFragment : BaseFragment<FragmentSigUpBinding>(FragmentSigUpBinding::inflate) {
    override fun onViewCreate() {

        binding.txt.setOnClickListener {
            navController.navigate(R.id.action_sigUpFragment_to_logInFragment)
        }
    }
}