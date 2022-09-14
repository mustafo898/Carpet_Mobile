package dark.composer.carpet.presentation.fragment.basket

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dark.composer.carpet.data.remote.models.request.basket.BasketUpdateRequest
import dark.composer.carpet.data.remote.models.request.sale.SaleRequest
import dark.composer.carpet.data.remote.models.response.basket.BasketCreateResponse
import dark.composer.carpet.data.remote.models.response.basket.BasketPaginationResponse
import dark.composer.carpet.data.remote.models.response.basket.DeleteResponse
import dark.composer.carpet.data.remote.models.response.sale.SaleResponse
import dark.composer.carpet.domain.use_case.basket.BasketUseCase
import dark.composer.carpet.domain.use_case.sale.SaleUseCase
import dark.composer.carpet.utils.BaseNetworkResult
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class BasketViewModel @Inject constructor(
    private val basketUseCase: BasketUseCase,
    private val saleUseCase: SaleUseCase,
) : ViewModel() {

    private val _update = MutableSharedFlow<BaseNetworkResult<BasketCreateResponse>>()
    val update = _update.asSharedFlow()

    private val _getBasket = MutableSharedFlow<BaseNetworkResult<BasketCreateResponse>>()
    val getBasket = _getBasket.asSharedFlow()

    private val _delete = MutableSharedFlow<BaseNetworkResult<DeleteResponse>>()
    val delete = _delete.asSharedFlow()

    private val _listBasket = MutableSharedFlow<BaseNetworkResult<List<BasketPaginationResponse>>>()
    val listBasket = _listBasket.asSharedFlow()

    private val _sale = MutableSharedFlow<BaseNetworkResult<SaleResponse>>()
    val sale = _sale.asSharedFlow()

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

    fun updateBasket(updateBasket: BasketUpdateRequest) {
        viewModelScope.launch {
            basketUseCase.updateBasket(updateBasket).onEach { result ->
                when (result) {
                    is BaseNetworkResult.Error -> {
                        _update.emit(
                            BaseNetworkResult.Error(
                                result.message ?: "An unexpected error occurred"
                            )
                        )
                    }
                    is BaseNetworkResult.Loading -> {
                        _update.emit(BaseNetworkResult.Loading(result.isLoading))
                    }
                    is BaseNetworkResult.Success -> {
                        result.data?.let {
                            _update.emit(BaseNetworkResult.Success(it))
                        }
                    }
                }
            }.catch { t ->
                Log.d("OOOOOOO", "getProduct: ")
            }.launchIn(viewModelScope)
        }
    }

    fun deleteBasket(id: Int) {
        viewModelScope.launch {
            basketUseCase.deleteBasket(id).onEach { result ->
                when (result) {
                    is BaseNetworkResult.Error -> {
                        _delete.emit(
                            BaseNetworkResult.Error(
                                result.message ?: "An unexpected error occurred"
                            )
                        )
                    }
                    is BaseNetworkResult.Loading -> {
                        _delete.emit(BaseNetworkResult.Loading(result.isLoading))
                    }
                    is BaseNetworkResult.Success -> {
                        result.data?.let {
                            _delete.emit(BaseNetworkResult.Success(it))
                        }
                    }
                }
            }.catch { t ->
                Log.d("OOOOOOO", "getProduct: ")
            }.launchIn(viewModelScope)
        }
    }

    fun getListBasket(status: String, page: Int, size: Int) {
        viewModelScope.launch {
            basketUseCase.getBasketList(status, page, size).onEach { result ->
                when (result) {
                    is BaseNetworkResult.Error -> {
                        _listBasket.emit(
                            BaseNetworkResult.Error(
                                result.message ?: "An unexpected error occurred"
                            )
                        )
                    }
                    is BaseNetworkResult.Loading -> {
                        _listBasket.emit(BaseNetworkResult.Loading(result.isLoading))
                    }
                    is BaseNetworkResult.Success -> {
                        result.data?.let {
                            _listBasket.emit(BaseNetworkResult.Success(it))
                        }
                    }
                }
            }.catch { t ->
                Log.d("OOOOOOO", "getProduct: ")
            }.launchIn(viewModelScope)
        }
    }

    fun getBasket(id: Int) {
        viewModelScope.launch {
            basketUseCase.getBasket(id).onEach { result ->
                when (result) {
                    is BaseNetworkResult.Error -> {
                        _getBasket.emit(
                            BaseNetworkResult.Error(
                                result.message ?: "An unexpected error occurred"
                            )
                        )
                    }
                    is BaseNetworkResult.Loading -> {
                        _getBasket.emit(BaseNetworkResult.Loading(result.isLoading))
                    }
                    is BaseNetworkResult.Success -> {
                        result.data?.let {
                            _getBasket.emit(BaseNetworkResult.Success(it))
                        }
                    }
                }
            }.catch { t ->
                Log.d("OOOOOOO", "getProduct: ")
            }.launchIn(viewModelScope)
        }
    }
}