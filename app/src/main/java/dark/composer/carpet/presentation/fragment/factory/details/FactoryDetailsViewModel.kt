package dark.composer.carpet.presentation.fragment.factory.details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    fun getProductList(type: String, page: Int, size: Int) {
        viewModelScope.launch {
            productUseCase.getProductList(type, page, size).onEach { result ->
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
                Log.d("OOOOOOO", "getProductList: ")
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
}