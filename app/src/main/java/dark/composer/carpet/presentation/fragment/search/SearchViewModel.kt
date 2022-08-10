package dark.composer.carpet.presentation.fragment.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dark.composer.carpet.data.repositories.ProductRepository
import dark.composer.carpet.utils.BaseNetworkResult
import dark.composer.carpet.data.remote.models.request.filter.ProductFilterRequest
import dark.composer.carpet.data.remote.models.response.product.pagination.ProductPaginationResponse
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchViewModel @Inject constructor(private val repo: ProductRepository) : ViewModel() {
    private val listProductPagination = MutableLiveData<List<ProductPaginationResponse>?>()
    val liveDataListProductPagination: MutableLiveData<List<ProductPaginationResponse>?> =
        listProductPagination

    private val _errorChannel = Channel<String?>()
    val errorFlow = _errorChannel.receiveAsFlow()

    private val _loadingChannel = Channel<Boolean?>()
    val loadingFlow = _loadingChannel.receiveAsFlow()

    private val typeChannel = MutableLiveData<String>()
    val typeFlow: LiveData<String> = typeChannel

    fun filterProduct(filterRequest: ProductFilterRequest) {
        viewModelScope.launch {
            repo.filterProduct(filterRequest).observeForever {
                when (it) {
                    is BaseNetworkResult.Success -> {
                        listProductPagination.value = it.data
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
                        Log.d("Admin", "getPagination: Kemadi")
                    }
                }
            }
        }
    }
}