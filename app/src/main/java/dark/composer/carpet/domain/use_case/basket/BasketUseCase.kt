package dark.composer.carpet.domain.use_case.basket

import dark.composer.carpet.data.remote.models.request.basket.BasketCreateRequest
import dark.composer.carpet.data.remote.models.request.basket.BasketUpdateRequest
import dark.composer.carpet.data.remote.models.response.basket.BasketCreateResponse
import dark.composer.carpet.data.remote.models.response.basket.BasketPaginationResponse
import dark.composer.carpet.data.remote.models.response.basket.DeleteResponse
import dark.composer.carpet.domain.repository.basket.BasketRepository
import dark.composer.carpet.utils.BaseNetworkResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BasketUseCase @Inject constructor(private val repository: BasketRepository) {
    suspend fun createBasket(createRequest: BasketCreateRequest): Flow<BaseNetworkResult<BasketCreateResponse>> {
        return repository.createBasket(createRequest)
    }

    suspend fun updateBasket(update: BasketUpdateRequest): Flow<BaseNetworkResult<BasketCreateResponse>> {
        return repository.updateBasket(update)
    }

    suspend fun deleteBasket(id: Int): Flow<BaseNetworkResult<DeleteResponse>> {
        return repository.deleteBasket(id)
    }

    suspend fun getBasket(id: Int): Flow<BaseNetworkResult<BasketCreateResponse>> {
        return repository.getBasket(id)
    }

    suspend fun getBasketList(status:String): Flow<BaseNetworkResult<List<BasketPaginationResponse>>> {
        return repository.getBasketList(status)
    }

    suspend fun getProductByStatus(status:String,page:Int,size:Int): Flow<BaseNetworkResult<List<BasketPaginationResponse>>> {
        return repository.getBasketListByStatus(status, page, size)
    }
}