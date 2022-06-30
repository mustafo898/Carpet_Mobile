package dark.composer.carpet.data.repositories

import android.util.Log
import dark.composer.carpet.data.retrofit.ApiService
import dark.composer.carpet.data.retrofit.models.BaseNetworkResult
import dark.composer.carpet.data.retrofit.models.response.profile.ProfileResponse
import dark.composer.carpet.utils.SharedPref
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import javax.inject.Inject

class ProfileRepository @Inject constructor(
    private var service: ApiService,
    private var sharedPref: SharedPref
){
    suspend fun changeImage(fileMultipartBody: MultipartBody.Part): Flow<BaseNetworkResult<ProfileResponse>> {
        return flow {
            Log.d("OOOOO", "changeImage: ishladi")
            val response = service.profileFileUpload(fileMultipartBody)
            if (response.code() == 200){
                response.body()?.let {
                    Log.d("FFFFF", "changeImage: ${it.url}")
                    emit(BaseNetworkResult.Success(it))
                }
            }else if (response.code() == 400){
                emit(BaseNetworkResult.Error("No access"))
                Log.d("OOOOO", "changeImage: xatolik")
            }else if(response.code() == 404){
                Log.d("OOOOO", "changeImage: xatolik")
                emit(BaseNetworkResult.Error("User not found"))
            }
        }
    }
}