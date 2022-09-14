package dark.composer.carpet.data.remote

import dark.composer.carpet.data.remote.models.request.basket.BasketCreateRequest
import dark.composer.carpet.data.remote.models.request.basket.BasketUpdateRequest
import dark.composer.carpet.data.remote.models.request.login.LogInRequest
import dark.composer.carpet.data.remote.models.request.signup.SignUpRequest
import dark.composer.carpet.data.remote.models.request.factory.FactoryAddRequest
import dark.composer.carpet.data.remote.models.request.factory.update.FactoryUpdateRequest
import dark.composer.carpet.data.remote.models.request.filter.ProductFilterRequest
import dark.composer.carpet.data.remote.models.request.product.ProductCreateRequest
import dark.composer.carpet.data.remote.models.request.profile.ProfileRequest
import dark.composer.carpet.data.remote.models.request.profile.create_customer.ProfileCreateRequest
import dark.composer.carpet.data.remote.models.request.sale.SaleCreateDateRequest
import dark.composer.carpet.data.remote.models.request.sale.SaleRequest
import dark.composer.carpet.data.remote.models.request.sale.SaleUpdateRequest
import dark.composer.carpet.data.remote.models.response.basket.BasketCreateResponse
import dark.composer.carpet.data.remote.models.response.basket.BasketPaginationResponse
import dark.composer.carpet.data.remote.models.response.basket.DeleteResponse
import dark.composer.carpet.data.remote.models.response.login.LogInResponse
import dark.composer.carpet.data.remote.models.response.signup.SignUpResponse
import dark.composer.carpet.data.remote.models.response.factory.FactoryResponse
import dark.composer.carpet.data.remote.models.response.factory.PaginationResponse
import dark.composer.carpet.data.remote.models.response.product.ProductFileUploadResponse
import dark.composer.carpet.data.remote.models.response.product.ProductResponse
import dark.composer.carpet.data.remote.models.response.product.pagination.ProductPaginationResponse
import dark.composer.carpet.data.remote.models.response.profile.ProfileFileResponse
import dark.composer.carpet.data.remote.models.response.profile.ProfileResponse
import dark.composer.carpet.data.remote.models.response.profile.users.UsersPagination
import dark.composer.carpet.data.remote.models.response.sale.SaleListByDate
import dark.composer.carpet.data.remote.models.response.sale.SalePaginationResponse
import dark.composer.carpet.data.remote.models.response.sale.SaleResponse
import dark.composer.carpet.utils.Constants
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*
import java.time.LocalDate
import java.time.LocalDateTime

interface ApiService {
    @POST(Constants.SIGNUP)
    suspend fun signUp(@Body signUpRequest: SignUpRequest): Response<SignUpResponse>

    @POST(Constants.LOGIN)
    suspend fun logIn(@Body logInRequest: LogInRequest): Response<LogInResponse>

    @DELETE("profile")
    suspend fun deleteProfile(): Response<ProfileResponse>

    @GET("profile/profile/addminver")
    suspend fun getProfile(): Response<ProfileResponse>

    @GET("profile/adm")
    suspend fun getUsersProfileList(): Response<List<ProfileResponse>>

    @GET("profile/adm/{id}")
    suspend fun getUsersProfileDetails(@Path("id") id: Int): Response<ProfileResponse>

    @GET(Constants.USER_PAGINATION)
    suspend fun getUsersPagination(
        @Query("size") size: Int,
        @Query("page") page: Int
    ): Response<UsersPagination>

    @PUT("profile/update")
    suspend fun updateProfile(@Body request: ProfileRequest): Response<ProfileResponse>

    @POST("profile/adm")
    suspend fun createProfile(@Body request: ProfileCreateRequest): Response<ProfileResponse>

    @PUT("profile/adm/{id}")
    suspend fun updateUsersProfile(
        @Path("id") id: Int,
        @Body request: ProfileCreateRequest
    ): Response<ProfileResponse>

    @DELETE("profile/adm")
    suspend fun deleteUsersProfile(@Query("id") id: Int): Response<ProfileResponse>

    @Multipart
    @POST("attach/upload/profile")
    suspend fun profileFileUpload(@Part file: MultipartBody.Part): Response<ProfileFileResponse>

    /** product CRUD */

    @POST("product/emp")
    suspend fun createProduct(@Body productCreateRequest: ProductCreateRequest): Response<ProductResponse>

    @GET("product/emp/{type}")
    suspend fun productDetails(
        @Path("type") type: String,
        @Query("id") id: String
    ): Response<ProductResponse>

//    @GET("product/adm/pagination/{type}")
//    suspend fun getProductPagination(
//        @Path("type") type: String,
//        @Query("page") page: Int,
//        @Query("size") size: Int
//    ): Response<List<ProductPaginationResponse>>

    @GET("product/adm/pagination/{type}")
    suspend fun getProductPagination(
        @Path("type") type: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response<List<ProductPaginationResponse>>

    @PUT("product/emp/{type}")
    suspend fun updateProduct(
        @Path("type") type: String,
        @Body productCreateRequest: ProductCreateRequest,
        @Query("id") id: String
    ): Response<ProductResponse>

    @DELETE("product/adm/{type}")
    suspend fun deleteProduct(
        @Path("type") type: String,
        @Query("id") id: String
    ): Response<ProductResponse>

    @Multipart
    @POST("attach/upload/product")
    suspend fun productFileUpload(
        @Part file: MultipartBody.Part,
        @Query("productId") productId: String
    ): Response<ProductFileUploadResponse>

//    @Multipart
//    @POST("attach/upload/product")
//    suspend fun productFileUpload(@Part files:Array<MultipartBody.Part>, @Query("productId") uuid: String): Response<ProductFileUploadResponse>

    /** factory CRUD */

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
    suspend fun updateFactory(
        @Body factoryUpdate: FactoryUpdateRequest,
        @Path("id") id: Int
    ): Response<FactoryResponse>

    @DELETE(Constants.FACTORY_UPDATE)
    suspend fun deleteFactory(
        @Path("id") id: Int
    ): Response<FactoryResponse>

    @POST(Constants.FACTORY_ADD)
    suspend fun createFactory(@Body factoryAddRequest: FactoryAddRequest): Response<FactoryResponse>

    @Multipart
    @POST("attach/adm/upload/factory/{key}")
    suspend fun factoryFileUpload(
        @Part file: MultipartBody.Part,
        @Path("key") page: String
    ): Response<ProfileFileResponse>

    /** Filter */

    @POST("/product/public/filter")
    suspend fun filterProduct(@Body filterProduct: ProductFilterRequest): Response<List<ProductPaginationResponse>>

    /** Basket */
    @POST("/basket/emp/created")
    suspend fun createBasket(@Body create: BasketCreateRequest): Response<BasketCreateResponse>

    @PUT("/basket/emp/update")
    suspend fun updateBasket(@Body update: BasketUpdateRequest): Response<BasketCreateResponse>

    @GET("/basket/emp/{id}")
    suspend fun getByIdBasket(@Path("id") id: Int): Response<BasketCreateResponse>

    @DELETE("/basket/adm/{id}")
    suspend fun deleteByIdBasket(@Path("id") id: Int): Response<DeleteResponse>

    @GET("/basket/emp/pagination/{status}")
    suspend fun getPaginationBasket(
        @Path("status") status: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response<List<BasketPaginationResponse>>

    /** Sale */
    @POST("/sale/emp/create")
    suspend fun createSale(@Body request: SaleRequest): Response<SaleResponse>

    @GET("/sale/emp/pagination/{type}")
    suspend fun getSaleList(
        @Path("type") type: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response<List<SalePaginationResponse>>

    @PUT("/sale/emp/update/{type}")
    suspend fun updateSale(
        @Body updateRequest: SaleUpdateRequest,
        @Path("type") type: String,
        @Query("id") id: Int
    ): Response<SaleResponse>

    @DELETE("/sale/adm/{type}")
    suspend fun deleteSale(
        @Path("type") type: String,
        @Query("id") id: Int
    ): Response<SaleResponse>

    @POST("/sale/adm/created_date")
    suspend fun listByCreateDate(@Body date: SaleCreateDateRequest): Response<List<SaleListByDate>>
}