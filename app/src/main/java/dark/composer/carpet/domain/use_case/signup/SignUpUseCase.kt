package dark.composer.carpet.domain.use_case.signup

import dark.composer.carpet.data.remote.models.request.signup.SignUpRequest
import dark.composer.carpet.data.remote.models.response.signup.SignUpResponse
import dark.composer.carpet.domain.repository.signup.SignUpRepository
import dark.composer.carpet.utils.BaseNetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SignUpUseCase @Inject constructor(private val repo: SignUpRepository) {
    suspend fun sigUp(signUp: SignUpRequest): Flow<BaseNetworkResult<SignUpResponse>> {
        return repo.register(signUp)
    }
}