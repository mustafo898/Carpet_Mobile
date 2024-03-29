package dark.composer.carpet.presentation.fragment.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dark.composer.carpet.data.remote.models.request.profile.ProfileRequest
import dark.composer.carpet.data.remote.models.request.profile.create_customer.ProfileCreateRequest
import dark.composer.carpet.data.remote.models.response.profile.ProfileFileResponse
import dark.composer.carpet.data.remote.models.response.profile.ProfileResponse
import dark.composer.carpet.domain.use_case.profile.ProfileUseCase
import dark.composer.carpet.utils.BaseNetworkResult
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

class ProfileViewModel @Inject constructor(private val useCase:ProfileUseCase) : ViewModel() {

    private val _profile = MutableSharedFlow<BaseNetworkResult<ProfileResponse>>()
    val profile = _profile.asSharedFlow()

    fun getProfile() {
        viewModelScope.launch {
            useCase.getProfile().onEach { result ->
                when(result){
                    is BaseNetworkResult.Error -> {
                        _profile.emit(BaseNetworkResult.Error(result.message?:"An unexpected error occurred"))
                    }
                    is BaseNetworkResult.Loading -> {
                        _profile.emit(BaseNetworkResult.Loading(result.isLoading))
                    }
                    is BaseNetworkResult.Success -> {
                        _profile.emit(BaseNetworkResult.Success(result.data!!))
                        Log.d("PROFILEVMSSS","profile:  ${result.data}")
                    }
                }
            }.catch {t->
                Log.d("Mistake", "getProfile: ${t.message}")
            }.launchIn(viewModelScope)
        }
    }

    private val _update = MutableSharedFlow<BaseNetworkResult<ProfileResponse>>()
    val update = _update.asSharedFlow()

    fun updateProfile(update:ProfileRequest) {
        viewModelScope.launch {
            useCase.updateProfile(update).onEach { result ->
                when(result){
                    is BaseNetworkResult.Error -> {
                        _update.emit(BaseNetworkResult.Error(result.message?:"An unexpected error occurred"))
                    }
                    is BaseNetworkResult.Loading -> {
                        _update.emit(BaseNetworkResult.Loading(result.isLoading))
                    }
                    is BaseNetworkResult.Success -> {
                        _update.emit(BaseNetworkResult.Success(result.data!!))
                    }
                }
            }.catch {t->
                Log.d("Mistake", "getProfile: ${t.message}")
            }.launchIn(viewModelScope)
        }
    }

    private val _file = MutableSharedFlow<BaseNetworkResult<ProfileFileResponse>>()
    val file = _file.asSharedFlow()

    fun fileUploadProfile(file:MultipartBody.Part) {
        viewModelScope.launch {
            useCase.fileUpload(file).onEach { result ->
                when(result){
                    is BaseNetworkResult.Error -> {
                        _file.emit(BaseNetworkResult.Error(result.message?:"An unexpected error occurred"))
                    }
                    is BaseNetworkResult.Loading -> {
                        _file.emit(BaseNetworkResult.Loading(result.isLoading))
                    }
                    is BaseNetworkResult.Success -> {
                        _file.emit(BaseNetworkResult.Success(result.data!!))
                    }
                }
            }.catch {t->
                Log.d("Mistake", "getProfile: ${t.message}")
            }.launchIn(viewModelScope)
        }
    }

    private val _delete = MutableSharedFlow<BaseNetworkResult<ProfileResponse>>()
    val delete = _delete.asSharedFlow()

    fun deleteProfile() {
        viewModelScope.launch {
            useCase.getProfile().onEach { result ->
                when(result){
                    is BaseNetworkResult.Error -> {
                        _delete.emit(BaseNetworkResult.Error(result.message?:"An unexpected error occurred"))
                    }
                    is BaseNetworkResult.Loading -> {
                        _delete.emit(BaseNetworkResult.Loading(result.isLoading))
                    }
                    is BaseNetworkResult.Success -> {
                        _delete.emit(BaseNetworkResult.Success(result.data!!))
                    }
                }
            }.catch {t->
                Log.d("Mistake", "getProfile: ${t.message}")
            }.launchIn(viewModelScope)
        }
    }
}