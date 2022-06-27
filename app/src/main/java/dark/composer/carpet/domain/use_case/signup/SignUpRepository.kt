package dark.composer.carpet.domain.use_case.signup

import dark.composer.carpet.data.retrofit.auth.ResultModelAuth
import dark.composer.carpet.data.retrofit.models.request.SignUpRequest
import kotlinx.coroutines.flow.Flow

interface SignUpRepository {
    suspend fun signUp(signUpResponse: SignUpRequest) : Flow<ResultModelAuth>
}