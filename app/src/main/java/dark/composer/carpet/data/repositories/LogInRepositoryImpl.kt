package dark.composer.carpet.data.repositories

import android.util.Log
import dark.composer.carpet.data.remote.ApiService
import dark.composer.carpet.utils.BaseNetworkResult
import dark.composer.carpet.data.remote.models.request.login.LogInRequest
import dark.composer.carpet.data.remote.models.response.login.LogInResponse
import dark.composer.carpet.domain.repository.login.LogInRepository
import dark.composer.carpet.utils.SharedPref
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LogInRepositoryImpl @Inject constructor(
    private var service: ApiService,
    private var sharedPref: SharedPref
) : LogInRepository {
    override suspend fun logIn(logInRequest: LogInRequest): Flow<BaseNetworkResult<LogInResponse>> {
        return flow {
            val response = service.logIn(logInRequest)
            emit(BaseNetworkResult.Loading(true))
            if (response.code() == 200) {
                emit(BaseNetworkResult.Loading(false))
                response.body()?.let {
                    sharedPref.setToken(it.jwt)
                    sharedPref.setImage(it.url ?: "")
                    sharedPref.setName(it.name)
                    sharedPref.setSurName(it.surname)
                    sharedPref.setPhoneNumber(it.phoneNumber)
                    sharedPref.setRole(it.role)
                    Log.d("QQQQQ", "logIn: ${it.name}")
                    emit(BaseNetworkResult.Success(it))
                }
            } else {
                emit(BaseNetworkResult.Error(response.message()))
            }
        }
    }

//    else if (response.code() == 403){
//        emit(BaseNetworkResult.Loading(false))
//        emit(BaseNetworkResult.Error("No access"))
//    }else if(response.code() == 404){
//        emit(BaseNetworkResult.Loading(false))
//        emit(BaseNetworkResult.Error("User not found"))
//    }else{
//        emit(BaseNetworkResult.Loading(false))
//        emit(BaseNetworkResult.Error("User not found"))
//    }
}