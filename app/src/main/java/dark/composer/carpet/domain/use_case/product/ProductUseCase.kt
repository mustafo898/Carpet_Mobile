package dark.composer.carpet.domain.use_case.product

import dark.composer.carpet.data.remote.models.request.filter.ProductFilterRequest
import dark.composer.carpet.data.remote.models.request.product.ProductCreateRequest
import dark.composer.carpet.data.remote.models.response.product.ProductFileUploadResponse
import dark.composer.carpet.data.remote.models.response.product.ProductResponse
import dark.composer.carpet.data.remote.models.response.product.pagination.ProductPaginationResponse
import dark.composer.carpet.domain.repository.product.ProductRepository
import dark.composer.carpet.utils.BaseNetworkResult
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import javax.inject.Inject

class ProductUseCase @Inject constructor(private val productRepository:ProductRepository) {
    suspend fun createProduct(createRequest: ProductCreateRequest):Flow<BaseNetworkResult<ProductResponse>> {
        return productRepository.createProduct(createRequest)
    }

    suspend fun updateProduct(type:String, update: ProductCreateRequest, id:String):Flow<BaseNetworkResult<ProductResponse>> {
        return productRepository.updateProduct(type, update, id)
    }

    suspend fun deleteProduct(type:String,id:String):Flow<BaseNetworkResult<ProductResponse>> {
        return productRepository.deleteProduct(type, id)
    }

    suspend fun getProductList(type:String,page:Int,size:Int):Flow<BaseNetworkResult<List<ProductPaginationResponse>>> {
        return productRepository.getProductList(type, page, size)
    }

    suspend fun fileUploadProduct(file:MultipartBody.Part,id:String):Flow<BaseNetworkResult<ProductFileUploadResponse>> {
        return productRepository.fileUploadProduct(file, id)
    }

    suspend fun getProduct(type:String,id: String):Flow<BaseNetworkResult<ProductResponse>> {
        return productRepository.getProduct(type, id)
    }

    suspend fun filterProduct(filterRequest: ProductFilterRequest):Flow<BaseNetworkResult<List<ProductPaginationResponse>>> {
        return productRepository.filterProduct(filterRequest)
    }
}