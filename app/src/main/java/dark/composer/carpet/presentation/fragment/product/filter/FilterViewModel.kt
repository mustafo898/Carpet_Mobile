package dark.composer.carpet.presentation.fragment.product.filter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dark.composer.carpet.R
import dark.composer.carpet.data.remote.models.response.factory.FactoryResponse
import dark.composer.carpet.databinding.FilterSheetBinding
import dark.composer.carpet.domain.use_case.factory.FactoryUseCase
import dark.composer.carpet.presentation.fragment.BaseFragment
import dark.composer.carpet.utils.BaseNetworkResult
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class FilterViewModel @Inject constructor(
    private val factoryUseCase: FactoryUseCase
) : ViewModel(){
    private val _factory = MutableSharedFlow<BaseNetworkResult<List<FactoryResponse>>>()
    val factory = _factory.asSharedFlow()

    fun getFactoryList(page: Int, size: Int) {
        viewModelScope.launch {
            factoryUseCase.getFactoryList(page, size).onEach { result ->
                when(result){
                    is BaseNetworkResult.Error -> {
                        _factory.emit(BaseNetworkResult.Error(result.message?:"An unexpected error occurred"))
                    }
                    is BaseNetworkResult.Loading -> {
                        _factory.emit(BaseNetworkResult.Loading(result.isLoading))
                    }
                    is BaseNetworkResult.Success -> {
                        _factory.emit(BaseNetworkResult.Success(result.data?.content?: emptyList()))
                    }
                }
            }.launchIn(viewModelScope)
        }
    }
}