package dark.composer.carpet.data.repositories

import dark.composer.carpet.data.remote.ApiService
import dark.composer.carpet.utils.BaseNetworkResult
import dark.composer.carpet.data.remote.models.request.factory.FactoryAddRequest
import dark.composer.carpet.data.remote.models.request.factory.update.FactoryUpdateRequest
import dark.composer.carpet.data.remote.models.response.factory.FactoryResponse
import dark.composer.carpet.data.remote.models.response.factory.PaginationResponse
import dark.composer.carpet.data.remote.models.response.profile.ProfileFileResponse
import dark.composer.carpet.domain.repository.factory.FactoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import javax.inject.Inject

class FactoryRepositoryImpl @Inject constructor(private val service: ApiService) :
    FactoryRepository {
    override suspend fun getFactory(id: Int): Flow<BaseNetworkResult<FactoryResponse>> = flow {
        val response = service.getFactoryInfo(id)
        emit(BaseNetworkResult.Loading(true))
        if (response.code() == 200) {
            emit(BaseNetworkResult.Loading(false))
            response.body()?.let {
                emit(BaseNetworkResult.Success(it))
            }
        } else {
            emit(BaseNetworkResult.Loading(false))
            emit(BaseNetworkResult.Error(response.message()))
        }
    }

    override suspend fun getFactoryList(
        page: Int,
        size: Int
    ): Flow<BaseNetworkResult<PaginationResponse>> = flow {
        val response = service.getFactoryPagination(page, size)
        emit(BaseNetworkResult.Loading(true))
        if (response.code() == 200) {
            emit(BaseNetworkResult.Loading(false))
            response.body()?.let {
                emit(BaseNetworkResult.Success(it))
            }
        } else {
            emit(BaseNetworkResult.Loading(false))
            emit(BaseNetworkResult.Error(response.message()))
        }

    }

    override suspend fun updateFactory(
        factoryUpdateRequest: FactoryUpdateRequest,
        id: Int
    ): Flow<BaseNetworkResult<FactoryResponse>> = flow {
        val response = service.updateFactory(factoryUpdateRequest, id)
        emit(BaseNetworkResult.Loading(true))
        if (response.code() == 200) {
            emit(BaseNetworkResult.Loading(false))
            response.body()?.let {
                emit(BaseNetworkResult.Success(it))
            }
        } else {
            emit(BaseNetworkResult.Loading(false))
            emit(BaseNetworkResult.Error(response.message()))
        }
    }

    override suspend fun deleteFactory(id: Int): Flow<BaseNetworkResult<FactoryResponse>> = flow {
        val response = service.deleteFactory(id)
        emit(BaseNetworkResult.Loading(true))
        if (response.code() == 200) {
            emit(BaseNetworkResult.Loading(false))
            response.body()?.let {
                emit(BaseNetworkResult.Success(it))
            }
        } else {
            emit(BaseNetworkResult.Loading(false))
            emit(BaseNetworkResult.Error(response.message()))
        }
    }

    override suspend fun createFactory(factoryAddRequest: FactoryAddRequest): Flow<BaseNetworkResult<FactoryResponse>> =
        flow {
            val response = service.createFactory(factoryAddRequest)
            emit(BaseNetworkResult.Loading(true))
            if (response.code() == 200) {
                emit(BaseNetworkResult.Loading(false))
                response.body()?.let {
                    emit(BaseNetworkResult.Success(it))
                }
            } else {
                emit(BaseNetworkResult.Loading(false))
                emit(BaseNetworkResult.Error(response.message()))
            }
        }

    override suspend fun getFactoryPaginationAdmin(
        page: Int,
        size: Int
    ): Flow<BaseNetworkResult<PaginationResponse>> = flow {
//        val response = service.getFactoryInfo(id)
//        emit(BaseNetworkResult.Loading(true))
//        if (response.code() == 200){
//            emit(BaseNetworkResult.Loading(false))
//            response.body()?.let {
//                emit(BaseNetworkResult.Success(it))
//            }
//        }else {
//            emit(BaseNetworkResult.Loading(false))
//            emit(BaseNetworkResult.Error(response.message()))
//        }
    }

    override suspend fun fileUploadFactory(
        file: MultipartBody.Part,
        key: String
    ): Flow<BaseNetworkResult<ProfileFileResponse>> = flow {
        val response = service.factoryFileUpload(file, key)
        emit(BaseNetworkResult.Loading(true))
        if (response.code() == 200) {
            emit(BaseNetworkResult.Loading(false))
            response.body()?.let {
                emit(BaseNetworkResult.Success(it))
            }
        } else {
            emit(BaseNetworkResult.Loading(false))
            emit(BaseNetworkResult.Error(response.message()))
        }
    }
}