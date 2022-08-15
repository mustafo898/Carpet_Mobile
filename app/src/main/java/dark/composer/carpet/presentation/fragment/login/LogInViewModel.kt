package dark.composer.carpet.presentation.fragment.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dark.composer.carpet.data.remote.models.request.login.LogInRequest
import dark.composer.carpet.data.remote.models.response.login.LogInResponse
import dark.composer.carpet.domain.use_case.login.LogInUseCase
import dark.composer.carpet.utils.BaseNetworkResult
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class LogInViewModel @Inject constructor(private val useCase: LogInUseCase) : ViewModel() {
    private val logInChannel = MutableSharedFlow<BaseNetworkResult<LogInResponse>>()
    val loginFlow = logInChannel.asSharedFlow()

    private val phoneChannel = Channel<String>()
    val phoneFlow = phoneChannel.receiveAsFlow()

    private val passwordChannel = Channel<String>()
    val passwordFlow = passwordChannel.receiveAsFlow()

    fun logIn(
        phoneNumber: String,
        password: String,
    ) {
        viewModelScope.launch {
            if (!validPhone(phoneNumber) && !validPassword(password)) {
                validPhone(phoneNumber)
                validPassword(password)
            } else {
                useCase.logIn(LogInRequest(password,phoneNumber)).onEach { result ->
                    when(result){
                        is BaseNetworkResult.Error -> {
                            logInChannel.emit(BaseNetworkResult.Error(result.message ?: "An unexpected error occurred LogIn"))
                        }
                        is BaseNetworkResult.Loading -> {
                            logInChannel.emit(BaseNetworkResult.Loading(result.isLoading))
                        }
                        is BaseNetworkResult.Success -> {
                            logInChannel.emit(BaseNetworkResult.Success(result.data!!))
                        }
                    }
                }.catch { t ->
                    Log.d("Mistake", "logIn: ${t.message}")
                }.launchIn(viewModelScope)
            }
        }
    }

    fun validPhone(phone: String): Boolean {
        if (phone == "90909000"){
            viewModelScope.launch {
                phoneChannel.send("Correct")
            }
            return true
        }
        if (phone.isEmpty()) {
            viewModelScope.launch {
                phoneChannel.send("Phone Number must be entered")
            }
            return false
        } else if (phone.length != 9) {
            viewModelScope.launch {
                phoneChannel.send("Please Enter Correct Phone Number")
            }
            return false
        } else {
            viewModelScope.launch {
                phoneChannel.send("Correct")
            }
            return true
        }
    }

    fun validPassword(password: String): Boolean {
        if (password == "admin"){
            viewModelScope.launch {
                passwordChannel.send("Correct")
            }
            return true
        }
        if (password.length <= 6) {
            viewModelScope.launch {
                passwordChannel.send("Minimum 6 Character Password")
            }
            return false
        } else if (!password.matches(".*[A-Z].*".toRegex())) {
            viewModelScope.launch {
                passwordChannel.send("Must Contain 1 Upper-case Character")
            }
            return false
        } else if (!password.matches(".*[a-z].*".toRegex())) {
            viewModelScope.launch {
                passwordChannel.send("Must Contain 1 Lower-case Character")
            }
            return false
        } else if (!password.matches(".*[@#\$%^_].*".toRegex())) {
            viewModelScope.launch {
                passwordChannel.send("Must Contain 1 Special Character (@#\$%^_)")
            }
            return false
        } else {
            viewModelScope.launch {
                passwordChannel.send("Correct")
            }
            return true
        }
    }
}