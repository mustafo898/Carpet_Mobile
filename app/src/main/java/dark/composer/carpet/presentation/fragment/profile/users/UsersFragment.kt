package dark.composer.carpet.presentation.fragment.profile.users

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStarted
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import dark.composer.carpet.R
import dark.composer.carpet.databinding.FragmentUsersBinding
import dark.composer.carpet.presentation.fragment.BaseFragment
import dark.composer.carpet.presentation.fragment.adapters.UsersAdapter
import dark.composer.carpet.presentation.fragment.profile.ProfileViewModel
import dark.composer.carpet.utils.BaseNetworkResult
import kotlinx.coroutines.launch
import okhttp3.internal.addHeaderLenient
import okhttp3.internal.userAgent
import javax.inject.Inject

class UsersFragment : BaseFragment<FragmentUsersBinding>(FragmentUsersBinding::inflate) {
    @Inject
    lateinit var viewModel: UsersViewModel

    private val usersAdapter by lazy {
        UsersAdapter()
    }

    override fun onViewCreate() {
        viewModel = ViewModelProvider(
            requireActivity(), providerFactory
        )[UsersViewModel::class.java]

        binding.list.layoutManager = LinearLayoutManager(requireContext())
        binding.list.adapter = usersAdapter
        action()
        observe()
        send()
    }

    fun observe(){
        viewLifecycleOwner.lifecycleScope.launch{
            viewLifecycleOwner.lifecycle.whenStarted {
                viewModel.usersList.collect{
                    when(it){
                        is BaseNetworkResult.Error -> {
                            Log.d("PPPPPERROR", "ERROR ${it.message}")
                            Toast.makeText(requireContext(), "${it.message}", Toast.LENGTH_SHORT).show() }
                        is BaseNetworkResult.Loading -> {
                            Toast.makeText(requireContext(), "Loading..", Toast.LENGTH_SHORT).show() }
                        is BaseNetworkResult.Success -> {
                            Log.d("PPPPP", "users ${it.data}")
                            it.data?.let { t->
                                Log.d("PPPPP", "observe: ${t.content}")
                                usersAdapter.setList(t.content?: emptyList())
                            }
                        }
                    }
                }
            }
        }
    }

    fun send(){
        binding.back.setOnClickListener {
            navController.popBackStack()
        }

        usersAdapter.setClickListener {
            navController.navigate(R.id.action_usersFragment_to_userDetailsFragment, bundleOf("ID" to it))
        }

        viewModel.getList(20,0)
    }

    private fun action(){
//        binding.back.setOnClickListener {
//            navController.navigate(R.id.action_usersFragment_to_profileFragment)
//        }
    }
}