package dark.composer.carpet.presentation.fragment.product

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dark.composer.carpet.data.repositories.ProductRepository
import dark.composer.carpet.data.retrofit.models.BaseNetworkResult
import dark.composer.carpet.data.retrofit.models.request.product.ProductCreateRequest
import dark.composer.carpet.data.retrofit.models.response.factory.PaginationResponse
import dark.composer.carpet.data.retrofit.models.response.product.ProductResponse
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductViewModel @Inject constructor(private val repository:ProductRepository) : ViewModel() {
    private val createProduct = MutableLiveData<ProductResponse>()
    val createProductLiveData : MutableLiveData<ProductResponse> = createProduct

    private val _errorChannel = Channel<String?>()
    val errorFlow = _errorChannel.receiveAsFlow()

    private val _loadingChannel = Channel<Boolean?>()
    val loadingFlow = _loadingChannel.receiveAsFlow()


}