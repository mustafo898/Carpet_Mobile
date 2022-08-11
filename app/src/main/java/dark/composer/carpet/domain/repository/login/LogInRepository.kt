package dark.composer.carpet.domain.repository.login

import dark.composer.carpet.data.remote.models.request.login.LogInRequest
import dark.composer.carpet.data.remote.models.response.login.LogInResponse
import dark.composer.carpet.utils.BaseNetworkResult
import kotlinx.coroutines.flow.Flow

interface LogInRepository {
    suspend fun logIn(request: LogInRequest): Flow<BaseNetworkResult<LogInResponse>>
}