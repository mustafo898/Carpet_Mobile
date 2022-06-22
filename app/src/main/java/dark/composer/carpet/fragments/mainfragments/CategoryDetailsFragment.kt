package dark.composer.carpet.fragments.mainfragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.transition.TransitionInflater
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dark.composer.carpet.R
import dark.composer.carpet.databinding.FragmentCategoryDetailsBinding
import dark.composer.carpet.fragments.BaseFragment
import dark.composer.carpet.mvvm.CategoryDetailsViewModel

class CategoryDetailsFragment : BaseFragment<FragmentCategoryDetailsBinding>(FragmentCategoryDetailsBinding::inflate){
    private lateinit var viewModel: CategoryDetailsViewModel

    override fun onViewCreate() {
        val bundle: Bundle? = this.arguments
        bundle?.let {
            val description = bundle.getString("DESC")
            val price = bundle.getString("PRICE")
            val image = bundle.getString("IMAGE")
            Log.d("QQQQ", "onViewCreate: $description")
            binding.description.transitionName = description
            binding.price.transitionName = price
            binding.image.transitionName = image
        }

        val animation = TransitionInflater.from(requireContext()).inflateTransition(R.transition.animate)
        sharedElementEnterTransition = animation
        sharedElementReturnTransition = animation
    }
}