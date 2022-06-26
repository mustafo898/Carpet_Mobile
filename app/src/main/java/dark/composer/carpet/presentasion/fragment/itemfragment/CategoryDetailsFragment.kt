package dark.composer.carpet.presentasion.fragment.itemfragment

import android.os.Bundle
import android.transition.TransitionInflater
import android.util.Log
import android.widget.Toast
import dark.composer.carpet.R
import dark.composer.carpet.databinding.FragmentCategoryDetailsBinding
import dark.composer.carpet.presentasion.fragment.BaseFragment

class CategoryDetailsFragment : BaseFragment<FragmentCategoryDetailsBinding>(FragmentCategoryDetailsBinding::inflate){
    private lateinit var viewModel: CategoryDetailsViewModel

    override fun onViewCreate() {
        val bundle: Bundle? = this.arguments
        bundle?.let {
            val description = bundle.getString("DESC")
            val price = bundle.getString("PRICE")
            val image = bundle.getString("IMAGE")
            Log.d("WWWW", "onViewCreate: $description")
            binding.description.transitionName = description
            Toast.makeText(requireContext(), "${binding.description.transitionName}  ", Toast.LENGTH_SHORT).show()
            binding.price.transitionName = price
            binding.image.transitionName = image
        }

        val animation = TransitionInflater.from(requireContext()).inflateTransition(R.transition.animate)
        sharedElementEnterTransition = animation
        sharedElementReturnTransition = animation
    }
}