package dark.composer.carpet.presentation.fragment.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dark.composer.carpet.data.repositories.LogInRepository
import dark.composer.carpet.data.retrofit.models.BaseNetworkResult
import dark.composer.carpet.data.retrofit.models.request.login.LogInRequest
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

    private val phoneChannel = Channel<String>()
    val phoneFlow = phoneChannel.receiveAsFlow()

    private val passwordChannel = Channel<String>()
    val passwordFlow = passwordChannel.receiveAsFlow()

    fun logIn(
        phoneNumber: String,
        password: String,
    ) {
        if (!validPhone(phoneNumber) && !validPassword(password)) {
            validPhone(phoneNumber)
            validPassword(password)
        } else {
            viewModelScope.launch {
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
            }
        }
    }

    fun validPhone(phone: String): Boolean {
        if(phone == "90909000"){
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
        }else {
            viewModelScope.launch {
                phoneChannel.send("Correct")
            }
            return true
        }
    }

    fun validPassword(password: String): Boolean {
        if (password == "admin") {
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
        }
//        else if (!password.matches(".*[@#\$%^_].*".toRegex())){
//            viewModelScope.launch {
//                passwordChannel.send("Must Contain 1 Special Character (@#\$%^_)")
//            }
//            return false
//        }
         else {
            viewModelScope.launch {
                passwordChannel.send("Correct")
            }
            return true
        }
    }
}