package dark.composer.carpet.presentation.fragment.profile.list.customer

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dark.composer.carpet.data.repositories.ListRepository
import dark.composer.carpet.data.retrofit.models.BaseNetworkResult
import dark.composer.carpet.data.retrofit.models.response.product.pagination.ProductPaginationResponse
import dark.composer.carpet.data.retrofit.models.response.profile.ProfileResponse
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ListViewModel  @Inject constructor(private val repo : ListRepository) : ViewModel() {
    private val list = MutableLiveData<List<ProfileResponse>?>()
    val liveDataList: MutableLiveData<List<ProfileResponse>?> = list

    private val _errorChannel = Channel<String?>()
    val errorFlow = _errorChannel.receiveAsFlow()

    private val _loadingChannel = Channel<Boolean?>()
    val loadingFlow = _loadingChannel.receiveAsFlow()

    fun getProfileList() {
        viewModelScope.launch {
            repo.getProfile().observeForever {
                when (it) {
                    is BaseNetworkResult.Success -> {
                        list.value = it.data
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