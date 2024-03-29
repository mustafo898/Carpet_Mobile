package dark.composer.carpet.presentation.fragment.factory.details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dark.composer.carpet.data.remote.models.request.factory.update.FactoryUpdateRequest
import dark.composer.carpet.data.remote.models.request.filter.ProductFilterRequest
import dark.composer.carpet.data.remote.models.response.factory.FactoryResponse
import dark.composer.carpet.data.remote.models.response.product.pagination.ProductPaginationResponse
import dark.composer.carpet.domain.use_case.factory.FactoryUseCase
import dark.composer.carpet.domain.use_case.product.ProductUseCase
import dark.composer.carpet.utils.BaseNetworkResult
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class FactoryDetailsViewModel @Inject constructor(private val productUseCase: ProductUseCase, private val factoryUseCase: FactoryUseCase) : ViewModel() {
    private val _factory = MutableSharedFlow<BaseNetworkResult<FactoryResponse>>()
    val factory = _factory.asSharedFlow()

    private val _productList = MutableSharedFlow<BaseNetworkResult<List<ProductPaginationResponse>>>()
    val productList = _productList.asSharedFlow()

    fun getFilterProductList(filterRequest: ProductFilterRequest) {
        viewModelScope.launch {
            productUseCase.filterProduct(filterRequest).onEach { result ->
                when(result){
                    is BaseNetworkResult.Error -> {
                        _productList.emit(BaseNetworkResult.Error(result.message ?: "An unexpected error occurred"))
                    }
                    is BaseNetworkResult.Loading -> {
                        _productList.emit(BaseNetworkResult.Loading(result.isLoading))
                    }
                    is BaseNetworkResult.Success -> {
                        _productList.emit(BaseNetworkResult.Success(result.data?: emptyList()))
                    }
                }
            }.catch {t->
                Log.d("OOOOOOO", "getProductList: ${t.message}")
            }.launchIn(viewModelScope)
        }
    }

    fun getFactory(id:Int) {
        viewModelScope.launch {
            factoryUseCase.getFactory(id).onEach { result ->
                when(result){
                    is BaseNetworkResult.Error -> {
                        _factory.emit(BaseNetworkResult.Error(result.message ?: "An unexpected error occurred"))
                    }
                    is BaseNetworkResult.Loading -> {
                        _factory.emit(BaseNetworkResult.Loading(result.isLoading))
                    }
                    is BaseNetworkResult.Success -> {
                        _factory.emit(BaseNetworkResult.Success(result.data!!))
                    }
                }
            }.catch {t->
                Log.d("OOOOOOO", "getProductList: ")
            }.launchIn(viewModelScope)
        }
    }

    fun deleteFactory(id:Int) {
        viewModelScope.launch {
            factoryUseCase.deleteFactory(id).onEach { result ->
                when(result){
                    is BaseNetworkResult.Error -> {
                        _factory.emit(BaseNetworkResult.Error(result.message ?: "An unexpected error occurred"))
                    }
                    is BaseNetworkResult.Loading -> {
                        _factory.emit(BaseNetworkResult.Loading(result.isLoading))
                    }
                    is BaseNetworkResult.Success -> {
                        _factory.emit(BaseNetworkResult.Success(result.data!!))
                    }
                }
            }.catch {t->
                Log.d("OOOOOOO", "getProductList: ")
            }.launchIn(viewModelScope)
        }
    }
}