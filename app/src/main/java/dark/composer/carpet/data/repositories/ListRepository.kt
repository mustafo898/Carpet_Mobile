package dark.composer.carpet.data.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dark.composer.carpet.data.retrofit.ApiService
import dark.composer.carpet.data.retrofit.models.BaseNetworkResult
import dark.composer.carpet.data.retrofit.models.request.profile.create_customer.ProfileCreateRequest
import dark.composer.carpet.data.retrofit.models.response.profile.ProfileResponse
import dark.composer.carpet.utils.SharedPref
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ListRepository @Inject constructor(
    private val service: ApiService,
    private val sharedPref: SharedPref
) {
    suspend fun getProfile(): LiveData<BaseNetworkResult<List<ProfileResponse>>> {
        val list = MutableLiveData<BaseNetworkResult<List<ProfileResponse>>>()
        val response = service.getListProfile()
        list.value = BaseNetworkResult.Loading(true)
        if (response.code() == 200) {
            response.body()?.let {
                list.value = BaseNetworkResult.Loading(false)
                list.value = BaseNetworkResult.Success(it)
                Log.d("EEEEEEE", "getListProfile: $it")
            }
        } else {
            list.value = BaseNetworkResult.Loading(false)
            list.value = BaseNetworkResult.Error(response.message())
        }
        return list
    }

    suspend fun getProfileDetails(id:Int): LiveData<BaseNetworkResult<ProfileResponse>> {
        val list = MutableLiveData<BaseNetworkResult<ProfileResponse>>()
        val response = service.getListProfileDetails(id)
        list.value = BaseNetworkResult.Loading(true)
        if (response.code() == 200) {
            response.body()?.let {
                list.value = BaseNetworkResult.Loading(false)
                list.value = BaseNetworkResult.Success(it)
                Log.d("EEEEEEE", "getListProfile: $it")
            }
        } else {
            list.value = BaseNetworkResult.Loading(false)
            list.value = BaseNetworkResult.Error(response.message())
        }
        return list
    }

    suspend fun updateProfile(id:Int,request: ProfileCreateRequest): LiveData<BaseNetworkResult<ProfileResponse>> {
        val list = MutableLiveData<BaseNetworkResult<ProfileResponse>>()
        val response = service.updateCustomersProfile(id,request)
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

    suspend fun deleteProfile(id:Int): LiveData<BaseNetworkResult<ProfileResponse>> {
        val list = MutableLiveData<BaseNetworkResult<ProfileResponse>>()
        val response = service.deleteCustomersProfile(id)
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
}