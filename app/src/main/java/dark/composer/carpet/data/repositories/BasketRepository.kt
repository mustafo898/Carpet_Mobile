package dark.composer.carpet.data.repositories

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dark.composer.carpet.data.retrofit.ApiService
import dark.composer.carpet.data.retrofit.models.BaseNetworkResult
import dark.composer.carpet.data.retrofit.models.request.basket.BasketCreateRequest
import dark.composer.carpet.data.retrofit.models.request.basket.BasketUpdateRequest
import dark.composer.carpet.data.retrofit.models.response.basket.BasketCreateResponse
import dark.composer.carpet.data.retrofit.models.response.basket.BasketPaginationResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BasketRepository @Inject constructor(private val service: ApiService) {
    suspend fun createBasket(createRequest: BasketCreateRequest): LiveData<BaseNetworkResult<BasketCreateResponse>> {
        val liveData = MutableLiveData<BaseNetworkResult<BasketCreateResponse>>()
        val response = service.createBasket(createRequest)
        liveData.value = BaseNetworkResult.Loading(true)
        if (response.code() == 200) {
            liveData.value = BaseNetworkResult.Loading(false)
            response.body()?.let {
                liveData.value = BaseNetworkResult.Success(it)
            }
        } else if (response.code() == 401) {
            Log.d("RRRRRR", "createBasket: ${response.message()}")
        } else {
            liveData.value = BaseNetworkResult.Loading(false)
            liveData.value = BaseNetworkResult.Error(response.message().toString())
        }
        return liveData
    }

    suspend fun updateBasket(createRequest: BasketUpdateRequest): LiveData<BaseNetworkResult<BasketCreateResponse>> {
        val liveData = MutableLiveData<BaseNetworkResult<BasketCreateResponse>>()
        val response = service.updateBasket(createRequest)
        liveData.value = BaseNetworkResult.Loading(true)
        if (response.code() == 200) {
            liveData.value = BaseNetworkResult.Loading(false)
            response.body()?.let {
                liveData.value = BaseNetworkResult.Success(it)
            }
        } else {
            Log.d("RRRRRR", "createBasket: ${response.message()}")
            liveData.value = BaseNetworkResult.Loading(false)
            liveData.value = BaseNetworkResult.Error(response.message().toString())
        }
        return liveData
    }

    suspend fun getPaginationBasket(
        status: String, page: Int, size: Int, context: Context
    ): LiveData<BaseNetworkResult<List<BasketPaginationResponse>>> {
        val liveData = MutableLiveData<BaseNetworkResult<List<BasketPaginationResponse>>>()
        val response = service.getPaginationBasket(status, page, size)
        liveData.value = BaseNetworkResult.Loading(true)
        Toast.makeText(context, "Keldi", Toast.LENGTH_SHORT).show()

        if (response.isSuccessful) {
//                emit(BaseNetworkResult.Loading(false))
//                response.body()?.let {
            liveData.value = BaseNetworkResult.Loading(false)
            Log.d("eeeee", "getPaginationBasket: ${response.body()}")
            liveData.value = BaseNetworkResult.Success(response.body()!!)
//                }
        } else {
            liveData.value = BaseNetworkResult.Loading(false)
            liveData.value = BaseNetworkResult.Error(response.errorBody().toString())
        }
        return liveData
    }
}