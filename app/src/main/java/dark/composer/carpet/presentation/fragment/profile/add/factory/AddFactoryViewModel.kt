package dark.composer.carpet.presentation.fragment.profile.add.factory

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dark.composer.carpet.data.repositories.FactoryRepository
import dark.composer.carpet.data.retrofit.models.BaseNetworkResult
import dark.composer.carpet.data.retrofit.models.request.factory.FactoryAddRequest
import dark.composer.carpet.data.retrofit.models.response.factory.FactoryResponse
import dark.composer.carpet.data.retrofit.models.response.profile.ProfileFileResponse
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

class AddFactoryViewModel @Inject constructor(private val repo: FactoryRepository) : ViewModel() {
    private val _loadingChannel = Channel<Boolean?>()
    val loadingFlow = _loadingChannel.receiveAsFlow()

    private val successChannel = Channel<ProfileFileResponse>()
    val successFlow = successChannel.receiveAsFlow()

    private val _errorChannel = Channel<String?>()
    val errorFlow = _errorChannel.receiveAsFlow()

    private val addFactory = MutableLiveData<FactoryResponse?>()
    val liveDataAddFactory: MutableLiveData<FactoryResponse?> = addFactory

    private val nameChannel = MutableLiveData<String>()
    val nameFlow:LiveData<String> = nameChannel

    fun addFactory(name: String,context:Context){
        if (validName(name)){
            viewModelScope.launch {
                repo.addFactory(FactoryAddRequest(name)).catch {
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
            Toast.makeText(context, "Yes", Toast.LENGTH_SHORT).show()
        }else{
            validName(name)
        }
    }

    fun validName(name: String): Boolean {
        if (name.isEmpty()) {
            viewModelScope.launch {
                nameChannel.value = "Name must be entered"
            }
            return false
        } else if (name.length < 4) {
            viewModelScope.launch {
                nameChannel.value = "Minimum 4 Characters Name"
            }
            return false
        } else {
            viewModelScope.launch {
                nameChannel.value = "Correct"
            }
            return true
        }
    }

    fun uploadFile(file: MultipartBody.Part, key:String){
        viewModelScope.launch {
            repo.fileUploadFactory(key,file).observeForever{
                when(it){
                    is BaseNetworkResult.Success->{
                        viewModelScope.launch {
                            it.data?.let { it1 -> successChannel.send(it1) }
                        }
                        Log.d("EEEEE", "getPagination: ${it.data}")
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
    }
}