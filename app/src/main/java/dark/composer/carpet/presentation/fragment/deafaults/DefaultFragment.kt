package dark.composer.carpet.presentation.fragment.deafaults

import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import dark.composer.carpet.R
import dark.composer.carpet.presentation.fragment.itemfragment.CategoryDetailsFragment
import dark.composer.carpet.databinding.FragmentDefaultBinding
import dark.composer.carpet.data.dto.CategoryModel
import dark.composer.carpet.presentation.fragment.BaseFragment
import dark.composer.carpet.utils.SharedPref
import javax.inject.Inject


class DefaultFragment : BaseFragment<FragmentDefaultBinding>(FragmentDefaultBinding::inflate) {
    private val adapterCategory by lazy {
        CategoryAdapter(requireContext())
    }

    private val adapterPopular by lazy {
        PopularAdapter(requireContext())
    }

    @Inject
    lateinit var shared:SharedPref

    override fun onViewCreate() {
        binding.listCategory.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.HORIZONTAL,false)
        binding.listPopular.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.HORIZONTAL,false)
        binding.listCategory.adapter = adapterCategory
        binding.listPopular.adapter = adapterPopular

        adapterCategory.set(setCategory())
        adapterPopular.set(setPopular())

        Glide.with(requireContext()).load(shared.getImage()).into(binding.image)
        binding.userName.text = "${shared.getName()} ${shared.getSurName()}"
        binding.phoneNumber.text = "${shared.getPhoneNumber()}"

        binding.userName.setOnClickListener {
            navController.navigate(R.id.action_defaultFragment_to_settingsFragment)
        }

        adapterCategory.setClickListener {description, price, image ->
            val s = CategoryDetailsFragment()
            val bundle = Bundle()
            bundle.putString("DESC", description.transitionName)
            Log.d("SSSSS", "onViewCreate: ${description.transitionName}")
            bundle.putString("PRICE", price.transitionName)
            bundle.putString("IMAGE", image.transitionName)
            s.arguments = bundle
            (view?.parent as? ViewGroup)?.doOnPreDraw {
                startPostponedEnterTransition()
            }
//            val extras = FragmentNavigatorExtras(description to description.transitionName, price to price.transitionName, image to image.transitionName)
            navController.navigate(R.id.action_defaultFragment_to_categoryDetailsFragment,bundle)
        }
        binding.order.setOnClickListener {
            navController.navigate(R.id.action_defaultFragment_to_logInFragment)
        }
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
}