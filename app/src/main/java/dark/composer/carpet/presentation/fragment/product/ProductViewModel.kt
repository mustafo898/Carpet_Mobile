package dark.composer.carpet.presentation.fragment.product

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dark.composer.carpet.data.remote.models.request.filter.ProductFilterRequest
import dark.composer.carpet.data.remote.models.response.product.pagination.ProductPaginationResponse
import dark.composer.carpet.domain.use_case.product.ProductUseCase
import dark.composer.carpet.utils.BaseNetworkResult
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductViewModel @Inject constructor(private val useCase: ProductUseCase) : ViewModel() {
    private val _productList = MutableSharedFlow<BaseNetworkResult<List<ProductPaginationResponse>>>()
    val productList = _productList.asSharedFlow()

    private val _filter = MutableSharedFlow<BaseNetworkResult<List<ProductPaginationResponse>>>()
    val filter = _filter.asSharedFlow()

    fun getProductList(type: String, page: Int, size: Int) {
        viewModelScope.launch {
            useCase.getProductList(type, page, size).onEach { result ->
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
                Log.d("Mistake", "getProductList: ${result.message}")
            }.launchIn(viewModelScope)
        }
    }

    fun filterProduct(filterRequest: ProductFilterRequest) {
        viewModelScope.launch {
            useCase.filterProduct(filterRequest).onEach { result ->
                when(result){
                    is BaseNetworkResult.Error -> {
                        _filter.emit(BaseNetworkResult.Error(result.message ?: "An unexpected error occurred"))
                    }
                    is BaseNetworkResult.Loading -> {
                        _filter.emit(BaseNetworkResult.Loading(result.isLoading))
                    }
                    is BaseNetworkResult.Success -> {
                        _filter.emit(BaseNetworkResult.Success(result.data?: emptyList()))
                    }
                }
                Log.d("Mistake", "filterProduct: ${result.message}")
            }.launchIn(viewModelScope)
        }
    }
}