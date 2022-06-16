package dark.composer.carpet.retrofit

import dark.composer.carpet.retrofit.request.LogInRequest
import dark.composer.carpet.retrofit.response.LogInResponse
import dark.composer.carpet.utils.Constants
import retrofit2.http.Body
import retrofit2.http.POST


interface ApiService {

    @POST(Constants.LOGIN)
    suspend fun (@Body LogInRequest):LogInResponse

}