package dark.composer.carpet.fragments.mainfragments

import dark.composer.carpet.R
import dark.composer.carpet.databinding.FragmentDefault1Binding
import dark.composer.carpet.databinding.FragmentDefaultBinding
import dark.composer.carpet.fragments.BaseFragment

class DefaultFragment : BaseFragment<FragmentDefault1Binding>(FragmentDefault1Binding::inflate) {
    override fun onViewCreate() {
//        binding.addBtn.setOnClickListener {
//            navController.navigate(R.id.action_defaultFragment_to_logInFragment)
//        }
    }
}