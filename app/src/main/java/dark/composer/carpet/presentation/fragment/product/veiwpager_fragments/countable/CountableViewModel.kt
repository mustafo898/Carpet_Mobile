package dark.composer.carpet.presentation.fragment.product.veiwpager_fragments.countable

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dark.composer.carpet.data.repositories.ProductRepository
import dark.composer.carpet.data.retrofit.models.BaseNetworkResult
import dark.composer.carpet.data.retrofit.models.response.product.ProductResponse
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class CountableViewModel @Inject constructor(private val repo : ProductRepository) : ViewModel() {
    private val listPagination = MutableLiveData<List<ProductResponse>?>()
    val liveDataListPagination: MutableLiveData<List<ProductResponse>?> = listPagination


    private val _errorChannel = Channel<String?>()
    val errorFlow = _errorChannel.receiveAsFlow()

    private val _loadingChannel = Channel<Boolean?>()
    val loadingFlow = _loadingChannel.receiveAsFlow()

    fun getPagination(page:Int,size: Int, type:String){
        viewModelScope.launch {
            repo.getPagination(page,size,type).observeForever{
                when(it){
                    is BaseNetworkResult.Success->{
                        listPagination.value = it.data
                        Log.d("EEEEE", "getPagination: ${it.data}")
                    }
                    is BaseNetworkResult.Error->{
                        viewModelScope.launch {
                            _errorChannel.send(it.message)
                        }
                    }
                    is BaseNetworkResult.Loading->{
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