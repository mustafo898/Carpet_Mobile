package dark.composer.carpet.data.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dark.composer.carpet.data.retrofit.ApiService
import dark.composer.carpet.data.retrofit.models.BaseNetworkResult
import dark.composer.carpet.data.retrofit.models.request.filter.ProductFilterRequest
import dark.composer.carpet.data.retrofit.models.request.product.ProductCreateRequest
import dark.composer.carpet.data.retrofit.models.response.factory.PaginationResponse
import dark.composer.carpet.data.retrofit.models.response.product.ProductFileUploadResponse
import dark.composer.carpet.data.retrofit.models.response.product.ProductResponse
import dark.composer.carpet.data.retrofit.models.response.product.pagination.ProductPaginationResponse
import okhttp3.MultipartBody
import javax.inject.Inject

class ProductRepository @Inject constructor(private var service: ApiService) {
    suspend fun getPagination(page:Int,size:Int,type:String): LiveData<BaseNetworkResult<List<ProductPaginationResponse>>> {
        val list = MutableLiveData<BaseNetworkResult<List<ProductPaginationResponse>>>()
        val response = service.getProductPagination(type,page,size)
        list.value = BaseNetworkResult.Loading(true)
        if (response.code() == 200){
            response.body()?.let {
                list.value = BaseNetworkResult.Loading(false)
                list.value = BaseNetworkResult.Success(it)
                it.forEach {t->
                    Log.d("EEEEEEE", "count: ${t.imageUrlList}")
                }
            }
        }else{
            list.value = BaseNetworkResult.Loading(false)
            list.value = BaseNetworkResult.Error(response.message())
        }
        return list
    }

    suspend fun productDetails(id:String,type:String): LiveData<BaseNetworkResult<ProductResponse>> {
        val list = MutableLiveData<BaseNetworkResult<ProductResponse>>()
        val response = service.productDetails(type,id)
        list.value = BaseNetworkResult.Loading(true)
        if (response.code() == 200){
            response.body()?.let {
                list.value = BaseNetworkResult.Loading(false)
                list.value = BaseNetworkResult.Success(it)
                Log.d("productDetails", "getPagination: $it")
            }
        }else{
            list.value = BaseNetworkResult.Loading(false)
            list.value = BaseNetworkResult.Error(response.message())
        }
        return list
    }

    suspend fun updateProduct(id:String,type:String,productUpdate : ProductCreateRequest): LiveData<BaseNetworkResult<ProductResponse>> {
        val list = MutableLiveData<BaseNetworkResult<ProductResponse>>()
        val response = service.updateProduct(type,productUpdate,id)
        list.value = BaseNetworkResult.Loading(true)
        if (response.code() == 200){
            response.body()?.let {
                list.value = BaseNetworkResult.Loading(false)
                list.value = BaseNetworkResult.Success(it)
                Log.d("productDetails", "getPagination: $it")
            }
        }else{
            list.value = BaseNetworkResult.Loading(false)
            list.value = BaseNetworkResult.Error(response.message())
        }
        return list
    }

    suspend fun deleteProduct(id:String,type:String): LiveData<BaseNetworkResult<ProductResponse>> {
        val list = MutableLiveData<BaseNetworkResult<ProductResponse>>()
        val response = service.deleteProduct(type,id)
        list.value = BaseNetworkResult.Loading(true)
        if (response.code() == 200){
            response.body()?.let {
                list.value = BaseNetworkResult.Loading(false)
                list.value = BaseNetworkResult.Success(it)
                Log.d("productDetails", "getPagination: $it")
            }
        }else{
            list.value = BaseNetworkResult.Loading(false)
            list.value = BaseNetworkResult.Error(response.message())
        }
        return list
    }

    suspend fun createProduct(productCreateRequest: ProductCreateRequest): LiveData<BaseNetworkResult<ProductResponse>> {
        val list = MutableLiveData<BaseNetworkResult<ProductResponse>>()
        val response = service.createProduct(productCreateRequest)
        list.value = BaseNetworkResult.Loading(true)
        if (response.code() == 200){
            response.body()?.let {
                list.value = BaseNetworkResult.Loading(false)
                list.value = BaseNetworkResult.Success(it)
                Log.d("EEEEEEE", "getPagination: $it")
            }
        }else{
            list.value = BaseNetworkResult.Loading(false)
            list.value = BaseNetworkResult.Error(response.message())
        }
        return list
    }

    suspend fun fileUploadProduct(productId:String,file: MultipartBody.Part):LiveData<BaseNetworkResult<ProductFileUploadResponse>>{
        val fileProduct = MutableLiveData<BaseNetworkResult<ProductFileUploadResponse>>()
        val response = service.productFileUpload(file,productId)
        fileProduct.value = BaseNetworkResult.Loading(true)
        if (response.code() == 200){
            response.body()?.let {
                fileProduct.value = BaseNetworkResult.Loading(false)
                fileProduct.value = BaseNetworkResult.Success(it)
            }
        }else{
            fileProduct.value = BaseNetworkResult.Loading(false)
            fileProduct.value = BaseNetworkResult.Error(response.message())
        }
        return fileProduct
    }

    suspend fun filterProduct(filterProductRequest: ProductFilterRequest): LiveData<BaseNetworkResult<List<ProductPaginationResponse>>> {
        val list = MutableLiveData<BaseNetworkResult<List<ProductPaginationResponse>>>()
        val response = service.filterProduct(filterProductRequest)
        list.value = BaseNetworkResult.Loading(true)
        if (response.code() == 200){
            response.body()?.let {
                list.value = BaseNetworkResult.Loading(false)
                list.value = BaseNetworkResult.Success(it)
                it.forEach {t->
                    Log.d("EEEEEEE", "count: ${t.imageUrlList}")
                }
            }
        }else{
            list.value = BaseNetworkResult.Loading(false)
            list.value = BaseNetworkResult.Error(response.message())
        }
        return list
    }

}