package dark.composer.carpet.presentation.fragment.profile.users.details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dark.composer.carpet.data.remote.models.response.profile.ProfileResponse
import dark.composer.carpet.domain.use_case.profile.ProfileUseCase
import dark.composer.carpet.utils.BaseNetworkResult
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserDetailsViewModel @Inject constructor(private val profileUseCase: ProfileUseCase) :
    ViewModel() {
    private val _profile = MutableSharedFlow<BaseNetworkResult<ProfileResponse>>()
    val profile = _profile.asSharedFlow()

    fun getProfile(id: Int) {
        viewModelScope.launch {
            profileUseCase.getUsersProfileDetails(id).onEach { result ->
                when (result) {
                    is BaseNetworkResult.Error -> {
                        _profile.emit(
                            BaseNetworkResult.Error(
                                result.message ?: "An unexpected error occurred"
                            )
                        )
                    }
                    is BaseNetworkResult.Loading -> {
                        _profile.emit(BaseNetworkResult.Loading(result.isLoading))
                    }
                    is BaseNetworkResult.Success -> {
                        _profile.emit(BaseNetworkResult.Success(result.data!!))
                    }
                }
            }.catch { t ->
                Log.d("Mistake", "getProfile: ${t.message}")
            }.launchIn(viewModelScope)
        }
    }

    private val _delete = MutableSharedFlow<BaseNetworkResult<ProfileResponse>>()
    val delete = _delete.asSharedFlow()

    private fun deleteProfile() {
        viewModelScope.launch {
            profileUseCase.getProfile().onEach { result ->
                when (result) {
                    is BaseNetworkResult.Error -> {
                        _delete.emit(
                            BaseNetworkResult.Error(
                                result.message ?: "An unexpected error occurred"
                            )
                        )
                    }
                    is BaseNetworkResult.Loading -> {
                        _delete.emit(BaseNetworkResult.Loading(result.isLoading))
                    }
                    is BaseNetworkResult.Success -> {
                        _delete.emit(BaseNetworkResult.Success(result.data!!))
                    }
                }
            }.catch { t ->
                Log.d("Mistake", "getProfile: ${t.message}")
            }.launchIn(viewModelScope)
        }
    }
}