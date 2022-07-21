package dark.composer.carpet.presentation.fragment.admin

import android.opengl.Visibility
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenCreated
import androidx.lifecycle.whenStarted
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import dark.composer.carpet.R
import dark.composer.carpet.data.retrofit.models.request.factory.FactoryAddRequest
import dark.composer.carpet.data.retrofit.models.response.factory.FactoryResponse
import dark.composer.carpet.databinding.FragmentAdminBinding
import dark.composer.carpet.presentation.dialog.AddDialog
import dark.composer.carpet.presentation.fragment.BaseFragment
import dark.composer.carpet.utils.SharedPref
import kotlinx.coroutines.launch
import javax.inject.Inject

class AdminFragment : BaseFragment<FragmentAdminBinding>(FragmentAdminBinding::inflate) {
    private val factoryAdapter by lazy {
        FactoryAdapter(requireContext())
    }

    @Inject
    lateinit var shared: SharedPref
    lateinit var viewModel: AdminViewModel

    override fun onViewCreate() {
        viewModel = ViewModelProvider(
            this,
            providerFactory
        )[AdminViewModel::class.java]

        binding.listSale.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.listSale.adapter = factoryAdapter
        binding.listSale.showShimmerAdapter()

        viewModel.liveDataProfile.observe(requireActivity()) {
            it?.let { t ->
                Glide.with(requireContext()).load(t.url).into(binding.image)
                Log.d("RRRRR", "onViewCreate: ${t.name}")
                binding.userName.text = "${t.name} ${t.surname}"
                binding.phoneNumber.text = t.phoneNumber
            }
        }

        viewModel.getProfile()

        viewModel.liveDataListPagination.observe(requireActivity()) {
            it?.let { t ->
                binding.listSale.hideShimmerAdapter()
                factoryAdapter.setListFactory(t.content)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.whenStarted {
                viewModel.errorFlow.collect {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.listPopular.visibility = View.GONE

        binding.salesEmpty.visibility = View.VISIBLE

        viewModel.getPagination(0, 10)

        binding.image.setOnClickListener {
            navController.navigate(R.id.action_adminFragment_to_settingsFragment)
        }

        factoryAdapter.setClickListener {pos,id->
            navController.navigate(
                R.id.action_adminFragment_to_factoryDetailsFragment,
                bundleOf("ID" to id)
            )
        }
    }

//    private fun getData(){
//        val list = ArrayList<FactoryResponse>()
//        for (i in 0 until 14){
//            list.add(FactoryResponse("12",i,"key_$i","Name",null,"Active",true))
//        }
//        factoryAdapter.setListFactory(list)
//        binding.listSale.hideShimmerAdapter()
//    }

//        viewModel.liveDataListPagination.observe(requireActivity()){
//            binding.listSale.hideShimmerAdapter()
//            factoryAdapter.setListFactory(it!!.content)
//        }

}