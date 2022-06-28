package dark.composer.carpet.data.retrofit

import dark.composer.carpet.data.retrofit.models.request.LogInRequest
import dark.composer.carpet.data.retrofit.models.request.SignUpRequest
import dark.composer.carpet.data.retrofit.models.request.factory.FactoryAddRequest
import dark.composer.carpet.data.retrofit.models.request.factory.update.FactoryUpdateRequest
import dark.composer.carpet.data.retrofit.models.response.LogInResponse
import dark.composer.carpet.data.retrofit.models.response.SignUpResponse
import dark.composer.carpet.data.retrofit.models.response.factory.FactoryResponse
import dark.composer.carpet.data.retrofit.models.response.factory.PaginationResponse
import dark.composer.carpet.utils.Constants
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @POST(Constants.LOGIN)
    suspend fun logIn(@Body logInRequest: LogInRequest):Response<LogInResponse>

    @POST(Constants.SIGNUP)
    suspend fun signUp(@Body signUpRequest: SignUpRequest):Response<SignUpResponse>

    @GET(Constants.FACTORY_PAGINATION)
    suspend fun getFactoryPagination(@Query("page") page:Int,@Query("size") size: Int):Response<PaginationResponse>

    @GET(Constants.FACTORY_INFO)
    suspend fun getFactoryInfo(@Path("id") id:Int):Response<FactoryResponse>

    @PUT(Constants.FACTORY_UPDATE)
    suspend fun updateInfoFactory(@Body factoryUpdate:FactoryUpdateRequest, @Path("id") id:Int):Response<FactoryResponse>

    @POST(Constants.FACTORY_ADD)
    suspend fun addInfoFactory(@Body factoryAddRequest: FactoryAddRequest):Response<FactoryResponse>
}