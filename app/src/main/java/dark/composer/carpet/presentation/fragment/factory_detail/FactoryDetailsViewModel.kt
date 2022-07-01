package dark.composer.carpet.presentation.fragment.factory_detail

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dark.composer.carpet.data.repositories.FactoryDetailsRepository
import dark.composer.carpet.data.retrofit.models.BaseNetworkResult
import dark.composer.carpet.data.retrofit.models.request.factory.update.FactoryUpdateRequest
import dark.composer.carpet.data.retrofit.models.response.factory.FactoryResponse
import dark.composer.carpet.data.retrofit.models.response.profile.ProfileResponse
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

class FactoryDetailsViewModel @Inject constructor(private val factoryDetailsRepository: FactoryDetailsRepository): ViewModel(){
    private val infoFactory = MutableLiveData<FactoryResponse?>()
    val liveDataInfoFactory: MutableLiveData<FactoryResponse?> = infoFactory

    private val successChannel = Channel<ProfileResponse>()
    val successFlow = successChannel.receiveAsFlow()

    private val _loadingChannel = Channel<Boolean?>()
    val loadingFlow = _loadingChannel.receiveAsFlow()

    private val _errorChannel = Channel<String?>()
    val errorFlow = _errorChannel.receiveAsFlow()

    fun getInfoFactory(id:Int){
        viewModelScope.launch {
            factoryDetailsRepository.getInfoFactory(id).observeForever{
                when(it){
                    is BaseNetworkResult.Success->{
                        infoFactory.value = it.data
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

    fun updateInfoFactory(factoryUpdateRequest: FactoryUpdateRequest,id:Int){
        viewModelScope.launch {
            factoryDetailsRepository.updateFactory(id,factoryUpdateRequest).observeForever{
                when(it){
                    is BaseNetworkResult.Success->{
                        infoFactory.value = it.data
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

    fun uploadFile(file:MultipartBody.Part,key:String){
        viewModelScope.launch {
            factoryDetailsRepository.fileUploadFactory(key,file).observeForever{
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