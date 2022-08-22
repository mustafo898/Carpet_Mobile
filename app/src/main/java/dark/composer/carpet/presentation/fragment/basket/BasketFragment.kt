package dark.composer.carpet.presentation.fragment.basket

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStarted
import dark.composer.carpet.R
import dark.composer.carpet.data.remote.models.request.basket.BasketUpdateRequest
import dark.composer.carpet.databinding.FragmentBasketBinding
import dark.composer.carpet.presentation.fragment.BaseFragment
import dark.composer.carpet.presentation.fragment.adapters.BasketAdapter
import dark.composer.carpet.presentation.fragment.product.details.ProductDetailsViewModel
import dark.composer.carpet.utils.BaseNetworkResult
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import okhttp3.internal.assertThreadDoesntHoldLock
import javax.inject.Inject


class BasketFragment : BaseFragment<FragmentBasketBinding>(FragmentBasketBinding::inflate) {
    @Inject
    lateinit var viewModel: BasketViewModel
    private val adapter by lazy {
        BasketAdapter()
    }
    override fun onViewCreate() {
        viewModel = ViewModelProvider(
            requireActivity(), providerFactory
        )[BasketViewModel::class.java]

        binding.list.adapter = adapter

        observe()
        send()
    }

    private fun observe(){
        viewLifecycleOwner.lifecycleScope.launch{
            viewLifecycleOwner.lifecycle.whenStarted {
                viewModel.listBasket.collect{
                    when(it){
                        is BaseNetworkResult.Error ->
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                        is BaseNetworkResult.Loading -> Toast.makeText(requireContext(), "Loading...", Toast.LENGTH_SHORT)
                            .show()
                        is BaseNetworkResult.Success -> {
                            it.data?.let {t->
                                adapter.setList(t)
                            }
                        }
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch{
            viewLifecycleOwner.lifecycle.whenStarted {
                viewModel.update.collect{
                    when(it){
                        is BaseNetworkResult.Error ->
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                        is BaseNetworkResult.Loading -> Toast.makeText(requireContext(), "Loading...", Toast.LENGTH_SHORT)
                            .show()
                        is BaseNetworkResult.Success -> {

                        }
                    }
                }
            }
        }
    }

    private fun send(){
        adapter.setAddClickListener { id, amount ->
            viewModel.updateBasket(BasketUpdateRequest(basketId = id, price = 16.0, amount = amount))
//            viewModel.getListBasket("GIVEN",0,20)
        }

        adapter.setRemoveClickListener { id, amount ->
            viewModel.updateBasket(BasketUpdateRequest(basketId = id, price = 16.0, amount = amount))
//            viewModel.getListBasket("GIVEN",0,20)
        }

        adapter.setDeleteClickListener {
            viewModel.deleteBasket(it)
            viewModel.getListBasket("GIVEN",0,20)
        }

        viewModel.getListBasket("GIVEN",0,20)
    }
}