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
import dark.composer.carpet.presentation.fragment.BaseFragment
import dark.composer.carpet.utils.SharedPref
import javax.inject.Inject

class DefaultFragment : BaseFragment<FragmentDefaultBinding>(FragmentDefaultBinding::inflate) {
    private val factoryAdapter by lazy {
        FactoryAdapter(requireContext())
    }

    lateinit var viewModel: DefaultViewModel

    private val adapterPopular by lazy {
        PopularAdapter(requireContext())
    }

    @Inject
    lateinit var shared: SharedPref

    override fun onViewCreate() {
        viewModel = ViewModelProvider(
            this,
            providerFactory
        )[DefaultViewModel::class.java]

        binding.listSale.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL, false
        )
        binding.listPopular.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL, false
        )
        binding.listSale.adapter = factoryAdapter
        binding.listPopular.adapter = adapterPopular

        viewModel.liveDataListPagination.observe(requireActivity()) {
            it?.let { t ->
                factoryAdapter.setListFactory(t.content)
            }
        }
        viewModel.getPagination(0, 10)

        adapterPopular.set(setPopular())

        adapterPopular.setClickListener {
            val dialog = AddDialog(requireContext())
            dialog.setStatus(true)
            dialog.setVisible(false)
            dialog.setTitle("Add Factory !!!")
            dialog.show()
        }

        val anim = AnimationUtils.loadAnimation(requireContext(), R.anim.move_left)
        binding.logIn.startAnimation(anim)

        binding.order.setOnClickListener {
            navController.navigate(R.id.action_defaultFragment_to_logInFragment)
//            successToast()
        }

        binding.logIn.setOnClickListener {
            navController.navigate(R.id.action_defaultFragment_to_logInFragment)
//            successToast()

            val inflater: LayoutInflater = layoutInflater
            val layout: View =
                inflater.inflate(
                    R.layout.custom_toast_yellow,
                    view?.findViewById(R.id.custom_toast_containerY)
                )
            var text: TextView? = view!!.findViewById(R.id.toastYellow)
            val message="Please register first!"
            text?.text = message
            var f = text?.text.toString()

            val toast = Toast(requireContext())
            toast.setGravity(Gravity.TOP, 0, 0)
            toast.duration = Toast.LENGTH_SHORT
            toast.view = layout
            toast.show()
        }

    }

    private fun successToast() {
        val inflater: LayoutInflater = layoutInflater
        val layout: View =
            inflater.inflate(
                R.layout.custom_toast_yellow,
                view?.findViewById(R.id.custom_toast_containerY)
            )
        val text: TextView? = view!!.findViewById(R.id.toastYellow)
        val message="Please register first!"
        text?.text = message
        var f = text?.text.toString()

        val toast = Toast(requireContext())
        toast.setGravity(Gravity.TOP, 0, 0)
        toast.duration = Toast.LENGTH_SHORT
        toast.view = layout
        toast.show()
    }

    private fun setPopular(): List<CategoryModel> {
        val list = mutableListOf<CategoryModel>()
        for (i in 0 until 10) {
            list.add(CategoryModel("Shu zor chiqibti", R.drawable.carpet, "1203369 cym"))
        }
        return list
    }
}