package dark.composer.carpet.data.repositories

import android.util.Log
import dark.composer.carpet.data.retrofit.ApiService
import dark.composer.carpet.data.retrofit.models.BaseNetworkResult
import dark.composer.carpet.data.retrofit.models.request.SignUpRequest
import dark.composer.carpet.utils.SharedPref
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SignUpRepositoryImpl @Inject constructor(
    private val service: ApiService,
    private val sharedPref: SharedPref
) {
    suspend fun signUp(signUpRequest: SignUpRequest): Flow<BaseNetworkResult<Boolean>> {
        return flow {
            val response = service.signUp(signUpRequest)
            if (response.code() == 200) {
                response.body().let {
                    sharedPref.setToken(it!!.jwt)
                    Log.d("QQQQQ", "logIn: ${it.name}")
                }
                emit(BaseNetworkResult.Success(true))
            } else {
                emit(BaseNetworkResult.Error(response.message().toString()))
            }
        }
    }
}