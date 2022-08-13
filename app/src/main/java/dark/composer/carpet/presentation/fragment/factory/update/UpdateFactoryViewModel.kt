package dark.composer.carpet.presentation.fragment.factory.update

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dark.composer.carpet.data.remote.models.request.factory.FactoryAddRequest
import dark.composer.carpet.data.remote.models.request.factory.update.FactoryUpdateRequest
import dark.composer.carpet.data.remote.models.response.factory.FactoryResponse
import dark.composer.carpet.data.remote.models.response.profile.ProfileFileResponse
import dark.composer.carpet.domain.use_case.factory.FactoryUseCase
import dark.composer.carpet.utils.BaseNetworkResult
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

class UpdateFactoryViewModel @Inject constructor(private val useCase : FactoryUseCase) : ViewModel() {

    private val _fileUpload = MutableSharedFlow<BaseNetworkResult<ProfileFileResponse?>>()
    val fileUpload = _fileUpload.asSharedFlow()

    private val _name = MutableSharedFlow<HashMap<String,Boolean>>()
    val name = _name.asSharedFlow()

    private val _factory = MutableSharedFlow<BaseNetworkResult<FactoryResponse>>()
    val factory = _factory.asSharedFlow()

    private val _update = MutableSharedFlow<BaseNetworkResult<FactoryResponse>>()
    val update = _update.asSharedFlow()

    fun validName(string: String){
        viewModelScope.launch {
            if (string.isEmpty()){
                _name.emit(hashMapOf("Must be entered" to false))
            }else if (string.length <= 3){
                _name.emit(hashMapOf("Minimum size factory name must be 3" to false))
            }else{
                _name.emit(hashMapOf("" to true))
            }
        }
    }

    fun updateFactory(updateRequest: FactoryUpdateRequest,id:Int){
        viewModelScope.launch {
            useCase.updateFactory(updateRequest,id).onEach { result ->
                when(result){
                    is BaseNetworkResult.Error -> {
                        _update.emit(BaseNetworkResult.Error(result.message ?: "An unexpected error occured"))
                    }
                    is BaseNetworkResult.Loading -> {
                        _update.emit(BaseNetworkResult.Loading(result.isLoading))
                    }
                    is BaseNetworkResult.Success -> {
                        _update.emit(BaseNetworkResult.Success(result.data!!))
                    }
                }
            }.catch {t->
                Log.d("Add Factory", "updateFactory: ")
            }.launchIn(viewModelScope)
        }
    }

    fun getFactory(id:Int){
        viewModelScope.launch {
            useCase.getFactory(id).onEach { result ->
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