package dark.composer.carpet.presentasion.fragment.signup

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dark.composer.carpet.data.repositories.SignUpRepositoryImpl
import dark.composer.carpet.data.retrofit.models.BaseNetworkResult
import dark.composer.carpet.data.retrofit.models.request.SignUpRequest
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignUpViewModel @Inject constructor(private val signUpRepositoryImpl: SignUpRepositoryImpl) :
    ViewModel() {

    private val signUpChannel = Channel<Boolean>()
    val signUpFlow = signUpChannel.receiveAsFlow()

    private val errorChannel = Channel<String>()
    val errorFlow = errorChannel.receiveAsFlow()

    private val phoneChannel = Channel<String>()
    val phoneFlow = phoneChannel.receiveAsFlow()

    private val passwordChannel = Channel<String>()
    val passwordFlow = passwordChannel.receiveAsFlow()

    private val confirmPasswordChannel = Channel<String>()
    val confirmPasswordFlow = confirmPasswordChannel.receiveAsFlow()

    private val nameChannel = Channel<String>()
    val nameFlow = nameChannel.receiveAsFlow()

    private val surNameChannel = Channel<String>()
    val surNameFlow = surNameChannel.receiveAsFlow()

    fun signUp(
        name: String,
        surname: String,
        phoneNumber: String,
        password: String,
        configPassword: String,
    ) {
        viewModelScope.launch {
            if (!validConfirmPassword(configPassword, password) && !validName(name) && !validPhone(
                    phoneNumber
                ) && !validPassword(password) && !validSurname(surname)
            ) {
                validConfirmPassword(configPassword, password)
                validName(name)
                validPhone(phoneNumber)
                validPassword(password)
                validSurname(surname)
            } else {
                signUpRepositoryImpl.signUp(
                    SignUpRequest(
                        name = name,
                        surname = surname,
                        phoneNumber = phoneNumber,
                        password = password,
                        configPassword = configPassword
                    )
                ).catch { t ->
                    Log.d("DDDD", "getServicesResponse: $t")
                }.collect {
                    when (it) {
                        is BaseNetworkResult.Success -> {
                            it.data?.let { d ->
                                signUpChannel.send(d)
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

    fun validName(name: String): Boolean {
        if (name.isEmpty()) {
            viewModelScope.launch {
                nameChannel.send("Name must be entered")
            }
            return false
        } else if (name.length <= 4) {
            viewModelScope.launch {
                nameChannel.send("Minimum 4 Characters Name")
            }
            return false
        } else {
            viewModelScope.launch {
                nameChannel.send("Correct")
            }
            return true
        }
    }

    fun validSurname(surName: String): Boolean {
        if (surName.isEmpty()) {
            viewModelScope.launch {
                surNameChannel.send("Last Name must be entered")
            }
            return false
        } else if (surName.length <= 4) {
            viewModelScope.launch {
                surNameChannel.send("Minimum 4 Characters LastName")
            }
            return false
        } else {
            viewModelScope.launch {
                surNameChannel.send("Correct")
            }
            return true
        }
    }

    fun validPhone(phone: String): Boolean {
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

    fun validConfirmPassword(configPassword: String, password: String): Boolean {
        if (password.isEmpty()) {
            viewModelScope.launch {
                passwordChannel.send("Password must be Entered")
            }
            return false
        }else if (configPassword != password) {
            viewModelScope.launch {
                confirmPasswordChannel.send("Confirm Password must be Equal Password")
            }
            return false
        } else {
            viewModelScope.launch {
                confirmPasswordChannel.send("Correct")
            }
            return true
        }
    }
}