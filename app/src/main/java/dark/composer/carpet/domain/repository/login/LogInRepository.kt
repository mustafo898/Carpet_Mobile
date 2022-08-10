package dark.composer.carpet.domain.repository.login

import dark.composer.carpet.data.remote.models.request.login.LogInRequest
import dark.composer.carpet.data.remote.models.response.login.LogInResponse

interface LogInRepository {
    suspend fun logIn(request: LogInRequest): LogInResponse
}