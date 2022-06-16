package dark.composer.carpet.mvvm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dark.composer.carpet.repositories.LogInRepository
import dark.composer.carpet.retrofit.models.BaseNetworkResult
import dark.composer.carpet.retrofit.models.request.LogInRequest
import dark.composer.carpet.utils.SharedPref
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class LogInViewModel @Inject constructor(
    private val logInRepository: LogInRepository,
    private var sharedPreferencesHelper: SharedPref
) : ViewModel() {

    private val logInChannel = Channel<Boolean>()
    val logInFlow = logInChannel.receiveAsFlow()

    private val errorChannel = Channel<String>()
    val errorFlow = errorChannel.receiveAsFlow()

    fun logIn(
        phoneNumber: String,
        password: String,
    ) {
        viewModelScope.launch {
            if (password.isNotEmpty() && phoneNumber.isNotEmpty()) {
                if (phoneNumber.length == 9) {
                    if (password.length >= 6) {
                        logInRepository.logIn(LogInRequest(password, phoneNumber)).catch { t ->
                            Log.d("DDDD", "getServicesResponse: $t")
                        }.collect {
                            when (it) {
                                is BaseNetworkResult.Success -> {
                                    it.data?.let { d ->
                                        logInChannel.send(d)
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
                    } else {
                        errorChannel.send("Password must be 6 or more characters")
                    }
                } else {
                    errorChannel.send("Please enter correct phone number")
                }
            } else {
                errorChannel.send("Please fill fields")
            }
        }
    }
}