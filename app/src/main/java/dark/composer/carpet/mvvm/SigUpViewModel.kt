package dark.composer.carpet.mvvm

import android.util.Log
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

class SigUpViewModel @Inject constructor(private val signUpRepository: SignUpRepository) : ViewModel() {

    private val signUpChannel = Channel<Boolean>()
    val signUpFlow = signUpChannel.receiveAsFlow()

    private val errorChannel = Channel<String>()
    val errorFlow = errorChannel.receiveAsFlow()

    fun signUp(
        name: String,
        surname: String,
        phoneNumber: String,
        password: String,
        configPassword: String,
    ) {
        viewModelScope.launch {
            if (name.isNotEmpty() && surname.isNotEmpty() && phoneNumber.isNotEmpty() && password.isNotEmpty() && configPassword.isNotEmpty()){
                if (name.length >= 4){
                    if (surname.length >= 3){
                        if (phoneNumber.length == 9){
                            if (password.length >= 6){
                                if (password == configPassword){
                                    signUpRepository.signUp(SignUpRequest(name=name,surname=surname,phoneNumber=phoneNumber,password=password,configPassword=configPassword))
                                }else{
                                    errorChannel.send("Config Password must be equal password")
                                }
                            }else{
                                errorChannel.send("Password must be 6 or more characters")
                            }
                        }else{
                            errorChannel.send("Please enter phone correct")
                        }
                    }else{
                        errorChannel.send("Surname must be 3 or more characters")
                    }
                }else{
                    errorChannel.send("Name must be 3 or more characters")
                }
            }else{
                errorChannel.send("Please fill fields")
            }
        }
    }
}