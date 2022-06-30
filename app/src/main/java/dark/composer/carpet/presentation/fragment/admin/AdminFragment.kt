package dark.composer.carpet.presentation.fragment.admin

import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import dark.composer.carpet.R
import dark.composer.carpet.data.retrofit.models.request.factory.FactoryAddRequest
import dark.composer.carpet.databinding.FragmentAdminBinding
import dark.composer.carpet.presentation.fragment.BaseFragment
import dark.composer.carpet.utils.SharedPref
import javax.inject.Inject

class AdminFragment : BaseFragment<FragmentAdminBinding>(FragmentAdminBinding::inflate) {
    private val factoryAdapter by lazy {
        FactoryAdapter(requireContext())
    }
    @Inject
    lateinit var shared:SharedPref
    lateinit var viewModel: AdminViewModel

    override fun onViewCreate() {
        viewModel = ViewModelProvider(
            this,
            providerFactory
        )[AdminViewModel::class.java]

        binding.listSale.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL,false)
        binding.listSale.adapter = factoryAdapter

        binding.txtCustomers.isSelected = true
        binding.txtFactory.isSelected = true
        binding.txtEmployee.isSelected = true

        Glide.with(requireContext()).load(shared.getImage()).into(binding.image)
        binding.userName.text = "${shared.getName()} ${shared.getSurName()}"
        binding.phoneNumber.text = "${shared.getPhoneNumber()}"

        binding.addFactory.setOnClickListener {
            viewModel.addFactory(FactoryAddRequest(name = "Hello"))
        }

        viewModel.liveDataAddFactory.observe(requireActivity()){
            it?.let {t->
                factoryAdapter.addFactory(t)
            }
        }

        viewModel.liveDataListPagination.observe(requireActivity()){
            it?.let {t->
                factoryAdapter.setListFactory(t.content)
            }
        }

        viewModel.getPagination(0,10)

        binding.imageMore.setOnClickListener {
            navController.navigate(R.id.action_adminFragment_to_settingsFragment)
        }

        binding.image.setOnClickListener {
            navController.navigate(R.id.action_adminFragment_to_settingsFragment)
        }

        factoryAdapter.setClickListener {
            navController.navigate(R.id.action_adminFragment_to_factoryDetailsFragment, bundleOf("ID" to it))
        }

    }

//    private fun getAllNewMovies() {
//        viewModel.movieRepository.getAllNewMovies(requireContext()).observe(this) { result ->
//            val imageList = ArrayList<SlideModel>() // Create image list
//            val positions = ArrayList<Long>() // Create image list
//            result?.results?.forEach { movies ->
//                positions.add(movies.id)
//                imageList.add(
//                    SlideModel(
//                        "${Constants.BASE_IMAGE_URL}${movies.backdropPath}",
//                        movies.originalTitle
//                    )
//                )
//            }
//            binding.imageSlider.setImageList(imageList, ScaleTypes.FIT)
//            binding.imageSlider.setItemClickListener(object : ItemClickListener {
//                override fun onItemSelected(position: Int) {
//                    val bundle = bundleOf("MOVIE_ID" to positions[position])
//                    navController.navigate(R.id.action_mainFragment_to_movieDetailFragment, bundle)
//                }
//            })
//        }
//    }

    override fun onStop() {
        binding.imageSlider.stopSliding()
        super.onStop()
    }
}