package dark.composer.carpet.presentation.fragment.profile.list.details

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import dark.composer.carpet.R
import dark.composer.carpet.data.retrofit.models.request.profile.ProfileRequest
import dark.composer.carpet.data.retrofit.models.request.profile.create_customer.ProfileCreateRequest
import dark.composer.carpet.databinding.FragmentCustomersDetailsBinding
import dark.composer.carpet.presentation.dialog.UpdateProfileDialog
import dark.composer.carpet.presentation.fragment.BaseFragment
import dark.composer.carpet.presentation.fragment.profile.list.customer.ListViewModel

class ListDetailsFragment : BaseFragment<FragmentCustomersDetailsBinding>(FragmentCustomersDetailsBinding::inflate) {
    lateinit var viewModel: ListDetailsViewModel

    override fun onViewCreate() {
        viewModel = ViewModelProvider(
            this,
            providerFactory
        )[ListDetailsViewModel::class.java]

        var id = 0
        val bundle: Bundle? = this.arguments
        bundle?.let {
            id = it.getInt("PROFILE_ID")
        }
        var f = false
        var name = ""
        var lastName = ""
        viewModel.liveDataProfile.observe(requireActivity()){
            Glide.with(requireContext()).load(it?.url).into(binding.image)
            binding.userFull.text = "${it?.name} ${it?.surname}"
            name = it?.name.toString()
            lastName = it?.surname.toString()
            it?.let {t->
                f = t.visible
            }
        }

        viewModel.getProfileList(id)

        binding.more.setOnClickListener {
            val popup = PopupMenu(requireContext(), binding.more)
            popup.menuInflater.inflate(R.menu.pop_up_menu, popup.menu)
            val d = popup.menu.findItem(R.id.delete_menu)
            val log = popup.menu.findItem(R.id.logout)
            if (f){
                log.title = "Log Out"
            }else{
                log.title = "Log in"
            }
            d.isVisible = false
            popup.setOnMenuItemClickListener(object : MenuItem.OnMenuItemClickListener,
                PopupMenu.OnMenuItemClickListener {
                override fun onMenuItemClick(item: MenuItem): Boolean {
                    when (item.itemId) {
                        R.id.editName -> {
                            val dialog = UpdateProfileDialog(requireContext(), name, lastName)
                            dialog.setPhoneVisible(true)
                            viewModel.liveDataProfile.observe(requireActivity()) {
                                dialog.dismiss()
                            }
                            dialog.setOnAddListener { name, lastname, password,phone ->
                                viewModel.updateProfile(id,ProfileCreateRequest(name,password,phone,"EMPLOYEE",lastname))
                            }
                            dialog.show()
                        }
                        R.id.logout -> {
                            Toast.makeText(
                                requireContext(),
                                "You Clicked : " + item.title,
                                Toast.LENGTH_SHORT
                            ).show()
                            viewModel.deleteProfile(id)
                        }
                        R.id.share -> {

                        }
                    }
                    return true
                }
            })
            popup.show() //showing popup menu
        }
    }
}