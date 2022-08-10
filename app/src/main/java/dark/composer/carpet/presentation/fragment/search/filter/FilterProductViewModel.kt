package dark.composer.carpet.presentation.fragment.search.filter

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dark.composer.carpet.data.repositories.FactoryRepository
import dark.composer.carpet.utils.BaseNetworkResult
import dark.composer.carpet.data.remote.models.response.factory.PaginationResponse
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class FilterProductViewModel @Inject constructor(private val factoryRepo: FactoryRepository) :
    ViewModel() {
    private val _errorChannel = Channel<String?>()
    val errorFlow = _errorChannel.receiveAsFlow()

    private val _loadingChannel = Channel<Boolean?>()
    val loadingFlow = _loadingChannel.receiveAsFlow()

    private val typeChannel = MutableLiveData<String>()
    val typeFlow: LiveData<String> = typeChannel

    private val listPagination = MutableLiveData<PaginationResponse?>()
    val liveDataListPagination: MutableLiveData<PaginationResponse?> = listPagination

    fun getPagination(page: Int, size: Int) {
        viewModelScope.launch {
            factoryRepo.getPagination(page, size).observeForever {
                when (it) {
                    is BaseNetworkResult.Success -> {
                        listPagination.value = it.data
                        Log.d("EEEEE", "getPagination: ${it.data?.content}")
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

    fun validType(type: String): Boolean {
        return if (type.isNotEmpty()) {
            typeChannel.value = type
            true
        } else {
            typeChannel.value = "You must select Type"
            false
        }
    }
}