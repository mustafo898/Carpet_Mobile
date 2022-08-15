package dark.composer.carpet.presentation.fragment.profile.users

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dark.composer.carpet.data.remote.models.request.profile.ProfileRequest
import dark.composer.carpet.data.remote.models.response.profile.ProfileResponse
import dark.composer.carpet.domain.use_case.profile.ProfileUseCase
import dark.composer.carpet.utils.BaseNetworkResult
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class UsersViewModel @Inject constructor(private val profileUseCase: ProfileUseCase) : ViewModel() {

    private val _usersList = MutableSharedFlow<BaseNetworkResult<List<ProfileResponse>>>()
    val usersList = _usersList.asSharedFlow()

    fun getList(size:Int,page:Int) {
        viewModelScope.launch {
            profileUseCase.getUsersProfilePagination(size,page).onEach { result ->
                when(result){
                    is BaseNetworkResult.Error -> {
                        _usersList.emit(BaseNetworkResult.Error(result.message?:"An unexpected error occurred"))
                    }
                    is BaseNetworkResult.Loading -> {
                        _usersList.emit(BaseNetworkResult.Loading(result.isLoading))
                    }
                    is BaseNetworkResult.Success -> {
                        Log.d("LLLLL", "getList: ${result.data!!}")
                        _usersList.emit(BaseNetworkResult.Success(result.data))
                    }
                }
            }.catch {t->
                Log.d("Mistake", "getProfile: ${t.message}")
            }.launchIn(viewModelScope)
        }
    }
}