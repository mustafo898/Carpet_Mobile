package dark.composer.carpet.domain.repository.sale

import dark.composer.carpet.data.remote.models.request.sale.SaleCreateDateRequest
import dark.composer.carpet.data.remote.models.request.sale.SaleRequest
import dark.composer.carpet.data.remote.models.request.sale.SaleUpdateRequest
import dark.composer.carpet.data.remote.models.response.sale.SaleListByDate
import dark.composer.carpet.data.remote.models.response.sale.SalePaginationResponse
import dark.composer.carpet.data.remote.models.response.sale.SaleResponse
import dark.composer.carpet.utils.BaseNetworkResult
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.time.LocalDateTime

interface SaleRepository {
    suspend fun createSale(saleRequest: SaleRequest):Flow<BaseNetworkResult<SaleResponse>>
    suspend fun updateSale(id:Int, type: String, updateRequest: SaleUpdateRequest):Flow<BaseNetworkResult<SaleResponse>>
    suspend fun deleteSale(id:Int, type: String):Flow<BaseNetworkResult<SaleResponse>>
    suspend fun listSale(type:String, page:Int, size:Int):Flow<BaseNetworkResult<List<SalePaginationResponse>>>
    suspend fun listByCreateDateSale(date : SaleCreateDateRequest):Flow<BaseNetworkResult<List<SaleListByDate>>>
}