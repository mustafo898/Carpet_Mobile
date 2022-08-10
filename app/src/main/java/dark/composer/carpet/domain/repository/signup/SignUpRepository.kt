package dark.composer.carpet.domain.repository.signup

import dark.composer.carpet.data.remote.models.request.signup.SignUpRequest
import dark.composer.carpet.data.remote.models.response.signup.SignUpResponse
import dark.composer.carpet.utils.BaseNetworkResult
import kotlinx.coroutines.flow.Flow

interface SignUpRepository {
    suspend fun register(request: SignUpRequest): Flow<BaseNetworkResult<SignUpResponse>>

}