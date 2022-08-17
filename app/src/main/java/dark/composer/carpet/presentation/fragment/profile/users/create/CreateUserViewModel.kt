package dark.composer.carpet.presentation.fragment.profile.users.create

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dark.composer.carpet.data.remote.models.request.profile.create_customer.ProfileCreateRequest
import dark.composer.carpet.data.remote.models.response.profile.ProfileResponse
import dark.composer.carpet.domain.use_case.profile.ProfileUseCase
import dark.composer.carpet.utils.BaseNetworkResult
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class CreateUserViewModel @Inject constructor(private val profileUseCase: ProfileUseCase) :
    ViewModel() {
    private val nameChannel = Channel<String>()
    val nameFlow = nameChannel.receiveAsFlow()

    private val surNameChannel = Channel<String>()
    val surNameFlow = surNameChannel.receiveAsFlow()

    private val phoneChannel = Channel<String>()
    val phoneFlow = phoneChannel.receiveAsFlow()

    private val passwordChannel = Channel<String>()
    val passwordFlow = passwordChannel.receiveAsFlow()

    private val typeChannel = Channel<String>()
    val typeFlow = typeChannel.receiveAsFlow()

    private val _create = MutableSharedFlow<BaseNetworkResult<ProfileResponse>>()
    val create = _create.asSharedFlow()

    fun createProfile(
        name: String,
        surname: String,
        number: String,
        password: String,
        role: String,
    ) {
        viewModelScope.launch {
            if (!validName(name) && !validPhone(number) && !validPassword(password) && !validSurname(surname)) {
                validName(name)
                validPhone(number)
                validPassword(password)
                validSurname(surname)
            } else {
                profileUseCase.createProfile(
                    ProfileCreateRequest(
                        name,
                        password,
                        number,
                        role,
                        surname
                    )
                ).onEach { result ->
                    when (result) {
                        is BaseNetworkResult.Error -> {
                            _create.emit(
                                BaseNetworkResult.Error(
                                    result.message ?: "An unexpected error occurred"
                                )
                            )
                        }
                        is BaseNetworkResult.Loading -> {
                            _create.emit(BaseNetworkResult.Loading(result.isLoading))
                        }
                        is BaseNetworkResult.Success -> {
                            _create.emit(BaseNetworkResult.Success(result.data!!))
                        }
                    }
                }.catch { t ->
                    Log.d("Mistake", "getProfile: ${t.message}")
                }.launchIn(viewModelScope)
            }
        }
    }

    fun validName(name: String): Boolean {
        if (name.isEmpty()) {
            viewModelScope.launch {
                nameChannel.send("Name must be entered")
            }
            return false
        } else if (name.length < 4) {
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
        } else if (surName.length < 4) {
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
}