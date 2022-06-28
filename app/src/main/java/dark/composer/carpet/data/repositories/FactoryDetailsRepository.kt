package dark.composer.carpet.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dark.composer.carpet.data.retrofit.ApiService
import dark.composer.carpet.data.retrofit.models.BaseNetworkResult
import dark.composer.carpet.data.retrofit.models.request.factory.update.FactoryUpdateRequest
import dark.composer.carpet.data.retrofit.models.response.factory.FactoryResponse
import javax.inject.Inject

class FactoryDetailsRepository @Inject constructor(private val service:ApiService) {
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
}