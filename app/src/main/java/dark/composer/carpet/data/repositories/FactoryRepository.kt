package dark.composer.carpet.data.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dark.composer.carpet.data.retrofit.ApiService
import dark.composer.carpet.data.retrofit.models.BaseNetworkResult
import dark.composer.carpet.data.retrofit.models.request.factory.FactoryAddRequest
import dark.composer.carpet.data.retrofit.models.request.factory.update.FactoryUpdateRequest
import dark.composer.carpet.data.retrofit.models.response.factory.FactoryResponse
import dark.composer.carpet.data.retrofit.models.response.factory.PaginationResponse
import dark.composer.carpet.data.retrofit.models.response.profile.ProfileFileResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import javax.inject.Inject

class FactoryRepository @Inject constructor(private val service:ApiService) {
    suspend fun getInfoFactory(id:Int):LiveData<BaseNetworkResult<FactoryResponse>>{
        val factoryInfo = MutableLiveData<BaseNetworkResult<FactoryResponse>>()
        val response = service.getFactoryInfo(id)
        factoryInfo.value = BaseNetworkResult.Loading(true)
        if (response.code() == 200){
            response.body()?.let {
                factoryInfo.value = BaseNetworkResult.Loading(false)
                factoryInfo.value = BaseNetworkResult.Success(it)
            }
        }else{
            factoryInfo.value = BaseNetworkResult.Loading(false)
            factoryInfo.value = BaseNetworkResult.Error(response.message())
        }
        return factoryInfo
    }

    suspend fun addFactory(addRequest: FactoryAddRequest): Flow<BaseNetworkResult<FactoryResponse>> {
        return flow {
            val response = service.addInfoFactory(addRequest)
            emit(BaseNetworkResult.Loading(true))
            if (response.code() == 200) {
                response.body()?.let {
                    emit(BaseNetworkResult.Loading(false))
                    emit(BaseNetworkResult.Success(it))
                    Log.d("EEEEEEE", "getPagination: ${it.name}")
                }
            } else {
                emit(BaseNetworkResult.Loading(false))
                emit(BaseNetworkResult.Error(response.message()))
            }
        }
    }

    suspend fun getPagination(page:Int,size:Int):LiveData<BaseNetworkResult<PaginationResponse>>{
        val list = MutableLiveData<BaseNetworkResult<PaginationResponse>>()
        val response = service.getFactoryPagination(page,size)
        list.value = BaseNetworkResult.Loading(true)
        if (response.code() == 200){
            response.body()?.let {
                list.value = BaseNetworkResult.Loading(false)
                list.value = BaseNetworkResult.Success(it)
                Log.d("EEEEEEE", "getPagination: ${it.content.size}")
            }
        }else{
            list.value = BaseNetworkResult.Loading(false)
            list.value = BaseNetworkResult.Error(response.message())
        }
        return list
    }

    suspend fun updateFactory(id:Int,factoryUpdateRequest: FactoryUpdateRequest):LiveData<BaseNetworkResult<FactoryResponse>>{
        val factoryInfo = MutableLiveData<BaseNetworkResult<FactoryResponse>>()
        val response = service.updateInfoFactory(factoryUpdateRequest,id)
        factoryInfo.value = BaseNetworkResult.Loading(true)
        if (response.code() == 200){
            response.body()?.let {
                factoryInfo.value = BaseNetworkResult.Loading(false)
                factoryInfo.value = BaseNetworkResult.Success(it)
            }
        }else{
            factoryInfo.value = BaseNetworkResult.Loading(false)
            factoryInfo.value = BaseNetworkResult.Error(response.message())
        }
        return factoryInfo
    }

    suspend fun fileUploadFactory(key:String,file: MultipartBody.Part):LiveData<BaseNetworkResult<ProfileFileResponse>>{
        val factoryInfo = MutableLiveData<BaseNetworkResult<ProfileFileResponse>>()
        val response = service.factoryFileUpload(file,key)
        factoryInfo.value = BaseNetworkResult.Loading(true)
        if (response.code() == 200){
            response.body()?.let {
                factoryInfo.value = BaseNetworkResult.Loading(false)
                factoryInfo.value = BaseNetworkResult.Success(it)
            }
        }else{
            factoryInfo.value = BaseNetworkResult.Loading(false)
            factoryInfo.value = BaseNetworkResult.Error(response.message())
        }
        return factoryInfo
    }
}