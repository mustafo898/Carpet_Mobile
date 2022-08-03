package dark.composer.carpet.presentation.fragment.basket

import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStarted
import androidx.recyclerview.widget.LinearLayoutManager
import dark.composer.carpet.data.retrofit.models.request.basket.BasketUpdateRequest
import dark.composer.carpet.data.retrofit.models.response.basket.BasketPaginationResponse
import dark.composer.carpet.databinding.FragmentBasketBinding
import dark.composer.carpet.presentation.fragment.BaseFragment
import dark.composer.carpet.presentation.fragment.basket.dialog.UpdateBottomSheetDialog
import kotlinx.coroutines.launch
import javax.inject.Inject

class BasketFragment : BaseFragment<FragmentBasketBinding>(FragmentBasketBinding::inflate) {
    @Inject
    lateinit var viewModel: BasketViewModel
    private val basketAdapter by lazy {
        BasketAdapter()
    }

    private val bottomSheetDialog by lazy {
        UpdateBottomSheetDialog(requireContext())
    }

    override fun onViewCreate() {
        viewModel = ViewModelProvider(this, providerFactory)[BasketViewModel::class.java]
        binding.list.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.list.adapter = basketAdapter
        binding.list.showShimmerAdapter()

        viewModel.basketPagination.observe(viewLifecycleOwner) {
            binding.list.hideShimmerAdapter()
            Toast.makeText(requireContext(), "Hide", Toast.LENGTH_SHORT).show()
            basketAdapter.setList(it)
            Toast.makeText(requireContext(), "$it", Toast.LENGTH_SHORT).show()
        }

        viewModel.basketFlow.observe(viewLifecycleOwner) {
            bottomSheetDialog.dismiss()
            Toast.makeText(requireContext(), "Success Updated to Basket", Toast.LENGTH_SHORT)
                .show()
        }

        viewModel.basketFlow.observe(viewLifecycleOwner){
            bottomSheetDialog.amount = it.amount
        }

        basketAdapter.setEditClickListener {
            viewModel.getByIdBasket(it)
            bottomSheetDialog.setOnUpdateListener { amount, price, height ->
                viewModel.updateBasket(BasketUpdateRequest(it, "GIVEN", price, amount, height))
            }
            bottomSheetDialog.show()
        }

        viewModel.getPaginationBasket("GIVEN", 0, 20, requireContext())
    }
}