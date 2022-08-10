package dark.composer.carpet.domain.repository.factory

import dark.composer.carpet.data.remote.models.request.factory.FactoryAddRequest
import dark.composer.carpet.data.remote.models.request.factory.update.FactoryUpdateRequest
import dark.composer.carpet.data.remote.models.response.factory.FactoryResponse
import dark.composer.carpet.data.remote.models.response.factory.PaginationResponse
import dark.composer.carpet.data.remote.models.response.profile.ProfileFileResponse
import dark.composer.carpet.utils.BaseNetworkResult
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface FactoryRepository {
    suspend fun getFactory(id:Int): Flow<BaseNetworkResult<FactoryResponse>>
    suspend fun getFactoryList(page:Int,size:Int):Flow<BaseNetworkResult<PaginationResponse>>
//    suspend fun getFactoryListAdmin(): Flow<BaseNetworkResult<PaginationResponse>>
//    suspend fun getFactoryList():Flow<BaseNetworkResult<PaginationResponse>>
    suspend fun updateFactory(factoryUpdateRequest: FactoryUpdateRequest, id: Int):Flow<BaseNetworkResult<FactoryResponse>>
    suspend fun createFactory(factoryAddRequest: FactoryAddRequest):Flow<BaseNetworkResult<FactoryResponse>>
    suspend fun getFactoryPaginationAdmin(page:Int,size:Int):Flow<BaseNetworkResult<PaginationResponse>>
    suspend fun fileUploadFactory(file:MultipartBody.Part,key:String): Flow<BaseNetworkResult<ProfileFileResponse>>
}