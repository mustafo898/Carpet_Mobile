package dark.composer.carpet.presentation.fragment.product.details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dark.composer.carpet.data.remote.models.request.basket.BasketCreateRequest
import dark.composer.carpet.data.remote.models.request.sale.SaleRequest
import dark.composer.carpet.data.remote.models.response.basket.BasketCreateResponse
import dark.composer.carpet.data.remote.models.response.product.ProductResponse
import dark.composer.carpet.data.remote.models.response.product.pagination.ProductPaginationResponse
import dark.composer.carpet.data.remote.models.response.sale.SaleResponse
import dark.composer.carpet.domain.use_case.basket.BasketUseCase
import dark.composer.carpet.domain.use_case.product.ProductUseCase
import dark.composer.carpet.domain.use_case.sale.SaleUseCase
import dark.composer.carpet.utils.BaseNetworkResult
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductDetailsViewModel @Inject constructor(
    private val useCase: ProductUseCase,
    private val basketUseCase: BasketUseCase,
    private val saleUseCase: SaleUseCase,
) : ViewModel() {

    private val _update = MutableSharedFlow<BaseNetworkResult<ProductResponse>>()
    val update = _update.asSharedFlow()

    private val _sale = MutableSharedFlow<BaseNetworkResult<SaleResponse>>()
    val sale = _sale.asSharedFlow()

    private val _product = MutableSharedFlow<BaseNetworkResult<ProductResponse>>()
    val product = _product.asSharedFlow()

    private val _create = MutableSharedFlow<BaseNetworkResult<BasketCreateResponse>>()
    val create = _create.asSharedFlow()

    private val _productList =
        MutableSharedFlow<BaseNetworkResult<List<ProductPaginationResponse>>>()
    val productList = _productList.asSharedFlow()

    fun getProduct(id: String, type: String) {
        viewModelScope.launch {
            useCase.getProduct(type, id).onEach { result ->
                when (result) {
                    is BaseNetworkResult.Error -> {
                        _product.emit(
                            BaseNetworkResult.Error(
                                result.message ?: "An unexpected error occurred"
                            )
                        )
                    }
                    is BaseNetworkResult.Loading -> {
                        _product.emit(BaseNetworkResult.Loading(result.isLoading))
                    }
                    is BaseNetworkResult.Success -> {
                        _product.emit(BaseNetworkResult.Success(result.data!!))
                    }
                }
            }.catch { t ->
                Log.d("OOOOOOO", "getProduct: ")
            }.launchIn(viewModelScope)
        }
    }

    fun saleCreate(saleRequest: SaleRequest) {
        viewModelScope.launch {
            saleUseCase.createSale(saleRequest).onEach { result ->
                when (result) {
                    is BaseNetworkResult.Error -> {
                        _sale.emit(
                            BaseNetworkResult.Error(
                                result.message ?: "An unexpected error occurred"
                            )
                        )
                    }
                    is BaseNetworkResult.Loading -> {
                        _sale.emit(BaseNetworkResult.Loading(result.isLoading))
                    }
                    is BaseNetworkResult.Success -> {
                        _sale.emit(BaseNetworkResult.Success(result.data!!))
                    }
                }
            }.catch { t ->
                Log.d("OOOOOOO", "getProduct: ")
            }.launchIn(viewModelScope)
        }
    }

    fun getProductList(type: String, page: Int, size: Int) {
        viewModelScope.launch {
            useCase.getProductList(type, page, size).onEach { result ->
                when (result) {
                    is BaseNetworkResult.Error -> {
                        _productList.emit(
                            BaseNetworkResult.Error(
                                result.message ?: "An unexpected error occurred"
                            )
                        )
                    }
                    is BaseNetworkResult.Loading -> {
                        _productList.emit(BaseNetworkResult.Loading(result.isLoading))
                    }
                    is BaseNetworkResult.Success -> {
                        _productList.emit(BaseNetworkResult.Success(result.data ?: emptyList()))
                    }
                }
            }.catch { t ->
                Log.d("OOOOOOO", "getProduct: ")
            }.launchIn(viewModelScope)
        }
    }

    fun deleteProduct(type: String, id: String) {
        viewModelScope.launch {
            useCase.deleteProduct(type, id).onEach { result ->
                when (result) {
                    is BaseNetworkResult.Error -> {
                        _product.emit(
                            BaseNetworkResult.Error(
                                result.message ?: "An unexpected error occurred"
                            )
                        )
                    }
                    is BaseNetworkResult.Loading -> {
                        _product.emit(BaseNetworkResult.Loading(result.isLoading))
                    }
                    is BaseNetworkResult.Success -> {
                        result.data?.let {
                            _product.emit(BaseNetworkResult.Success(it))
                        }
                    }
                }
            }.catch { t ->
                Log.d("OOOOOOO", "getProduct: ")
            }.launchIn(viewModelScope)
        }
    }

    fun createBasket(createResponse: BasketCreateRequest) {
        viewModelScope.launch {
            basketUseCase.createBasket(createResponse).onEach { result ->
                when (result) {
                    is BaseNetworkResult.Error -> {
                        _create.emit(
                            BaseNetworkResult.Error(
                                result.message ?: "An unexpected error occurred"
                            )
                        )
                    }
                    is BaseNetworkResult.Loading -> {
                        _create.emit(BaseNetworkResult.Loading(result.isLoading))
                    }
                    is BaseNetworkResult.Success -> {
                        result.data?.let {
                            _create.emit(BaseNetworkResult.Success(it))
                        }
                    }
                }
            }.catch { t ->
                Log.d("OOOOOOO", "getProduct: ")
            }.launchIn(viewModelScope)
        }
    }
}