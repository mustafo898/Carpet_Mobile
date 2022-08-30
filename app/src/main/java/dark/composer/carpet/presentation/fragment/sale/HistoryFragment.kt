package dark.composer.carpet.presentation.fragment.sale

import android.app.AlertDialog
import android.app.DatePickerDialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStarted
import com.bumptech.glide.Glide
import dark.composer.carpet.R
import dark.composer.carpet.data.remote.models.request.sale.SaleCreateDateRequest
import dark.composer.carpet.data.remote.models.request.sale.SaleUpdateRequest
import dark.composer.carpet.databinding.FragmentHistoryBinding
import dark.composer.carpet.presentation.fragment.BaseFragment
import dark.composer.carpet.presentation.fragment.adapters.HistoryAdapter
import dark.composer.carpet.presentation.fragment.adapters.HistoryAdapter1
import dark.composer.carpet.presentation.fragment.adapters.ProductAdapter
import dark.composer.carpet.presentation.fragment.dialog.MenuType
import dark.composer.carpet.presentation.fragment.factory.details.FactoryDetailsViewModel
import dark.composer.carpet.presentation.fragment.profile.ProfileViewModel
import dark.composer.carpet.utils.BaseNetworkResult
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*
import javax.inject.Inject

class HistoryFragment : BaseFragment<FragmentHistoryBinding>(FragmentHistoryBinding::inflate) {
    @Inject
    lateinit var viewModel: HistoryViewModel
    private val historyAdapter by lazy {
        HistoryAdapter()
    }

    private val historyAdapter1 by lazy {
        HistoryAdapter1()
    }

    var type = "COUNTABLE"

    private val menuType by lazy {
        MenuType(requireContext(), binding.type)
    }

    private val calendar = Calendar.getInstance()
    private val year = calendar.get(Calendar.YEAR)
    private val month = calendar.get(Calendar.MONTH)
    private val day = calendar.get(Calendar.DAY_OF_MONTH)
    var t = "$year-${month+1}-$day"

    override fun onViewCreate() {
        viewModel = ViewModelProvider(
            requireActivity(), providerFactory
        )[HistoryViewModel::class.java]

        binding.list.adapter = historyAdapter1

        observe()
        send()
    }

    private fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.whenStarted {
                viewModel.list.collect {
                    when (it) {
                        is BaseNetworkResult.Error -> {
                            Toast.makeText(requireContext(), "${it.message}", Toast.LENGTH_SHORT)
                                .show()
                        }
                        is BaseNetworkResult.Loading -> {
                            Toast.makeText(requireContext(), "Loading..", Toast.LENGTH_SHORT).show()
                        }
                        is BaseNetworkResult.Success -> {
                            it.data?.let { t ->
                                Log.d("history", "history list: $t")
                                historyAdapter.setList(t)
                            }
                        }
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.whenStarted {
                viewModel.listByCreateDate.collect {
                    when (it) {
                        is BaseNetworkResult.Error -> {
                            Toast.makeText(requireContext(), "${it.message}", Toast.LENGTH_SHORT)
                                .show()
                        }
                        is BaseNetworkResult.Loading -> {
                            Toast.makeText(requireContext(), "Loading..", Toast.LENGTH_SHORT).show()
                        }
                        is BaseNetworkResult.Success -> {
                            it.data?.let { t ->
                                Log.d("history", "history list: $t")
                                historyAdapter1.setList(t)
                            }
                        }
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.whenStarted {
                viewModel.delete.collect {
                    when (it) {
                        is BaseNetworkResult.Error -> {
                            Toast.makeText(requireContext(), "${it.message}", Toast.LENGTH_SHORT)
                                .show()
                        }
                        is BaseNetworkResult.Loading -> {
                            Toast.makeText(requireContext(), "Loading..", Toast.LENGTH_SHORT).show()
                        }
                        is BaseNetworkResult.Success -> {
                            it.data?.let { t ->
                                Toast.makeText(
                                    requireContext(),
                                    "Success deleted",
                                    Toast.LENGTH_SHORT
                                ).show()
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
                        is BaseNetworkResult.Error -> {
                            Toast.makeText(requireContext(), "${it.message}", Toast.LENGTH_SHORT)
                                .show()
                        }
                        is BaseNetworkResult.Loading -> {
                            Toast.makeText(requireContext(), "Loading..", Toast.LENGTH_SHORT).show()
                        }
                        is BaseNetworkResult.Success -> {
                            it.data?.let { t ->
                                Toast.makeText(
                                    requireContext(),
                                    "Success deleted",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun send() {
        historyAdapter.setMoreClickListener { id, type ->
            viewModel.deleteSale(id = id, type = type)
//            viewModel.updateSale(
//                id = id,
//                type = type,
//                updateRequest = SaleUpdateRequest(price = 30.0, amount = 2)
//            )
        }

        menuType.setUncountableClickListener {
            type = "UNCOUNTABLE"
        }

        binding.type.setOnClickListener {
            menuType.show()
        }

        binding.back.setOnClickListener {
            navController.popBackStack()
        }

        menuType.setCountableClickListener {
            type = "COUNTABLE"
        }

//        viewModel.getList(page = 0, size = 20, type = type)
        binding.datePicker.text = t

        binding.datePicker.setOnClickListener {
            date()
            binding.datePicker.text = t
        }
        viewModel.getListByCreateDate(SaleCreateDateRequest(t))
    }

    private fun date() {
        val style = AlertDialog.THEME_HOLO_LIGHT
        val picker = DatePickerDialog(
            requireContext(), style
        )
        picker.setOnDateSetListener { view, mYear, mMonth, dayOfMonth ->
            t = "$mYear-${mMonth + 1}-$dayOfMonth"
            Log.d("History", "date : ${"$mYear-${mMonth + 1}-$dayOfMonth"}")
            viewModel.getListByCreateDate(SaleCreateDateRequest(t))
        }
        picker.show()
    }
}