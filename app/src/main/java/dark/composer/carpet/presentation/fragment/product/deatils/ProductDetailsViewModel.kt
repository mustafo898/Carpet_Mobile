package dark.composer.carpet.presentation.fragment.product.deatils

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dark.composer.carpet.data.repositories.ProductRepository
import dark.composer.carpet.data.retrofit.models.BaseNetworkResult
import dark.composer.carpet.data.retrofit.models.request.product.ProductCreateRequest
import dark.composer.carpet.data.retrofit.models.response.product.ProductFileUploadResponse
import dark.composer.carpet.data.retrofit.models.response.product.ProductResponse
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductDetailsViewModel @Inject constructor(private val repo:ProductRepository) : ViewModel() {
    private val product = MutableLiveData<ProductResponse?>()
    val productLiveData: MutableLiveData<ProductResponse?> = product

    private val _errorChannel = Channel<String?>()
    val errorFlow = _errorChannel.receiveAsFlow()

    private val _loadingChannel = Channel<Boolean?>()
    val loadingFlow = _loadingChannel.receiveAsFlow()

    private val successChangeChannel = Channel<ProductFileUploadResponse>()
    val successChangeFlow = successChangeChannel.receiveAsFlow()

    fun productDetails(id : String, type: String) {
        viewModelScope.launch {
            repo.productDetails(id,type).observeForever {
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

    fun updateProduct(id : String, type: String, updateProduct:ProductCreateRequest) {
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

//    fun uploadFile(file: MultipartBody.Part, productId:String){
//        viewModelScope.launch {
//            repo.fileUploadProduct(productId,file).observeForever{
//                when(it){
//                    is BaseNetworkResult.Success->{
//                        viewModelScope.launch {
//                            it.data?.let { it1 -> successChangeChannel.send(it1) }
//                        }
//                        Log.d("EEEEE", "getPagination: ${it.data}")
//                    }
//                    is BaseNetworkResult.Error->{
//                        viewModelScope.launch {
//                            _errorChannel.send(it.message)
//                        }
//                    }
//                    is BaseNetworkResult.Loading->{
//                        viewModelScope.launch {
//                            _loadingChannel.send(it.isLoading)
//                        }
//                    }
//                    else -> {
//                        Log.d("Admin", "getPagination: Kemadi")
//                    }
//                }
//            }
//        }
//    }
}