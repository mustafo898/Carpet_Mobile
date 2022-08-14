package dark.composer.carpet.data.repositories

import android.util.Log
import dark.composer.carpet.data.remote.ApiService
import dark.composer.carpet.utils.BaseNetworkResult
import dark.composer.carpet.data.remote.models.request.filter.ProductFilterRequest
import dark.composer.carpet.data.remote.models.request.product.ProductCreateRequest
import dark.composer.carpet.data.remote.models.response.product.ProductFileUploadResponse
import dark.composer.carpet.data.remote.models.response.product.ProductResponse
import dark.composer.carpet.data.remote.models.response.product.pagination.ProductPaginationResponse
import dark.composer.carpet.domain.repository.product.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(private var service: ApiService) :
    ProductRepository {
    override suspend fun getProductList(
        type: String,
        page: Int,
        size: Int
    ): Flow<BaseNetworkResult<List<ProductPaginationResponse>>> = flow {
        val response = service.getProductPagination(type, page, size)
        emit(BaseNetworkResult.Loading(true))
        if (response.code() == 200) {
            emit(BaseNetworkResult.Loading(false))
            response.body()?.let {
                emit(BaseNetworkResult.Success(it))
                it.forEach { t ->
                    Log.d("EEEEEEE", "getProductList: ${t.imageUrlList}")
                }
            }
        } else {
            emit(BaseNetworkResult.Loading(false))
            emit(BaseNetworkResult.Error(response.message()))
        }
    }

    override suspend fun filterProduct(filter: ProductFilterRequest): Flow<BaseNetworkResult<List<ProductPaginationResponse>>> =
        flow {
            val response = service.filterProduct(filter)
            emit(BaseNetworkResult.Loading(true))
            if (response.code() == 200) {
                emit(BaseNetworkResult.Loading(false))
                response.body()?.let {
                    emit(BaseNetworkResult.Success(it))
                    it.forEach { t ->
                        Log.d("EEEEEEE", "filterProduct: ${t.imageUrlList}")
                    }
                }
            } else {
                emit(BaseNetworkResult.Loading(false))
                emit(BaseNetworkResult.Error(response.message()))
            }
        }

    override suspend fun getProduct(
        type: String,
        id: String
    ): Flow<BaseNetworkResult<ProductResponse>> = flow {
        val response = service.productDetails(type, id)
        emit(BaseNetworkResult.Loading(true))
        if (response.code() == 200) {
            emit(BaseNetworkResult.Loading(false))
            response.body()?.let {
                emit(BaseNetworkResult.Success(it))
                Log.d("EEEEEEE", "getProduct: ${it.name}")
            }
        } else {
            emit(BaseNetworkResult.Loading(false))
            emit(BaseNetworkResult.Error(response.message()))
        }
    }

    override suspend fun createProduct(create: ProductCreateRequest): Flow<BaseNetworkResult<ProductResponse>> =
        flow {
            Log.d("NNNNN", "updateProduct:\n $create \n")
            val response = service.createProduct(create)
            emit(BaseNetworkResult.Loading(true))
            if (response.code() == 200) {
                emit(BaseNetworkResult.Loading(false))
                response.body()?.let {
                    emit(BaseNetworkResult.Success(it))
                    Log.d("EEEEEEE", "createProduct: ${it.name}")
                }
            } else {
                emit(BaseNetworkResult.Loading(false))
                emit(BaseNetworkResult.Error(response.message()))
            }
        }

    override suspend fun updateProduct(
        type: String,
        update: ProductCreateRequest,
        id: String
    ): Flow<BaseNetworkResult<ProductResponse>> = flow {
        Log.d("NNNNN", "updateProduct:\n $update \n type:$type \n id:$id")
        val response = service.updateProduct(type, update, id)
        emit(BaseNetworkResult.Loading(true))
        if (response.code() == 200) {
            emit(BaseNetworkResult.Loading(false))
            response.body()?.let {
                emit(BaseNetworkResult.Success(it))
                Log.d("EEEEEEE", "updateProduct: ${it.name}")
            }
        } else {
            emit(BaseNetworkResult.Loading(false))
            emit(BaseNetworkResult.Error(response.message()))
        }
    }

    override suspend fun deleteProduct(
        type: String,
        id: String
    ): Flow<BaseNetworkResult<ProductResponse>> = flow {
        val response = service.deleteProduct(type, id)
        emit(BaseNetworkResult.Loading(true))
        if (response.code() == 200) {
            emit(BaseNetworkResult.Loading(false))
            response.body()?.let {
                emit(BaseNetworkResult.Success(it))
                Log.d("EEEEEEE", "deleteProduct: ${it.name}")
            }
        } else {
            emit(BaseNetworkResult.Loading(false))
            emit(BaseNetworkResult.Error(response.message()))
        }
    }

    override suspend fun fileUploadProduct(
        file: MultipartBody.Part,
        id: String
    ): Flow<BaseNetworkResult<ProductFileUploadResponse>> = flow {
        val response = service.productFileUpload(file, id)
        emit(BaseNetworkResult.Loading(true))
        if (response.code() == 200) {
            emit(BaseNetworkResult.Loading(false))
            response.body()?.let {
                emit(BaseNetworkResult.Success(it))
                Log.d("EEEEEEE", "fileUploadProduct: ${it.urlImageList}")
            }
        } else {
            emit(BaseNetworkResult.Loading(false))
            emit(BaseNetworkResult.Error(response.message()))
        }
    }

}