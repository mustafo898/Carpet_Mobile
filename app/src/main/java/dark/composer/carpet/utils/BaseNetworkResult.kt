package dark.composer.carpet.utils

import dark.composer.carpet.data.remote.models.response.factory.FactoryResponse

sealed class BaseNetworkResult<T>(
    val data: T? = null,
    val message: String? = null,
    val isLoading: Boolean? = null
) {
    class Success<T>(data: T) : BaseNetworkResult<T>(data = data)
    class Error<T>(message: String?) : BaseNetworkResult<T>(message = message)
    class Loading<T>(isLoading: Boolean?) : BaseNetworkResult<T>(isLoading = isLoading)
}