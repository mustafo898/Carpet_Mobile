package dark.composer.carpet.presentation.fragment.product

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dark.composer.carpet.data.repositories.BasketRepository
import dark.composer.carpet.data.repositories.ProductRepository
import dark.composer.carpet.utils.BaseNetworkResult
import dark.composer.carpet.data.remote.models.request.basket.BasketCreateRequest
import dark.composer.carpet.data.remote.models.request.product.ProductCreateRequest
import dark.composer.carpet.data.remote.models.response.basket.BasketCreateResponse
import dark.composer.carpet.data.remote.models.response.product.ProductResponse
import dark.composer.carpet.data.remote.models.response.product.pagination.ProductPaginationResponse
import kotlinx.coroutines.channels.Channel
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
    val basketFlow: LiveData<BasketCreateResponse> = basketChannel

    private val _errorChannel = Channel<String?>()
    val errorFlow = _errorChannel.receiveAsFlow()

    private val _loading = MutableLiveData<Boolean>()
    val loadingLiveData: LiveData<Boolean> = _loading

    private val _loading1 = MutableLiveData<Boolean>()
    val loadingLiveData1: LiveData<Boolean> = _loading1

    fun createBasket(createRequest: BasketCreateRequest) {
        viewModelScope.launch {
            repoBasket.createBasket(createRequest).observeForever {
                when (it) {
                    is BaseNetworkResult.Success -> {
                        basketChannel.value = it.data!!
                    }
                    is BaseNetworkResult.Loading -> {
                        _loading.value = it.isLoading!!
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

    fun getCountPagination(page: Int, size: Int, count: String, id: String) {
        val list = mutableListOf<ProductPaginationResponse>()
        viewModelScope.launch {
            repo.getPagination(page, size, count).observeForever {
                when (it) {
                    is BaseNetworkResult.Success -> {
                        it.data?.let { t ->
                            t.forEach { e ->
                                if (e.uuid != id) {
                                    list.add(e)
                                }
                            }
                            listPagination.value = list
                        }
                        Log.d("EEEEE", "countable: ${it.data}")
                    }
                    is BaseNetworkResult.Error -> {
                        viewModelScope.launch {
                            _errorChannel.send(it.message)
                        }
                    }
                    is BaseNetworkResult.Loading -> {
                        _loading.value = it.isLoading!!
                    }
                    else -> {
                        Log.d("Admin", "getPagination: Kemadi")
                    }
                }
            }
        }
    }

    fun updateProduct(id : String, type: String, updateProduct: ProductCreateRequest) {
        viewModelScope.launch {
            repo.updateProduct(id,type,updateProduct).observeForever {
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
                            _loading.value = it.isLoading!!
                    }
                    else -> {
                        Log.d("ProductDetails", "getPagination: Kemadi")
                    }
                }
            }
        }
    }

    fun deleteProduct(id : String, type: String) {
        viewModelScope.launch {
            repo.deleteProduct(id,type).observeForever {
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
                            _loading.value = it.isLoading!!
                        }
                    }
                    else -> {
                        Log.d("ProductDetails", "getPagination: Kemadi")
                    }
                }
            }
        }
    }

    fun productDetails(id: String, type: String) {
        viewModelScope.launch {
            repo.productDetails(id, type).observeForever {
                when (it) {
                    is BaseNetworkResult.Loading -> {
                        Log.d("MMMMMM", "productDetails: ${it.isLoading}")
                        _loading1.postValue ( it.isLoading!!)
                    }
                    is BaseNetworkResult.Success -> {
                        product.value = it.data
                        Log.d("EEEEE", "getPagination: ${it.data}")
                    }
                    is BaseNetworkResult.Error -> {
                        viewModelScope.launch {
                            _errorChannel.send(it.message)
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