package dark.composer.carpet.presentation.fragment.profile.users

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dark.composer.carpet.R
import dark.composer.carpet.databinding.FragmentUsersBinding
import dark.composer.carpet.presentation.fragment.BaseFragment
import dark.composer.carpet.presentation.fragment.profile.ProfileViewModel
import okhttp3.internal.userAgent
import javax.inject.Inject

class UsersFragment : BaseFragment<FragmentUsersBinding>(FragmentUsersBinding::inflate) {
    @Inject
    lateinit var viewModel: UsersViewModel

    override fun onViewCreate() {
        viewModel = ViewModelProvider(
            requireActivity(), providerFactory
        )[UsersViewModel::class.java]

    }
}