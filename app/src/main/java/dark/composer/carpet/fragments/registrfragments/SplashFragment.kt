package dark.composer.carpet.fragments.registrfragments

import androidx.loader.app.LoaderManager
import androidx.recyclerview.widget.LinearLayoutManager
import dark.composer.carpet.R
import dark.composer.carpet.adapter.CategoryAdapter
import dark.composer.carpet.adapter.PopularAdapter
import dark.composer.carpet.databinding.FragmentDefBinding
import dark.composer.carpet.databinding.FragmentSplashBinding
import dark.composer.carpet.dto.CategoryModel
import dark.composer.carpet.fragments.BaseFragment

class SplashFragment : BaseFragment<FragmentDefBinding>(FragmentDefBinding::inflate) {
    private val adapterCategory by lazy {
        CategoryAdapter(requireContext())
    }

    private val adapterPopular by lazy {
        PopularAdapter(requireContext())
    }

    override fun onViewCreate() {
        binding.listCategory.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        binding.listPopular.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        binding.listCategory.adapter = adapterCategory
        binding.listPopular.adapter = adapterPopular

        adapterCategory.set(setCategory())
        adapterPopular.set(setPopular())

        binding.txtCustomers.isSelected = true
        binding.txtAdmin.isSelected = true
        binding.txtEmployee.isSelected = true
    }

    private fun setCategory():List<CategoryModel>{
        val list = mutableListOf<CategoryModel>()
        for (i in 0 until 10){
            list.add(CategoryModel("Shu zor chiqibti ggggggggggg",R.drawable.carpet,"1203369 cym"))
        }
        return list
    }

    private fun setPopular():List<CategoryModel>{
        val list = mutableListOf<CategoryModel>()
        for (i in 0 until 10){
            list.add(CategoryModel("Shu zor chiqibti",R.drawable.carpet,"1203369 cym"))
        }
        return list
    }
//    private fun startTimer() {
//        Handler().postDelayed({
//            if (shared.getToken() != "") {
//                extention.controller?.startMainFragment(TrainerFragment())
//            } else {
//                extention.controller?.startMainFragment(RegisterFragment())
//            }
//        }, 3000)
//    }
}