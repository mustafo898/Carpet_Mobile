package dark.composer.carpet.data.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dark.composer.carpet.data.retrofit.ApiService
import dark.composer.carpet.data.retrofit.models.BaseNetworkResult
import dark.composer.carpet.data.retrofit.models.request.product.ProductCreateRequest
import dark.composer.carpet.data.retrofit.models.response.factory.PaginationResponse
import dark.composer.carpet.data.retrofit.models.response.product.ProductDetailsResponse
import dark.composer.carpet.data.retrofit.models.response.product.ProductResponse
import javax.inject.Inject

class ProductRepository @Inject constructor(private var service: ApiService) {
    suspend fun getPagination(page:Int,size:Int,type:String): LiveData<BaseNetworkResult<List<ProductResponse>>> {
        val list = MutableLiveData<BaseNetworkResult<List<ProductResponse>>>()
        val response = service.getProductPagination(type,page,size)
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

    suspend fun productDetails(id:String,type:String): LiveData<BaseNetworkResult<ProductDetailsResponse>> {
        val list = MutableLiveData<BaseNetworkResult<ProductDetailsResponse>>()
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
}