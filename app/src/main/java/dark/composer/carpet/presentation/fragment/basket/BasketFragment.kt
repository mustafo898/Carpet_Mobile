package dark.composer.carpet.presentation.fragment.basket

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStarted
import dark.composer.carpet.data.remote.models.request.basket.BasketUpdateRequest
import dark.composer.carpet.databinding.FragmentBasketBinding
import dark.composer.carpet.presentation.fragment.BaseFragment
import dark.composer.carpet.presentation.fragment.adapters.BasketAdapter
import dark.composer.carpet.presentation.fragment.dialog.MenuBasket
import dark.composer.carpet.presentation.fragment.dialog.UpdateBasket
import dark.composer.carpet.utils.BaseNetworkResult
import kotlinx.coroutines.launch
import javax.inject.Inject


class BasketFragment : BaseFragment<FragmentBasketBinding>(FragmentBasketBinding::inflate) {
    @Inject
    lateinit var viewModel: BasketViewModel
    private val adapter by lazy {
        BasketAdapter()
    }

    private lateinit var menuBasket: MenuBasket
    private val dialog by lazy {
        UpdateBasket(requireContext())
    }

    override fun onViewCreate() {
        viewModel = ViewModelProvider(
            requireActivity(), providerFactory
        )[BasketViewModel::class.java]

        binding.list.adapter = adapter

        observe()
        send()
    }

    private fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.whenStarted {
                viewModel.listBasket.collect {
                    when (it) {
                        is BaseNetworkResult.Error ->
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                        is BaseNetworkResult.Loading -> Toast.makeText(
                            requireContext(),
                            "Loading...",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        is BaseNetworkResult.Success -> {
                            it.data?.let { t ->
                                Log.d("RRRR", "observe: $t")
                                adapter.setList(t)
                            }
                        }
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.whenStarted {
                viewModel.update.collect {
                    when (it) {
                        is BaseNetworkResult.Error ->
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                        is BaseNetworkResult.Loading -> Toast.makeText(
                            requireContext(),
                            "Loading...",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        is BaseNetworkResult.Success -> {
                            dialog.dismiss()
                            Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.whenStarted {
                viewModel.getBasket.collect {
                    when (it) {
                        is BaseNetworkResult.Error ->
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                        is BaseNetworkResult.Loading -> Toast.makeText(
                            requireContext(),
                            "Loading...",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        is BaseNetworkResult.Success -> {
                            it.data?.let { t ->
                                dialog.setAmount(t.amount)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun send() {
//        adapter.setBuyClickListener { id, amount ->
//            viewModel.updateBasket(BasketUpdateRequest(basketId = id, price = 16.0, amount = amount))
////            viewModel.getListBasket("GIVEN",0,20)
//        }

//        adapter.setRemoveClickListener { id, amount ->
//            viewModel.updateBasket(BasketUpdateRequest(basketId = id, price = 16.0, amount = amount))
////            viewModel.getListBasket("GIVEN",0,20)
//        }

//        adapter.setMoreClickListener {
//            viewModel.deleteBasket(it)
//            viewModel.getListBasket("GIVEN",0,20)
//        }
        var basketId = 0
        dialog.setOnUpdateListener { amount ->
            viewModel.updateBasket(
                BasketUpdateRequest(
                    basketId = basketId,
                    price = 16.0,
                    amount = amount
                )
            )
        }

        adapter.setMoreClickListener {it,id->
            menuBasket = MenuBasket(requireContext(), it)
            menuBasket.show()
            basketId = id
            viewModel.getBasket(basketId)
            menuBasket.setUpdateClickListener {
                dialog.show()
            }
        }

        viewModel.getListBasket("GIVEN", 0, 20)
    }
}