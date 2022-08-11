package dark.composer.carpet.data.repositories

import android.util.Log
import dark.composer.carpet.data.remote.ApiService
import dark.composer.carpet.utils.BaseNetworkResult
import dark.composer.carpet.data.remote.models.request.signup.SignUpRequest
import dark.composer.carpet.data.remote.models.response.signup.SignUpResponse
import dark.composer.carpet.domain.repository.signup.SignUpRepository
import dark.composer.carpet.utils.SharedPref
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SignUpRepositoryImpl @Inject constructor(
    private val service: ApiService,
    private val sharedPref: SharedPref
):SignUpRepository {
    override suspend fun register(request: SignUpRequest): Flow<BaseNetworkResult<SignUpResponse>> = flow{
        val response = service.signUp(request)
        if (response.code() == 200) {
            response.body().let {
                sharedPref.setToken(it!!.jwt)
                Log.d("QQQQQ", "register: ${it.name}")
                emit(BaseNetworkResult.Success(it))
            }
        } else {
            emit(BaseNetworkResult.Error(response.message().toString()))
        }
    }
}