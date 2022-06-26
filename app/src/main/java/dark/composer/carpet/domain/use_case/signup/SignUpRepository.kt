package dark.composer.carpet.domain.use_case.signup

import dark.composer.carpet.data.retrofit.models.BaseNetworkResult
import dark.composer.carpet.data.retrofit.models.auth.ResultModelAuth
import dark.composer.carpet.data.retrofit.models.request.SignUpRequest
import dark.composer.carpet.data.retrofit.models.response.SignUpResponse
import kotlinx.coroutines.flow.Flow

interface SignUpRepository {
    suspend fun signUp(signUpResponse: SignUpRequest) : Flow<ResultModelAuth>
}