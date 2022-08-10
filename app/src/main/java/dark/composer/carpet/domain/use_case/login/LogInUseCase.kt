package dark.composer.carpet.domain.use_case.login

import dark.composer.carpet.data.remote.dto.request.login.LogInRequest
import dark.composer.carpet.data.remote.dto.request.signup.SignUpRequest
import dark.composer.carpet.data.remote.dto.response.login.LogInResponse
import dark.composer.carpet.data.remote.dto.response.signup.SignUpResponse
import dark.composer.carpet.data.repository.SignUpRepositoryImpl
import dark.composer.carpet.domain.repository.login.LogInRepository
import dark.composer.carpet.domain.repository.signup.SignUpRepository
import dark.composer.carpet.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class LogInUseCase @Inject constructor(private val repo: LogInRepository) {
    operator fun invoke(login: LogInRequest): Flow<Resource<LogInResponse>> = flow {
        try {
            emit(Resource.loading(null))
            val profile = repo.logIn(login)
            emit(Resource.success(profile))
        } catch (e: HttpException) {
            e.printStackTrace()
            emit(Resource.error(e.localizedMessage ?: "An unexpected error occured", null))
        } catch (e: IOException) {
            emit(Resource.error("Couldn't reach server. Check your internet connection", null))
        }
    }
}