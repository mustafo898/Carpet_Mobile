package dark.composer.carpet.fragments.registrFragments

import androidx.recyclerview.widget.LinearLayoutManager
import dark.composer.carpet.R
import dark.composer.carpet.adapter.CategoryAdapter
import dark.composer.carpet.adapter.PopularAdapter
import dark.composer.carpet.databinding.FragmentDefault1Binding
import dark.composer.carpet.databinding.FragmentDefaultBinding
import dark.composer.carpet.databinding.FragmentSplashBinding
import dark.composer.carpet.dto.CategoryModel
import dark.composer.carpet.fragments.BaseFragment

class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {
    override fun onViewCreate() {
        binding.txt.setOnClickListener {
            navController.navigate(R.id.action_splashFragment_to_logInFragment)
        }
    }
}