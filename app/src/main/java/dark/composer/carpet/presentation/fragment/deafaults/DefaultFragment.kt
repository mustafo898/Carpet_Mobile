package dark.composer.carpet.presentation.fragment.deafaults

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dark.composer.carpet.R
import dark.composer.carpet.data.dto.CategoryModel
import dark.composer.carpet.databinding.FragmentDefaultBinding
import dark.composer.carpet.presentation.dialog.AddDialog
import dark.composer.carpet.presentation.dialog.CustomDialog
import dark.composer.carpet.presentation.fragment.BaseFragment
import dark.composer.carpet.presentation.fragment.admin.ViewPagerAdapter
import dark.composer.carpet.utils.SharedPref
import javax.inject.Inject

class DefaultFragment : BaseFragment<FragmentDefaultBinding>(FragmentDefaultBinding::inflate) {
    lateinit var viewModel: DefaultViewModel

    @Inject
    lateinit var shared: SharedPref

    override fun onViewCreate() {
        viewModel = ViewModelProvider(
            this,
            providerFactory
        )[DefaultViewModel::class.java]
        val t = 45
        print(t)
//        binding.listSale.layoutManager = LinearLayoutManager(
//            requireContext(),
//            LinearLayoutManager.HORIZONTAL, false
//        )
//
//        binding.listSale.adapter = factoryAdapter

//        viewModel.getPagination(0, 10)

        val pagerAdapter = ViewPagerAdapter(this)
        binding.viewPager.adapter = pagerAdapter
        binding.animateBar.setupWithViewPager2(binding.viewPager)


        val anim = AnimationUtils.loadAnimation(requireContext(), R.anim.move_left)
        binding.logIn.startAnimation(anim)

//        binding.logIn.setOnClickListener {
//            navController.navigate(R.id.action_defaultFragment_to_logInFragment)
//        }
//            successToast()

//            val inflater: LayoutInflater = layoutInflater
//            val layout: View =
//                inflater.inflate(
//                    R.layout.custom_toast_yellow,
//                    view?.findViewById(R.id.custom_toast_containerY)
//                )
//            var text: TextView? = view!!.findViewById(R.id.toastYellow)
//            val message="Please register first!"
//            text?.text = message
//            var f = text?.text.toString()

//            val toast = Toast(requireContext())
//            toast.setGravity(Gravity.TOP, 0, 0)
//            toast.duration = Toast.LENGTH_SHORT
//            toast.view = layout
//            toast.show()
    }
}
