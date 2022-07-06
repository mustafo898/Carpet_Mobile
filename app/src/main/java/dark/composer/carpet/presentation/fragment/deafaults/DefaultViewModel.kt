package dark.composer.carpet.presentation.fragment.deafaults

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dark.composer.carpet.data.repositories.DefaultRepository
import dark.composer.carpet.data.retrofit.models.BaseNetworkResult
import dark.composer.carpet.data.retrofit.models.response.factory.PaginationResponse
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class DefaultViewModel @Inject constructor(private val defaultRepository: DefaultRepository) : ViewModel() {
    private val listPagination = MutableLiveData<PaginationResponse?>()
    val liveDataListPagination: MutableLiveData<PaginationResponse?> = listPagination

    private val _loadingChannel = Channel<Boolean?>()
    val loadingFlow = _loadingChannel.receiveAsFlow()

    private val _errorChannel = Channel<String?>()
    val errorFlow = _errorChannel.receiveAsFlow()

    fun getPagination(page:Int,size: Int){
        viewModelScope.launch {
            defaultRepository.getPagination(page,size).observeForever{
                when(it){
                    is BaseNetworkResult.Success->{
                        listPagination.value = it.data
                        if (it.data != null){
//                            Log.d("EEEEE", "getPagination: ${it.data.content[0].createdDate}")
                        }
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