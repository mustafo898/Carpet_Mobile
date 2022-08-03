package dark.composer.carpet.presentation.fragment.admin

import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStarted
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayoutMediator
import dark.composer.carpet.R
import dark.composer.carpet.databinding.FragmentAdminBinding
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

//        binding.listSale.layoutManager =
//            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
//        binding.listSale.adapter = factoryAdapter
//        binding.listSale.showShimmerAdapter()

        activity?.findViewById<BottomNavigationView>(R.id.bottomNavigation)?.visibility = View.VISIBLE

        viewModel.liveDataProfile.observe(requireActivity()) {
            it?.let { t ->
                Glide.with(requireContext()).load(t.url).into(binding.image)
                Log.d("RRRRR", "onViewCreate: ${t.name}")
                binding.userName.text = "${t.name} ${t.surname}"
                binding.phoneNumber.text = t.phoneNumber
                binding.phoneNumber.visibility = View.VISIBLE
            }
        }

        val pagerAdapter = ViewPagerAdapter(this)
        binding.viewPager.adapter = pagerAdapter
        binding.animateBar.setupWithViewPager2(binding.viewPager)

        if(shared.getToken().isNullOrEmpty()){
            binding.userName.text = "Carpet"
            binding.image.setImageResource(R.drawable.ic_person)
            binding.phoneNumber.visibility = View.GONE
            binding.logIn.visibility = View.VISIBLE
        }else{
//            binding.userName.visibility = View.VISIBLE
//            binding.image.visibility = View.VISIBLE
//            binding.phoneNumber.visibility = View.VISIBLE
            binding.logIn.visibility = View.GONE
            viewModel.getProfile()
        }

        val anim = AnimationUtils.loadAnimation(requireContext(), R.anim.move_left)
        binding.logIn.startAnimation(anim)

        binding.logIn.setOnClickListener {
            navController.navigate(R.id.action_adminFragment_to_logInFragment)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.whenStarted {
                viewModel.errorFlow.collect {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.image.setOnClickListener {
            navController.navigate(R.id.action_adminFragment_to_profileFragment)
        }

//        factoryAdapter.setClickListener {pos,id->
//            navController.navigate(
//                R.id.action_adminFragment_to_factoryDetailsFragment,
//                bundleOf("ID" to id)
//            )
//        }
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