package dark.composer.carpet.repositories

import android.util.Log
import dark.composer.carpet.retrofit.ApiService
import dark.composer.carpet.retrofit.models.BaseNetworkResult
import dark.composer.carpet.retrofit.models.request.LogInRequest
import dark.composer.carpet.retrofit.models.response.LogInResponse
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
                response.body().let {
                    sharedPref.setToken(it!!.jwt)
                    Log.d("QQQQQ", "logIn: ${it.name}")
                }
                emit(BaseNetworkResult.Success(true))
            }else{
                emit(BaseNetworkResult.Error(response.message().toString()))
            }
        }
    }
}