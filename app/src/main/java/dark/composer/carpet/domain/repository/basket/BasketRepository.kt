package dark.composer.carpet.domain.repository.basket

import dark.composer.carpet.data.remote.models.request.basket.BasketCreateRequest
import dark.composer.carpet.data.remote.models.request.basket.BasketUpdateRequest
import dark.composer.carpet.data.remote.models.response.basket.BasketCreateResponse
import dark.composer.carpet.data.remote.models.response.basket.BasketPaginationResponse
import dark.composer.carpet.data.remote.models.response.basket.DeleteResponse
import dark.composer.carpet.utils.BaseNetworkResult
import kotlinx.coroutines.flow.Flow

interface BasketRepository {
    suspend fun createBasket(create: BasketCreateRequest): Flow<BaseNetworkResult<BasketCreateResponse>>
    suspend fun updateBasket(update: BasketUpdateRequest): Flow<BaseNetworkResult<BasketCreateResponse>>
    suspend fun deleteBasket(id: Int): Flow<BaseNetworkResult<DeleteResponse>>
    suspend fun getBasketListByStatus(
        status: String,
        page: Int,
        size: Int
    ): Flow<BaseNetworkResult<List<BasketPaginationResponse>>>

    suspend fun getBasketList(status: String,page: Int,size: Int): Flow<BaseNetworkResult<List<BasketPaginationResponse>>>
    suspend fun getBasket(id: Int): Flow<BaseNetworkResult<BasketCreateResponse>>
}