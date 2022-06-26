package dark.composer.carpet.data.retrofit.models.auth

import dark.composer.carpet.data.retrofit.models.BaseNetworkResult
import dark.composer.carpet.data.retrofit.models.response.LogInResponse
import dark.composer.carpet.data.retrofit.models.response.SignUpResponse
import kotlinx.coroutines.flow.Flow

data class ResultModelAuth(
    val signIp: ResultModelAuth? = null,
    val logIn: LogInResponse? = null,
    val message: String? = null,
    val isLoading: Boolean? = null
)
//{
//    class Success<T>(data: T) : ResultModelAuth<T>(data = data)
//    class Error<T>(message: String?) : ResultModelAuth<T>(message = message)
//    class Loading<T>(isLoading: Boolean?) : ResultModelAuth<T>(isLoading = isLoading)
//}