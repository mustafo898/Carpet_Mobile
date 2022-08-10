package dark.composer.carpet.presentation.fragment.profile.list.details

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dark.composer.carpet.data.repositories.ListRepository
import dark.composer.carpet.utils.BaseNetworkResult
import dark.composer.carpet.data.remote.models.request.profile.create_customer.ProfileCreateRequest
import dark.composer.carpet.data.remote.models.response.profile.ProfileResponse
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ListDetailsViewModel @Inject constructor(private val repo:ListRepository) : ViewModel() {
    private val profile = MutableLiveData<ProfileResponse?>()
    val liveDataProfile: MutableLiveData<ProfileResponse?> = profile

    private val _errorChannel = Channel<String?>()
    val errorFlow = _errorChannel.receiveAsFlow()

    private val _loadingChannel = Channel<Boolean?>()
    val loadingFlow = _loadingChannel.receiveAsFlow()

    fun getProfileList(id:Int) {
        viewModelScope.launch {
            repo.getProfileDetails(id).observeForever {
                when (it) {
                    is BaseNetworkResult.Success -> {
                        profile.value = it.data
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

    fun updateProfile(id:Int,request: ProfileCreateRequest) {
        viewModelScope.launch {
            repo.updateProfile(id,request).observeForever {
                when (it) {
                    is BaseNetworkResult.Success -> {
                        profile.value = it.data
                        Log.d("EEEEE", "getPagination: ${it.data?.name}")
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

    fun deleteProfile(id:Int) {
        viewModelScope.launch {
            repo.deleteProfile(id).observeForever {
                when (it) {
                    is BaseNetworkResult.Success -> {
                        profile.value = it.data
                        Log.d("EEEEE", "delete: ${it.data?.visible}")
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