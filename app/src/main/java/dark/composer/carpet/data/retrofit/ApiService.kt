package dark.composer.carpet.data.retrofit

import dark.composer.carpet.data.retrofit.models.request.LogInRequest
import dark.composer.carpet.data.retrofit.models.request.SignUpRequest
import dark.composer.carpet.data.retrofit.models.response.LogInResponse
import dark.composer.carpet.data.retrofit.models.response.SignUpResponse
import dark.composer.carpet.data.retrofit.models.response.factory.PaginationResponse
import dark.composer.carpet.utils.Constants
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @POST(Constants.LOGIN)
    suspend fun logIn(@Body logInRequest: LogInRequest):Response<LogInResponse>

    @POST(Constants.SIGNUP)
    suspend fun signUp(@Body signUpRequest: SignUpRequest):Response<SignUpResponse>

    @POST(Constants.FACTORY)
    suspend fun get()

    @GET(Constants.FACTORY_PAGINATION)
    suspend fun getFactoryPagination(@Query("page") page:Int,@Query("size") size: Int):Response<PaginationResponse>
}