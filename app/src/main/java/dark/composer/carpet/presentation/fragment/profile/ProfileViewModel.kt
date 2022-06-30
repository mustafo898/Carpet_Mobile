package dark.composer.carpet.presentation.fragment.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dark.composer.carpet.data.repositories.FactoryDetailsRepository
import dark.composer.carpet.data.repositories.ProfileRepository
import dark.composer.carpet.data.retrofit.models.BaseNetworkResult
import dark.composer.carpet.data.retrofit.models.request.LogInRequest
import dark.composer.carpet.data.retrofit.models.response.profile.ProfileResponse
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

class ProfileViewModel @Inject constructor(private val profileRepository: ProfileRepository) : ViewModel(){
    private val successChannel = Channel<ProfileResponse>()
    val successFlow = successChannel.receiveAsFlow()

    private val errorChannel = Channel<String>()
    val errorFlow = errorChannel.receiveAsFlow()

    fun changed(file:MultipartBody.Part){
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
                            errorChannel.send(d)
                        }
                    }
                    else -> {
                        Log.d("s", "signUp:")
                    }
                }
            }
        }
    }
}