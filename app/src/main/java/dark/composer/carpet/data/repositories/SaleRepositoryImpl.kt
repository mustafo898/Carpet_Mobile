package dark.composer.carpet.data.repositories

import android.util.Log
import dark.composer.carpet.data.remote.ApiService
import dark.composer.carpet.data.remote.models.request.sale.SaleCreateDateRequest
import dark.composer.carpet.data.remote.models.request.sale.SaleRequest
import dark.composer.carpet.data.remote.models.request.sale.SaleUpdateRequest
import dark.composer.carpet.data.remote.models.response.sale.SaleListByDate
import dark.composer.carpet.data.remote.models.response.sale.SalePaginationResponse
import dark.composer.carpet.data.remote.models.response.sale.SaleResponse
import dark.composer.carpet.domain.repository.sale.SaleRepository
import dark.composer.carpet.utils.BaseNetworkResult
import dark.composer.carpet.utils.SharedPref
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

class SaleRepositoryImpl @Inject constructor(
    private var service: ApiService,
) : SaleRepository {
    override suspend fun createSale(saleRequest: SaleRequest): Flow<BaseNetworkResult<SaleResponse>> =
        flow {
            val response = service.createSale(saleRequest)
            emit(BaseNetworkResult.Loading(true))
            if (response.code() == 200) {
                emit(BaseNetworkResult.Loading(false))
                response.body()?.let {
                    Log.d("Sale", "createSale: ${it.status}")
                    emit(BaseNetworkResult.Success(it))

//                    if (it.status == 1) {
//                        emit(BaseNetworkResult.Success(it))
//                    } else {
//                        emit(BaseNetworkResult.Error(it.message))
//                    }
                }
            }
            else {
                emit(BaseNetworkResult.Loading(false))
                emit(BaseNetworkResult.Error(response.message()))
                Log.d("Sale", "createSale: xatolik")
            }
        }

    override suspend fun deleteSale(id: Int, type: String): Flow<BaseNetworkResult<SaleResponse>> =
        flow {
            val response = service.deleteSale(type, id)
            emit(BaseNetworkResult.Loading(true))
            if (response.code() == 200) {
                emit(BaseNetworkResult.Loading(false))
                response.body()?.let {
                    Log.d("Sale", "deleteSale: ${it.status}")
//                    if (it.status == 1) {
                        emit(BaseNetworkResult.Success(it))
//                    } else {
//                        emit(BaseNetworkResult.Error(it.message))
//                    }
                }
            } else {
                emit(BaseNetworkResult.Loading(false))
                emit(BaseNetworkResult.Error(response.message()))
                Log.d("Sale", "deleteSale: xatolik")
            }
        }

    override suspend fun listSale(
        type: String,
        page: Int,
        size: Int
    ): Flow<BaseNetworkResult<List<SalePaginationResponse>>> = flow {
        val response = service.getSaleList(type, page, size)
        emit(BaseNetworkResult.Loading(true))
        if (response.code() == 200) {
            emit(BaseNetworkResult.Loading(false))
            response.body()?.let {
                Log.d("Sale", "listSale: $it")
                emit(BaseNetworkResult.Success(it))
            }
        } else {
            emit(BaseNetworkResult.Loading(false))
            emit(BaseNetworkResult.Error(response.message()))
            Log.d("Sale", "listSale: xatolik")
        }
    }

    override suspend fun listByCreateDateSale(date: SaleCreateDateRequest): Flow<BaseNetworkResult<List<SaleListByDate>>> =
        flow {
            Log.d("History", "listByCreateDateSale:  $date")
            val response = service.listByCreateDate(date)
            emit(BaseNetworkResult.Loading(true))
            if (response.code() == 200) {
                emit(BaseNetworkResult.Loading(false))
                response.body()?.let {
                    Log.d("Sale", "listByCreateDateSale: $it")
                    emit(BaseNetworkResult.Success(it))
                }
            } else {
                emit(BaseNetworkResult.Loading(false))
                emit(BaseNetworkResult.Error(response.message()))
                Log.d("Sale", "listByCreateDateSale: xatolik")
            }
        }

    override suspend fun updateSale(
        id: Int,
        type: String,
        updateRequest: SaleUpdateRequest
    ): Flow<BaseNetworkResult<SaleResponse>> = flow {
        val response = service.updateSale(updateRequest, type, id)
        emit(BaseNetworkResult.Loading(true))
        if (response.code() == 200) {
            emit(BaseNetworkResult.Loading(false))
            response.body()?.let {
                Log.d("Sale", "updateSale: ${it.status}")
                emit(BaseNetworkResult.Success(it))
//                if (it.status == 1) {
//                    emit(BaseNetworkResult.Success(it))
//                } else if (it.status == -1) {
//                    emit(BaseNetworkResult.Error(it.message))
//                }
            }
        } else {
            emit(BaseNetworkResult.Loading(false))
            emit(BaseNetworkResult.Error(response.message()))
            Log.d("Sale", "updateSale: xatolik")
        }
    }
}