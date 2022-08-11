package dark.composer.carpet.data.repositories

import android.util.Log
import dark.composer.carpet.data.remote.ApiService
import dark.composer.carpet.utils.BaseNetworkResult
import dark.composer.carpet.data.remote.models.request.basket.BasketCreateRequest
import dark.composer.carpet.data.remote.models.request.basket.BasketUpdateRequest
import dark.composer.carpet.data.remote.models.response.basket.BasketCreateResponse
import dark.composer.carpet.data.remote.models.response.basket.BasketPaginationResponse
import dark.composer.carpet.data.remote.models.response.basket.DeleteResponse
import dark.composer.carpet.domain.repository.basket.BasketRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BasketRepositoryImpl @Inject constructor(private val service: ApiService) : BasketRepository {
    override suspend fun createBasket(createRequest: BasketCreateRequest): Flow<BaseNetworkResult<BasketCreateResponse>> = flow {
        val response = service.createBasket(createRequest)
        emit(BaseNetworkResult.Loading(true))
        if (response.code() == 200) {
            response.body()?.let {
                emit(BaseNetworkResult.Success(it))
            }
        } else if (response.code() == 401) {
            emit(BaseNetworkResult.Error(response.message().toString()))
            Log.d("RRRRRR", "createBasket: ${response.message()}")
        } else {
            emit(BaseNetworkResult.Loading(false))
            emit(BaseNetworkResult.Error(response.message().toString()))
        }
    }

    override suspend fun updateBasket(update: BasketUpdateRequest): Flow<BaseNetworkResult<BasketCreateResponse>> = flow {
        val response = service.updateBasket(update)
        emit(BaseNetworkResult.Loading(true))
        if (response.code() == 200) {
            response.body()?.let {
                emit(BaseNetworkResult.Success(it))
            }
        } else if (response.code() == 401) {
            emit(BaseNetworkResult.Error(response.message().toString()))
            Log.d("RRRRRR", "updateBasket: ${response.message()}")
        } else {
            emit(BaseNetworkResult.Loading(false))
            emit(BaseNetworkResult.Error(response.message().toString()))
        }
    }

    override suspend fun deleteBasket(id: Int): Flow<BaseNetworkResult<DeleteResponse>> = flow {
        val response = service.deleteByIdBasket(id)
        emit(BaseNetworkResult.Loading(true))
        if (response.code() == 200) {
            response.body()?.let {
                emit(BaseNetworkResult.Success(it))
            }
        } else if (response.code() == 401) {
            emit(BaseNetworkResult.Error(response.message().toString()))
            Log.d("RRRRRR", "deleteBasket: ${response.message()}")
        } else {
            emit(BaseNetworkResult.Loading(false))
            emit(BaseNetworkResult.Error(response.message().toString()))
        }
    }

    override suspend fun getBasketListByStatus(
        status: String,
        page: Int,
        size: Int
    ): Flow<BaseNetworkResult<List<BasketPaginationResponse>>> = flow {
        val response = service.getPaginationBasket(status, page, size)
        emit(BaseNetworkResult.Loading(true))
        if (response.code() == 200) {
            response.body()?.let {
                emit(BaseNetworkResult.Success(it))
            }
        } else if (response.code() == 401) {
            emit(BaseNetworkResult.Error(response.message().toString()))
            Log.d("RRRRRR", "getBasketListByStatus: ${response.message()}")
        } else {
            emit(BaseNetworkResult.Loading(false))
            emit(BaseNetworkResult.Error(response.message().toString()))
        }
    }

    override suspend fun getBasketList(status: String): Flow<BaseNetworkResult<List<BasketPaginationResponse>>> = flow {
//        val response = service.getbaske(status, page, size)
//        emit(BaseNetworkResult.Loading(true))
//        if (response.code() == 200) {
//            response.body()?.let {
//                emit(BaseNetworkResult.Success(it))
//            }
//        } else if (response.code() == 401) {
//            emit(BaseNetworkResult.Error(response.message().toString()))
//            Log.d("RRRRRR", "createBasket: ${response.message()}")
//        } else {
//            emit(BaseNetworkResult.Loading(false))
//            emit(BaseNetworkResult.Error(response.message().toString()))
//        }
    }

    override suspend fun getBasket(id: Int): Flow<BaseNetworkResult<BasketCreateResponse>> = flow {
        val response = service.getByIdBasket(id)
        emit(BaseNetworkResult.Loading(true))
        if (response.code() == 200) {
            response.body()?.let {
                emit(BaseNetworkResult.Success(it))
            }
        } else if (response.code() == 401) {
            emit(BaseNetworkResult.Error(response.message().toString()))
            Log.d("RRRRRR", "getBasket: ${response.message()}")
        } else {
            emit(BaseNetworkResult.Loading(false))
            emit(BaseNetworkResult.Error(response.message().toString()))
        }
    }
}