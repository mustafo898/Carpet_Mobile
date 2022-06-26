package dark.composer.carpet.data.repositories

import android.util.Log
import dark.composer.carpet.data.retrofit.ApiService
import dark.composer.carpet.data.retrofit.models.BaseNetworkResult
import dark.composer.carpet.data.retrofit.models.request.LogInRequest
import dark.composer.carpet.utils.SharedPref
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LogInRepository @Inject constructor(
    private var service: ApiService,
    private var sharedPref: SharedPref
) {

    suspend fun logIn(logInRequest: LogInRequest): Flow<BaseNetworkResult<Boolean>> {
        return flow {
            val response = service.logIn(logInRequest)
            if (response.code() == 200){
                response.body()?.let {
                    sharedPref.setToken(it.jwt)
                    sharedPref.setImage(it.url?:"")
                    sharedPref.setName(it.name)
                    sharedPref.setSurName(it.surname)
                    sharedPref.setPhoneNumber(it.phoneNumber)
                    sharedPref.setRole(it.role)
                    Log.d("QQQQQ", "logIn: ${it.name}")
                }
                emit(BaseNetworkResult.Success(true))
            }else if (response.code() == 403){
                emit(BaseNetworkResult.Error("No access"))
            }else if(response.code() == 404){
                emit(BaseNetworkResult.Error("User not found"))
            }
        }
    }
}