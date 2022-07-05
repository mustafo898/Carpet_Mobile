package dark.composer.carpet.data.retrofit

import dark.composer.carpet.data.retrofit.models.request.login.LogInRequest
import dark.composer.carpet.data.retrofit.models.request.signup.SignUpRequest
import dark.composer.carpet.data.retrofit.models.request.factory.FactoryAddRequest
import dark.composer.carpet.data.retrofit.models.request.factory.update.FactoryUpdateRequest
import dark.composer.carpet.data.retrofit.models.request.product.ProductCreateRequest
import dark.composer.carpet.data.retrofit.models.response.login.LogInResponse
import dark.composer.carpet.data.retrofit.models.response.signup.SignUpResponse
import dark.composer.carpet.data.retrofit.models.response.factory.FactoryResponse
import dark.composer.carpet.data.retrofit.models.response.factory.PaginationResponse
import dark.composer.carpet.data.retrofit.models.response.product.ProductFileUploadResponse
import dark.composer.carpet.data.retrofit.models.response.product.ProductResponse
import dark.composer.carpet.data.retrofit.models.response.product.pagination.ProductPaginationResponse
import dark.composer.carpet.data.retrofit.models.response.profile.ProfileResponse
import dark.composer.carpet.utils.Constants
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @POST(Constants.SIGNUP)
    suspend fun signUp(@Body signUpRequest: SignUpRequest): Response<SignUpResponse>

    @POST(Constants.LOGIN)
    suspend fun logIn(@Body logInRequest: LogInRequest): Response<LogInResponse>

    @Multipart
    @POST("attach/upload/profile")
    suspend fun profileFileUpload(@Part file: MultipartBody.Part): Response<ProfileResponse>

    /* product CRUD */
    @POST("product/emp")
    suspend fun createProduct(@Body productCreateRequest: ProductCreateRequest): Response<ProductResponse>

    @GET("product/adm/{type}")
    suspend fun productDetails(@Path("type") type:String, @Query("id") id:String): Response<ProductResponse>

    @GET("product/adm/pagination/{type}")
    suspend fun getProductPagination(
        @Path("type") type: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response<List<ProductPaginationResponse>>

    @PUT("product/emp/{type}")
    suspend fun updateProduct(@Path("type") type:String,@Body productCreateRequest: ProductCreateRequest, @Query("id") id:String): Response<ProductResponse>

    @DELETE("product/adm/{type}")
    suspend fun deleteProduct(@Path("type") type:String, @Query("id") id:String) : Response<ProductResponse>

    @Multipart
    @POST("attach/upload/product")
    suspend fun productFileUpload(@Part file: MultipartBody.Part, @Query("productId") productId:String): Response<ProductFileUploadResponse>

    // factory CRUD
    @GET(Constants.FACTORY_PAGINATION_ADM)
    suspend fun getFactoryPaginationAdmin(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response<PaginationResponse>

    @GET(Constants.FACTORY_PAGINATION)
    suspend fun getFactoryPagination(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response<PaginationResponse>

    @GET(Constants.FACTORY_INFO)
    suspend fun getFactoryInfo(@Path("id") id: Int): Response<FactoryResponse>

    @PUT(Constants.FACTORY_UPDATE)
    suspend fun updateInfoFactory(
        @Body factoryUpdate: FactoryUpdateRequest,
        @Path("id") id: Int
    ): Response<FactoryResponse>

    @POST(Constants.FACTORY_ADD)
    suspend fun addInfoFactory(@Body factoryAddRequest: FactoryAddRequest): Response<FactoryResponse>

    @Multipart
    @POST("attach/adm/upload/factory/{key}")
    suspend fun factoryFileUpload(
        @Part file: MultipartBody.Part,
        @Path("key") page: String
    ): Response<ProfileResponse>
}