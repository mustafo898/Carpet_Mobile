package dark.composer.carpet.presentation.fragment.profile.add.factory

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dark.composer.carpet.data.repositories.ProfileRepository
import dark.composer.carpet.data.retrofit.models.BaseNetworkResult
import dark.composer.carpet.data.retrofit.models.request.factory.FactoryAddRequest
import dark.composer.carpet.data.retrofit.models.response.factory.FactoryResponse
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddFactoryViewModel @Inject constructor(private val repo:ProfileRepository) : ViewModel() {
    private val _loadingChannel = Channel<Boolean?>()
    val loadingFlow = _loadingChannel.receiveAsFlow()

    private val _errorChannel = Channel<String?>()
    val errorFlow = _errorChannel.receiveAsFlow()

    private val addFactory = MutableLiveData<FactoryResponse?>()
    val liveDataAddFactory: MutableLiveData<FactoryResponse?> = addFactory

    private val nameChannel = Channel<String>()
    val nameFlow = nameChannel.receiveAsFlow()

    fun addFactory(addFactoryRequest: FactoryAddRequest){
        if (validName(addFactoryRequest.name)){
            viewModelScope.launch {
                repo.addFactory(addFactoryRequest).catch {
                    Log.d("Add Factory", "addFactory: $this")
                }.collect{
                    when(it){
                        is BaseNetworkResult.Success->{
                            addFactory.value = it.data
                            Log.d("EEEEE", "getPagination: ${it.data?.name}")
                        }
                        is BaseNetworkResult.Error->{
                            viewModelScope.launch {
                                _errorChannel.send(it.message)
                            }
                        }
                        is BaseNetworkResult.Loading->{
                            viewModelScope.launch {
                                _loadingChannel.send(it.isLoading)
                            }
                        }
                        else -> {
                            Log.d("Admin", "getPagination: Kemadi")
                        }
                    }
                }
            }
        }else{
            validName(addFactoryRequest.name)
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

}