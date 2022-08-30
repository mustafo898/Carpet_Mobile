package dark.composer.carpet.domain.use_case.sale

import dark.composer.carpet.data.remote.models.request.sale.SaleCreateDateRequest
import dark.composer.carpet.data.remote.models.request.sale.SaleRequest
import dark.composer.carpet.data.remote.models.request.sale.SaleUpdateRequest
import dark.composer.carpet.data.remote.models.response.profile.ProfileResponse
import dark.composer.carpet.data.remote.models.response.sale.SaleListByDate
import dark.composer.carpet.data.remote.models.response.sale.SalePaginationResponse
import dark.composer.carpet.data.remote.models.response.sale.SaleResponse
import dark.composer.carpet.domain.repository.profile.ProfileRepository
import dark.composer.carpet.domain.repository.sale.SaleRepository
import dark.composer.carpet.utils.BaseNetworkResult
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

class SaleUseCase @Inject constructor(
    private val repo: SaleRepository
) {
    suspend fun createSale(saleRequest: SaleRequest): Flow<BaseNetworkResult<SaleResponse>> {
        return repo.createSale(saleRequest)
    }

    suspend fun updateSale(
        saleUpdateRequest: SaleUpdateRequest,
        type: String,
        id: Int
    ): Flow<BaseNetworkResult<SaleResponse>> {
        return repo.updateSale(type = type, id = id, updateRequest = saleUpdateRequest)
    }

    suspend fun deleteSale(id: Int, type: String): Flow<BaseNetworkResult<SaleResponse>> {
        return repo.deleteSale(id, type)
    }

    suspend fun listSale(
        type: String,
        size: Int,
        page: Int
    ): Flow<BaseNetworkResult<List<SalePaginationResponse>>> {
        return repo.listSale(type, page, size)
    }

    suspend fun listByCreateDateSale(dateTime: SaleCreateDateRequest): Flow<BaseNetworkResult<List<SaleListByDate>>> {
        return repo.listByCreateDateSale(dateTime)
    }
}