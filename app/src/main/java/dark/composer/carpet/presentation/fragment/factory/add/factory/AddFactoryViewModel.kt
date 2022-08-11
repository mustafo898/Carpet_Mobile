package dark.composer.carpet.presentation.fragment.factory.add.factory

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dark.composer.carpet.data.remote.models.request.factory.FactoryAddRequest
import dark.composer.carpet.data.remote.models.response.factory.FactoryResponse
import dark.composer.carpet.data.remote.models.response.profile.ProfileFileResponse
import dark.composer.carpet.domain.use_case.factory.FactoryUseCase
import dark.composer.carpet.utils.BaseNetworkResult
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

class AddFactoryViewModel @Inject constructor(private val useCase : FactoryUseCase) : ViewModel() {

    private val _fileUpload = MutableSharedFlow<BaseNetworkResult<ProfileFileResponse?>>()
    val fileUpload = _fileUpload.asSharedFlow()

    private val _name = MutableSharedFlow<HashMap<String,Boolean>>()
    val name = _name.asSharedFlow()

    private val _factory = MutableSharedFlow<BaseNetworkResult<FactoryResponse>>()
    val factory = _factory.asSharedFlow()

    fun validName(string: String){
        viewModelScope.launch {
            if (string.isEmpty()){
                _name.emit(hashMapOf("Must be entered" to false))
            }else if (string.length <= 3){
                _name.emit(hashMapOf("Must be entered" to false))
            }else{
                _name.emit(hashMapOf("" to true))
            }
        }
    }

    fun createFactory(name1:String){
        viewModelScope.launch {
            useCase.createFactory(FactoryAddRequest(name1)).onEach { result ->
                when(result){
                    is BaseNetworkResult.Error -> {
                        _factory.emit(BaseNetworkResult.Error(result.message ?: "An unexpected error occured"))
                    }
                    is BaseNetworkResult.Loading -> {
                        _factory.emit(BaseNetworkResult.Loading(result.isLoading))
                    }
                    is BaseNetworkResult.Success -> {
                        _factory.emit(BaseNetworkResult.Success(result.data!!))
                    }
                }
            }.catch {t->
                Log.d("Add Factory", "createFactory: ")
            }.launchIn(viewModelScope)
        }
    }

    fun uploadFile(file:MultipartBody.Part,key:String){
        viewModelScope.launch {
            useCase.fileUploadFactory(file, key).onEach { result ->
                when(result){
                    is BaseNetworkResult.Error -> {
                        _fileUpload.emit(BaseNetworkResult.Error(result.message ?: "An unexpected error occured"))
                    }
                    is BaseNetworkResult.Loading -> {
                        _fileUpload.emit(BaseNetworkResult.Loading(result.isLoading))
                    }
                    is BaseNetworkResult.Success -> {
                        _fileUpload.emit(BaseNetworkResult.Success(result.data!!))
                    }
                }
            }.catch {t->
                Log.d("Add File", "uploadFile: ")
            }.launchIn(viewModelScope)
        }
    }
}