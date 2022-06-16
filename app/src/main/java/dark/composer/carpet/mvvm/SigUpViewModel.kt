package dark.composer.carpet.mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dark.composer.carpet.repositories.SignUpRepository
import dark.composer.carpet.retrofit.models.BaseNetworkResult
import dark.composer.carpet.retrofit.models.request.SignUpRequest
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SigUpViewModel @Inject constructor(private val signUpRepository: SignUpRepository) :
    ViewModel() {
    private val logUpChannel = Channel<Boolean>()
    val logUpFlow = logUpChannel.receiveAsFlow()

    fun signUp(
        name: String,
        surname: String,
        phoneNumber: String,
        password: String,
        configPassword: String,
    ) {
        viewModelScope.launch {
            signUpRepository.signUp(
                SignUpRequest(
                    name = name,
                    surname = surname,
                    phoneNumber = phoneNumber,
                    password = password,
                    configPassword = configPassword
                )
            ).catch {
                //errors


            }.collect { result ->
                when (result) {
                    is BaseNetworkResult.Success -> {
                        result.data?.let { isSignUp ->
                            logUpChannel.send(isSignUp)
                        }
                    }
                    is BaseNetworkResult.Error -> {
                        //network error

                    }
                    is BaseNetworkResult.Loading -> {
                        //loading
//                       result.isLoading?.let {
//                           _isLoadingLiveData.value = it
//                       }
                    }
                }

            }
        }
    }
}