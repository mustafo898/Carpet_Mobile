package dark.composer.carpet.presentation.fragment.itemfragment

import android.app.AlertDialog
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import dark.composer.carpet.data.retrofit.models.request.factory.update.FactoryUpdateRequest
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
        var visible = false
        viewModel.liveDataInfoFactory.observe(requireActivity()){
            if (it != null){
                binding.date.text = it.createdDate.substring(0,10)
                binding.time.text = it.createdDate.substring(11,16)
                binding.status.text = it.name
                visible = it.visible
                Glide.with(requireContext()).load(it.photoUrl).into(binding.image)
            }else{
                binding.date.text = "..."
                binding.status.text = "..."
                binding.time.text = "..."
            }
        }

        binding.update.setOnClickListener {
            val dialog = AlertDialog.Builder(requireContext())

            dialog.setTitle("Update Factory")
            dialog.setMessage("Name")
            dialog.setPositiveButton("Ok") { dialog, d ->
                viewModel.updateInfoFactory(FactoryUpdateRequest(id,"Name","ACTIVE",true),id)
            }
            dialog.setNegativeButton("No",){ dialog,d->
                dialog.cancel()
                dialog.dismiss()
            }
            dialog.show()
        }

        binding.delete.setOnClickListener {
            val dialog = AlertDialog.Builder(requireContext())

            dialog.setTitle("Update Factory")
            if (visible){
                dialog.setMessage("${!visible}")
            }else{
                dialog.setMessage("$visible")
            }
            dialog.setPositiveButton("Ok") { dialog, d ->
                viewModel.updateInfoFactory(FactoryUpdateRequest(id,"Name","ACTIVE",false),id)
            }
            dialog.setNegativeButton("No",){ dialog,d->
                dialog.cancel()
                dialog.dismiss()
            }
            dialog.show()
        }


        viewModel.getInfoFactory(id)
    }
}