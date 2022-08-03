package dark.composer.carpet.presentation.fragment.admin

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dark.composer.carpet.data.repositories.AdminRepository
import dark.composer.carpet.data.repositories.FactoryRepository
import dark.composer.carpet.data.repositories.ProfileRepository
import dark.composer.carpet.data.retrofit.models.BaseNetworkResult
import dark.composer.carpet.data.retrofit.models.response.factory.FactoryResponse
import dark.composer.carpet.data.retrofit.models.response.factory.PaginationResponse
import dark.composer.carpet.data.retrofit.models.response.profile.ProfileResponse
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class AdminViewModel @Inject constructor(
    private val adminRepository: AdminRepository,
    private val profileRepository: ProfileRepository,
    private val factoryRepository: FactoryRepository
) : ViewModel() {
    private val listPagination = MutableLiveData<PaginationResponse?>()
    val liveDataListPagination: MutableLiveData<PaginationResponse?> = listPagination

    private val profile = MutableLiveData<ProfileResponse?>()
    val liveDataProfile: MutableLiveData<ProfileResponse?> = profile

    private val addFactory = MutableLiveData<FactoryResponse?>()
    val liveDataAddFactory: MutableLiveData<FactoryResponse?> = addFactory

    private val _loadingChannel = Channel<Boolean?>()
    val loadingFlow = _loadingChannel.receiveAsFlow()

    private val _errorChannel = Channel<String?>()
    val errorFlow = _errorChannel.receiveAsFlow()

    fun getPagination(page: Int, size: Int) {
        viewModelScope.launch {
            factoryRepository.getPagination(page, size).observeForever {
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

    fun getProfile() {
        viewModelScope.launch {
            profileRepository.getProfile().observeForever {
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
}