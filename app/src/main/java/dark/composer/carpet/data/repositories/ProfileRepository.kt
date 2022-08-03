package dark.composer.carpet.data.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dark.composer.carpet.data.retrofit.ApiService
import dark.composer.carpet.data.retrofit.models.BaseNetworkResult
import dark.composer.carpet.data.retrofit.models.request.factory.FactoryAddRequest
import dark.composer.carpet.data.retrofit.models.request.profile.ProfileRequest
import dark.composer.carpet.data.retrofit.models.request.profile.create_customer.ProfileCreateRequest
import dark.composer.carpet.data.retrofit.models.response.factory.FactoryResponse
import dark.composer.carpet.data.retrofit.models.response.factory.PaginationResponse
import dark.composer.carpet.data.retrofit.models.response.profile.ProfileFileResponse
import dark.composer.carpet.data.retrofit.models.response.profile.ProfileResponse
import dark.composer.carpet.utils.SharedPref
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import javax.inject.Inject

class ProfileRepository @Inject constructor(
    private var service: ApiService,
    private var sharedPref: SharedPref
) {
    suspend fun changeImage(fileMultipartBody: MultipartBody.Part): LiveData<BaseNetworkResult<ProfileFileResponse>> {
        val liveData = MutableLiveData<BaseNetworkResult<ProfileFileResponse>>()
        Log.d("OOOOO", "changeImage: ishladi")
        val response = service.profileFileUpload(fileMultipartBody)
        if (response.code() == 200) {
            response.body()?.let {
                Log.d("FFFFF", "changeImage: ${it.url}")
                liveData.value = BaseNetworkResult.Success(it)
            }
        } else if (response.code() == 400) {
            liveData.value = BaseNetworkResult.Error("No access")
            Log.d("OOOOO", "changeImage: xatolik")
        } else if (response.code() == 404) {
            Log.d("OOOOO", "changeImage: xatolik")
            liveData.value = BaseNetworkResult.Error("User not found")
        }
        return liveData
    }

    suspend fun getProfile(): LiveData<BaseNetworkResult<ProfileResponse>> {
        val list = MutableLiveData<BaseNetworkResult<ProfileResponse>>()
        val response = service.getProfile()
        list.value = BaseNetworkResult.Loading(true)
        if (response.code() == 200) {
            response.body()?.let {
                list.value = BaseNetworkResult.Loading(false)
                list.value = BaseNetworkResult.Success(it)
                Log.d("EEEEEEE", "getPagination: ${it.name}")
            }
        } else {
            list.value = BaseNetworkResult.Loading(false)
            list.value = BaseNetworkResult.Error(response.message())
        }
        return list
    }

    suspend fun updateProfile(request: ProfileRequest): LiveData<BaseNetworkResult<ProfileResponse>> {
        val list = MutableLiveData<BaseNetworkResult<ProfileResponse>>()
        val response = service.updateProfile(request)
        list.value = BaseNetworkResult.Loading(true)
        if (response.code() == 200) {
            response.body()?.let {
                list.value = BaseNetworkResult.Loading(false)
                list.value = BaseNetworkResult.Success(it)
                Log.d("EEEEEEE", "getPagination: ${it.name}")
            }
        } else {
            list.value = BaseNetworkResult.Loading(false)
            list.value = BaseNetworkResult.Error(response.message())
        }
        return list
    }

    suspend fun createProfile(request: ProfileCreateRequest): LiveData<BaseNetworkResult<ProfileResponse>> {
        val list = MutableLiveData<BaseNetworkResult<ProfileResponse>>()
        val response = service.createCustomersProfile(request)
        list.value = BaseNetworkResult.Loading(true)
        if (response.code() == 200) {
            response.body()?.let {
                list.value = BaseNetworkResult.Loading(false)
                list.value = BaseNetworkResult.Success(it)
                Log.d("EEEEEEE", "getPagination: ${it.name}")
            }
        } else {
            list.value = BaseNetworkResult.Loading(false)
            list.value = BaseNetworkResult.Error(response.message())
        }
        return list
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
}