package dark.composer.carpet.presentation.fragment.profile

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dark.composer.carpet.data.repositories.ProfileRepository
import dark.composer.carpet.data.retrofit.models.BaseNetworkResult
import dark.composer.carpet.data.retrofit.models.request.profile.ProfileRequest
import dark.composer.carpet.data.retrofit.models.request.profile.create_customer.ProfileCreateRequest
import dark.composer.carpet.data.retrofit.models.response.profile.ProfileFileResponse
import dark.composer.carpet.data.retrofit.models.response.profile.ProfileResponse
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

class ProfileViewModel @Inject constructor(private val profileRepository: ProfileRepository) :
    ViewModel() {
    private val successChannel = Channel<ProfileFileResponse>()
    val successFlow = successChannel.receiveAsFlow()

    private val _loadingChannel = Channel<Boolean?>()
    val loadingFlow = _loadingChannel.receiveAsFlow()

    private val _errorChannel = Channel<String?>()
    val errorFlow = _errorChannel.receiveAsFlow()

    private val profile = MutableLiveData<ProfileResponse?>()
    val liveDataProfile: MutableLiveData<ProfileResponse?> = profile

    fun changed(file: MultipartBody.Part) {
        viewModelScope.launch {
            profileRepository.changeImage(file).catch { t ->
                Log.d("DDDD", "getServicesResponse: $t")
            }.collect {
                when (it) {
                    is BaseNetworkResult.Success -> {
                        it.data?.let { d ->
                            successChannel.send(d)
                        }
                    }
                    is BaseNetworkResult.Error -> {
                        it.message?.let { d ->
                            _errorChannel.send(d)
                        }
                    }
                    else -> {
                        Log.d("s", "signUp:")
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

    fun updateProfile(request: ProfileRequest) {
        viewModelScope.launch {
            profileRepository.updateProfile(request).observeForever {
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

    fun createProfile(request: ProfileCreateRequest) {
        viewModelScope.launch {
            profileRepository.createProfile(request).observeForever {
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