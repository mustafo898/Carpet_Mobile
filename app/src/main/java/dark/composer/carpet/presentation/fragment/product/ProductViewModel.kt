package dark.composer.carpet.presentation.fragment.product

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dark.composer.carpet.data.repositories.BasketRepository
import dark.composer.carpet.data.repositories.ProductRepository
import dark.composer.carpet.data.retrofit.models.BaseNetworkResult
import dark.composer.carpet.data.retrofit.models.request.basket.BasketCreateRequest
import dark.composer.carpet.data.retrofit.models.response.basket.BasketCreateResponse
import dark.composer.carpet.data.retrofit.models.response.product.ProductResponse
import dark.composer.carpet.data.retrofit.models.response.product.pagination.ProductPaginationResponse
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductViewModel @Inject constructor(
    private val repo: ProductRepository,
    private val repoBasket: BasketRepository
) : ViewModel() {
    private val product = MutableLiveData<ProductResponse?>()
    val productLiveData: MutableLiveData<ProductResponse?> = product

    private val listPagination = MutableLiveData<List<ProductPaginationResponse>?>()
    val liveDataListPagination: MutableLiveData<List<ProductPaginationResponse>?> = listPagination

    private val basketChannel = MutableLiveData<BasketCreateResponse>()
    val basketFlow:LiveData<BasketCreateResponse> = basketChannel

    private val listUncountablePagination = MutableLiveData<List<ProductPaginationResponse>?>()
    val liveDataListUncountablePagination: MutableLiveData<List<ProductPaginationResponse>?> =
        listUncountablePagination

    private val _errorChannel = Channel<String?>()
    val errorFlow = _errorChannel.receiveAsFlow()

    private val _loadingChannel = Channel<Boolean?>()
    val loadingFlow = _loadingChannel.receiveAsFlow()

    fun createBasket(createRequest: BasketCreateRequest) {
        viewModelScope.launch {
            repoBasket.createBasket(createRequest).observeForever {
                when (it) {
                    is BaseNetworkResult.Success -> {
                        basketChannel.value = it.data
                    }
                    is BaseNetworkResult.Loading -> {
                        viewModelScope.launch {
                            _loadingChannel.send(it.isLoading)
                        }
                    }
                    is BaseNetworkResult.Error -> {
                        viewModelScope.launch {
                            _errorChannel.send(it.message)
                        }
                    }
                }
            }
        }
    }

    fun getCountPagination(page: Int, size: Int, count: String) {
        val list = mutableListOf<ProductPaginationResponse>()
        viewModelScope.launch {
            repo.getPagination(page, size, count).observeForever {
                when (it) {
                    is BaseNetworkResult.Success -> {
                        it.data?.let { t ->
//                            list.addAll(t)
                            listPagination.value = t
                        }
                        Log.d("EEEEE", "countable: ${it.data}")
                    }
                    is BaseNetworkResult.Error -> {
                        viewModelScope.launch {
                            _errorChannel.send(it.message)
                        }
                    }
                    is BaseNetworkResult.Loading -> {
                        viewModelScope.launch {
                            _loadingChannel.send(it.isLoading)
                        }
                    }
                    else -> {
                        Log.d("Admin", "getPagination: Kemadi")
                    }
                }
            }
        }
    }

    fun getUncountablePagination(page: Int, size: Int, uncountable: String) {
        viewModelScope.launch {
            repo.getPagination(page, size, uncountable).observeForever {
                when (it) {
                    is BaseNetworkResult.Success -> {
                        it.data?.let { t ->
//                            list.addAll(t)
                            listUncountablePagination.value = t
                        }
                        Log.d("EEEEE", "uncountable: ${it.data}")
                    }
                    is BaseNetworkResult.Error -> {
                        viewModelScope.launch {
                            _errorChannel.send(it.message)
                        }
                    }
                    is BaseNetworkResult.Loading -> {
                        viewModelScope.launch {
                            _loadingChannel.send(it.isLoading)
                        }
                    }
                    else -> {
                        Log.d("Admin", "getPagination: Kemadi")
                    }
                }
            }
//            Log.d("WWWWW","$list")
        }
    }

    fun productDetails(id: String, type: String) {
        viewModelScope.launch {
            repo.productDetails(id, type).observeForever {
                when (it) {
                    is BaseNetworkResult.Success -> {
                        product.value = it.data
                        Log.d("EEEEE", "getPagination: ${it.data}")
                    }
                    is BaseNetworkResult.Error -> {
                        viewModelScope.launch {
                            _errorChannel.send(it.message)
                        }
                    }
                    is BaseNetworkResult.Loading -> {
                        viewModelScope.launch {
                            _loadingChannel.send(it.isLoading)
                        }
                    }
                    else -> {
                        Log.d("ProductDetails", "getPagination: Kemadi")
                    }
                }
            }
        }
    }
}