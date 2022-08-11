package dark.composer.carpet.presentation.fragment.factory

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dark.composer.carpet.data.remote.models.request.filter.ProductFilterRequest
import dark.composer.carpet.data.remote.models.response.factory.FactoryResponse
import dark.composer.carpet.data.remote.models.response.factory.PaginationResponse
import dark.composer.carpet.data.remote.models.response.product.pagination.ProductPaginationResponse
import dark.composer.carpet.domain.use_case.factory.FactoryUseCase
import dark.composer.carpet.domain.use_case.product.ProductUseCase
import dark.composer.carpet.utils.BaseNetworkResult
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class FactoryViewModel @Inject constructor(
    private val factoryUseCase:FactoryUseCase,
    private val productUseCase:ProductUseCase
) : ViewModel() {

    private val _factory = MutableSharedFlow<BaseNetworkResult<List<FactoryResponse>>>()
    val factory = _factory.asSharedFlow()

    private val _filter = MutableSharedFlow<BaseNetworkResult<List<ProductPaginationResponse>>>()
    val filter = _filter.asSharedFlow()

    fun filterProduct(filterRequest: ProductFilterRequest) {
        viewModelScope.launch {
            productUseCase.filterProduct(filterRequest).onEach { result ->
                when(result){
                    is BaseNetworkResult.Error -> {
                        _filter.emit(BaseNetworkResult.Error(result.message?:"An unexpected error occurred"))
                    }
                    is BaseNetworkResult.Loading -> {
                        _filter.emit(BaseNetworkResult.Loading(result.isLoading))
                    }
                    is BaseNetworkResult.Success -> {
                        _filter.emit(BaseNetworkResult.Success(result.data?: emptyList()))
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

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