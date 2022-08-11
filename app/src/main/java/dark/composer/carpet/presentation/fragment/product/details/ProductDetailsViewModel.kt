package dark.composer.carpet.presentation.fragment.product.details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dark.composer.carpet.data.remote.models.response.basket.BasketCreateResponse
import dark.composer.carpet.data.remote.models.response.product.ProductResponse
import dark.composer.carpet.data.remote.models.response.product.pagination.ProductPaginationResponse
import dark.composer.carpet.domain.use_case.basket.BasketUseCase
import dark.composer.carpet.domain.use_case.product.ProductUseCase
import dark.composer.carpet.utils.BaseNetworkResult
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductDetailsViewModel @Inject constructor(
    private val useCase: ProductUseCase,
    private val useCaseBasket: BasketUseCase
) : ViewModel() {
    private val _update = MutableSharedFlow<BaseNetworkResult<ProductResponse>>()
    val update = _update.asSharedFlow()

    private val _product = MutableSharedFlow<BaseNetworkResult<ProductResponse>>()
    val product = _product.asSharedFlow()

    private val _delete = MutableSharedFlow<BaseNetworkResult<ProductResponse>>()
    val delete = _delete.asSharedFlow()

    private val _create = MutableSharedFlow<BaseNetworkResult<BasketCreateResponse>>()
    val create = _create.asSharedFlow()

    private val _productList =
        MutableSharedFlow<BaseNetworkResult<List<ProductPaginationResponse>>>()
    val productList = _productList.asSharedFlow()

    fun getProduct(id: String, type: String) {
        viewModelScope.launch {
            useCase.getProduct(type, id).onEach { result ->
                when(result){
                    is BaseNetworkResult.Error -> {
                        _product.emit(BaseNetworkResult.Error(result.message ?: "An unexpected error occurred"))
                    }
                    is BaseNetworkResult.Loading -> {
                        _product.emit(BaseNetworkResult.Loading(result.isLoading))
                    }
                    is BaseNetworkResult.Success -> {
                        _product.emit(BaseNetworkResult.Success(result.data!!))
                    }
                }
            }.catch {t->
                Log.d("OOOOOOO", "getProduct: ")
            }.launchIn(viewModelScope)
        }
    }

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
            }.catch {t->
                Log.d("OOOOOOO", "getProduct: ")
            }.launchIn(viewModelScope)
        }
    }

//    fun updateProduct(type:String,id: String,update: ProductCreateRequest) {
//        viewModelScope.launch {
//            useCase.updateProduct(type,update,id).onEach { result ->
//                _update.emit(BaseNetworkResult.loading(null))
//                _update.emit(BaseNetworkResult.success(result.data))
//            }.catch { t ->
//                _update.emit(
//                    Resource.error(
//                        msg = t.message ?: "An unexpected error occured",
//                        null
//                    )
//                )
//                Log.d("Mistake", "updateProduct: ${t.message}")
//            }.launchIn(viewModelScope)
//        }
//    }
//
//    fun deleteProduct(type: String,id: String) {
//        viewModelScope.launch {
//            useCase.deleteProduct(type,id).onEach { result ->
//                _delete.emit(Resource.loading(null))
//                _delete.emit(Resource.success(result.data))
//            }.catch { t ->
//                _delete.emit(
//                    Resource.error(
//                        msg = t.message ?: "An unexpected error occured",
//                        null
//                    )
//                )
//                Log.d("Mistake", "deleteProduct: ${t.message}")
//            }.launchIn(viewModelScope)
//        }
//    }
//
//    fun createBasket(create:BasketCreateRequest) {
//        viewModelScope.launch {
//            useCaseBasket.createBasket(create).onEach { result ->
//                _create.emit(Resource.loading(null))
//                _create.emit(Resource.success(result.data))
//            }.catch { t ->
//                _create.emit(
//                    Resource.error(
//                        msg = t.message ?: "An unexpected error occured",
//                        null
//                    )
//                )
//                Log.d("Mistake", "deleteProduct: ${t.message}")
//            }.launchIn(viewModelScope)
//        }
//    }
}