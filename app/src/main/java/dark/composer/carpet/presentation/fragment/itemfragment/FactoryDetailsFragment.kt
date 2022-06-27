package dark.composer.carpet.presentation.fragment.itemfragment

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import dark.composer.carpet.databinding.FragmentFactoryDetailsBinding
import dark.composer.carpet.presentation.fragment.BaseFragment

class FactoryDetailsFragment : BaseFragment<FragmentFactoryDetailsBinding>(FragmentFactoryDetailsBinding::inflate) {
    private lateinit var viewModel: FactoryDetailsViewModel

    override fun onViewCreate() {
        viewModel = ViewModelProvider(
            this,
            providerFactory
        )[FactoryDetailsViewModel::class.java]

        var id = 0
        val bundle: Bundle? = this.arguments
        bundle?.let {
            id = it.getInt("ID")
        }

        Toast.makeText(requireContext(), id.toString(), Toast.LENGTH_SHORT).show()

        viewModel.liveDataInfoFactory.observe(requireActivity()){
            if (it != null){
                binding.date.text = it.createdDate.substring(0,10)
                binding.time.text = it.createdDate.substring(11,16)
                binding.status.text = it.status
                Glide.with(requireContext()).load(it.photoUrl).into(binding.image)
            }else{
                binding.date.text = "..."
                binding.status.text = "..."
                binding.time.text = "..."
            }
        }

        viewModel.getPagination(id)
    }
}