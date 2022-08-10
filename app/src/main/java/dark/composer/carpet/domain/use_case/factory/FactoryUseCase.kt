package dark.composer.carpet.domain.use_case.factory

import dark.composer.carpet.data.remote.models.request.factory.FactoryAddRequest
import dark.composer.carpet.data.remote.models.request.factory.update.FactoryUpdateRequest
import dark.composer.carpet.data.remote.models.response.factory.FactoryResponse
import dark.composer.carpet.data.remote.models.response.factory.PaginationResponse
import dark.composer.carpet.data.remote.models.response.profile.ProfileFileResponse
import dark.composer.carpet.domain.repository.factory.FactoryRepository
import dark.composer.carpet.utils.BaseNetworkResult
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import javax.inject.Inject

class FactoryUseCase @Inject constructor(private val repository:FactoryRepository){
    suspend fun getFactory(id: Int): Flow<BaseNetworkResult<FactoryResponse>> {
        return repository.getFactory(id)
    }

    suspend fun getFactoryList(page:Int,size:Int): Flow<BaseNetworkResult<PaginationResponse>> {
        return repository.getFactoryList(page, size)
    }

    suspend fun getFactoryListAdmin(page:Int,size:Int): Flow<BaseNetworkResult<PaginationResponse>> {
        return repository.getFactoryPaginationAdmin(page, size)
    }

    suspend fun updateFactory(updateRequest: FactoryUpdateRequest, id: Int): Flow<BaseNetworkResult<FactoryResponse>> {
        return repository.updateFactory(updateRequest,id)
    }

    suspend fun createFactory(addRequest: FactoryAddRequest): Flow<BaseNetworkResult<FactoryResponse>> {
        return repository.createFactory(addRequest)
    }

    suspend fun fileUploadFactory(file:MultipartBody.Part,key:String): Flow<BaseNetworkResult<ProfileFileResponse>> {
        return repository.fileUploadFactory(file,key)
    }
}