package dark.composer.carpet.presentation.fragment.basket

import android.content.Context
import android.util.Log
import androidx.core.widget.ListViewAutoScrollHelper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dark.composer.carpet.data.repositories.BasketRepository
import dark.composer.carpet.data.retrofit.models.BaseNetworkResult
import dark.composer.carpet.data.retrofit.models.request.basket.BasketCreateRequest
import dark.composer.carpet.data.retrofit.models.request.basket.BasketUpdateRequest
import dark.composer.carpet.data.retrofit.models.response.basket.BasketCreateResponse
import dark.composer.carpet.data.retrofit.models.response.basket.BasketPaginationResponse
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class BasketViewModel @Inject constructor(private val repo: BasketRepository) : ViewModel() {
    private val basketChannel = MutableLiveData<BasketCreateResponse>()
    val basketFlow : LiveData<BasketCreateResponse> = basketChannel

    private val _basketPagination = MutableLiveData<List<BasketPaginationResponse>>()
    val basketPagination: LiveData<List<BasketPaginationResponse>> = _basketPagination

    private val _errorChannel = Channel<String?>()
    val errorFlow = _errorChannel.receiveAsFlow()

    private val _loadingChannel = Channel<Boolean?>()
    val loadingFlow = _loadingChannel.receiveAsFlow()

    fun updateBasket(updateRequest: BasketUpdateRequest) {
        viewModelScope.launch {
            repo.updateBasket(updateRequest).observeForever{
                when (it) {
                    is BaseNetworkResult.Success -> {
                        basketChannel.value = it.data!!
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

    fun getPaginationBasket(status: String, page: Int, size: Int, context: Context) {
        viewModelScope.launch {

            repo.getPaginationBasket(status, page, size, context).observeForever {
                when (it) {
                    is BaseNetworkResult.Success -> {
//                        it.data?.let {t->
                        _basketPagination.value = it.data!!
//                        }
                        Log.d("Basket", "createBasket: $it")
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
}
