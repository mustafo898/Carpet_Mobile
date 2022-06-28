package dark.composer.carpet.data.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dark.composer.carpet.data.retrofit.ApiService
import dark.composer.carpet.data.retrofit.models.BaseNetworkResult
import dark.composer.carpet.data.retrofit.models.request.factory.FactoryAddRequest
import dark.composer.carpet.data.retrofit.models.response.factory.FactoryResponse
import dark.composer.carpet.data.retrofit.models.response.factory.PaginationResponse
import dark.composer.carpet.utils.SharedPref
import javax.inject.Inject

class AdminRepository @Inject constructor(
    private val service: ApiService,
    private val sharedPref: SharedPref
){
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

    suspend fun addFactory(addRequest: FactoryAddRequest):LiveData<BaseNetworkResult<FactoryResponse>>{
        val list = MutableLiveData<BaseNetworkResult<FactoryResponse>>()
        val response = service.addInfoFactory(addRequest)
        list.value = BaseNetworkResult.Loading(true)
        if (response.code() == 200){
            response.body()?.let {
                list.value = BaseNetworkResult.Loading(false)
                list.value = BaseNetworkResult.Success(it)
                Log.d("EEEEEEE", "getPagination: ${it.name}")
            }
        }else{
            list.value = BaseNetworkResult.Loading(false)
            list.value = BaseNetworkResult.Error(response.message())
        }
        return list
    }
}