package dark.composer.carpet.domain.repository.product

import dark.composer.carpet.data.remote.models.request.filter.ProductFilterRequest
import dark.composer.carpet.data.remote.models.request.product.ProductCreateRequest
import dark.composer.carpet.data.remote.models.response.product.ProductFileUploadResponse
import dark.composer.carpet.data.remote.models.response.product.ProductResponse
import dark.composer.carpet.data.remote.models.response.product.pagination.ProductPaginationResponse
import dark.composer.carpet.utils.BaseNetworkResult
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface ProductRepository {
    suspend fun getProductList(type:String,page:Int,size:Int): Flow<BaseNetworkResult<List<ProductPaginationResponse>>>
    suspend fun filterProduct(filter: ProductFilterRequest): Flow<BaseNetworkResult<List<ProductPaginationResponse>>>
    suspend fun getProduct(type:String,id:String): Flow<BaseNetworkResult<ProductResponse>>
    suspend fun createProduct(create: ProductCreateRequest): Flow<BaseNetworkResult<ProductResponse>>
    suspend fun updateProduct(type: String,update:ProductCreateRequest,id: String): Flow<BaseNetworkResult<ProductResponse>>
    suspend fun deleteProduct(type: String,id: String): Flow<BaseNetworkResult<ProductResponse>>
    suspend fun fileUploadProduct(file:MultipartBody.Part,id:String): Flow<BaseNetworkResult<ProductFileUploadResponse>>
}