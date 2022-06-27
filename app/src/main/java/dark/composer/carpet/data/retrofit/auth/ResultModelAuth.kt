package dark.composer.carpet.data.retrofit.auth

import dark.composer.carpet.data.retrofit.models.response.LogInResponse

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