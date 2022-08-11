package dark.composer.carpet.domain.use_case.login

import dark.composer.carpet.data.remote.models.request.login.LogInRequest
import dark.composer.carpet.data.remote.models.response.login.LogInResponse
import dark.composer.carpet.domain.repository.login.LogInRepository
import dark.composer.carpet.utils.BaseNetworkResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LogInUseCase @Inject constructor(private val repo: LogInRepository) {
    suspend fun logIn(login: LogInRequest): Flow<BaseNetworkResult<LogInResponse>> {
        return repo.logIn(login)
    }
}