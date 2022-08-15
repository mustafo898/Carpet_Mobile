package dark.composer.carpet.data.repositories

import android.util.Log
import dark.composer.carpet.data.remote.ApiService
import dark.composer.carpet.utils.BaseNetworkResult
import dark.composer.carpet.data.remote.models.request.profile.ProfileRequest
import dark.composer.carpet.data.remote.models.request.profile.create_customer.ProfileCreateRequest
import dark.composer.carpet.data.remote.models.response.profile.ProfileFileResponse
import dark.composer.carpet.data.remote.models.response.profile.ProfileResponse
import dark.composer.carpet.domain.repository.profile.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private var service: ApiService,
):ProfileRepository{
    override suspend fun getProfile(): Flow<BaseNetworkResult<ProfileResponse>> = flow {
        val response = service.getProfile()
        emit(BaseNetworkResult.Loading(true))
        if (response.code() == 200) {
            emit(BaseNetworkResult.Loading(false))
            response.body()?.let {
                Log.d("FFFFF", "changeImage: ${it.url}")
                emit(BaseNetworkResult.Success(it))
            }
        } else {
            emit(BaseNetworkResult.Loading(false))
            emit(BaseNetworkResult.Error("No access"))
            Log.d("OOOOO", "changeImage: xatolik")
        }
    }

    override suspend fun fileUpload(file: MultipartBody.Part): Flow<BaseNetworkResult<ProfileFileResponse>> = flow {
        val response = service.profileFileUpload(file)
        emit(BaseNetworkResult.Loading(true))
        if (response.code() == 200) {
            emit(BaseNetworkResult.Loading(false))
            response.body()?.let {
                Log.d("FFFFF", "fileUpload: ${it.url}")
                emit(BaseNetworkResult.Success(it))
            }
        } else {
            emit(BaseNetworkResult.Loading(false))
            emit(BaseNetworkResult.Error("No access"))
            Log.d("OOOOO", "fileUpload: xatolik")
        }
    }

    override suspend fun deleteProfile(): Flow<BaseNetworkResult<ProfileResponse>> = flow {
        val response = service.deleteProfile()
        emit(BaseNetworkResult.Loading(true))
        if (response.code() == 200) {
            emit(BaseNetworkResult.Loading(false))
            response.body()?.let {
                Log.d("FFFFF", "deleteProfile: ${it.url}")
                emit(BaseNetworkResult.Success(it))
            }
        } else {
            emit(BaseNetworkResult.Loading(false))
            emit(BaseNetworkResult.Error("No access"))
            Log.d("OOOOO", "deleteProfile: xatolik")
        }
    }

    override suspend fun updateProfile(update: ProfileRequest): Flow<BaseNetworkResult<ProfileResponse>> = flow {
        val response = service.updateProfile(update)
        emit(BaseNetworkResult.Loading(true))
        if (response.code() == 200) {
            emit(BaseNetworkResult.Loading(false))
            response.body()?.let {
                Log.d("FFFFF", "changeImage: ${it.url}")
                emit(BaseNetworkResult.Success(it))
            }
        } else {
            emit(BaseNetworkResult.Loading(false))
            emit(BaseNetworkResult.Error("No access"))
            Log.d("OOOOO", "updateProfile: xatolik")
        }
    }

    override suspend fun deleteUsersProfile(id: Int): Flow<BaseNetworkResult<ProfileResponse>> = flow {
        val response = service.deleteUsersProfile(id)
        emit(BaseNetworkResult.Loading(true))
        if (response.code() == 200) {
            emit(BaseNetworkResult.Loading(false))
            response.body()?.let {
                Log.d("FFFFF", "deleteUsersProfile: ${it.url}")
                emit(BaseNetworkResult.Success(it))
            }
        } else {
            emit(BaseNetworkResult.Loading(false))
            emit(BaseNetworkResult.Error("No access"))
            Log.d("OOOOO", "deleteUsersProfile: xatolik")
        }
    }

    override suspend fun updateUsersProfile(
        id: Int,
        update: ProfileCreateRequest
    ): Flow<BaseNetworkResult<ProfileResponse>> = flow {
        val response = service.updateUsersProfile(id,update)
        emit(BaseNetworkResult.Loading(true))
        if (response.code() == 200) {
            emit(BaseNetworkResult.Loading(false))
            response.body()?.let {
                Log.d("FFFFF", "updateUsersProfile: ${it.url}")
                emit(BaseNetworkResult.Success(it))
            }
        } else {
            emit(BaseNetworkResult.Loading(false))
            emit(BaseNetworkResult.Error("No access"))
            Log.d("OOOOO", "updateUsersProfile: xatolik")
        }
    }

    override suspend fun getUsersProfileList(): Flow<BaseNetworkResult<List<ProfileResponse>>> = flow {
        val response = service.getUsersProfileList()
        emit(BaseNetworkResult.Loading(true))
        if (response.code() == 200) {
            emit(BaseNetworkResult.Loading(false))
            response.body()?.let {
                Log.d("FFFFF", "getUsersProfileList: $it")
                emit(BaseNetworkResult.Success(it))
            }
        } else {
            emit(BaseNetworkResult.Loading(false))
            emit(BaseNetworkResult.Error("No access"))
            Log.d("OOOOO", "getUsersProfileList: xatolik")
        }
    }

    override suspend fun getUsersProfilePagination(
        size: Int,
        page: Int

    ): Flow<BaseNetworkResult<List<ProfileResponse>>> = flow {
        val response = service.getUsersPagination(size, page)
        emit(BaseNetworkResult.Loading(true))
        if (response.code() == 200) {
            emit(BaseNetworkResult.Loading(false))
            response.body()?.let {
                Log.d("BBBBBB", "getUsersProfilePagination: $it")
                emit(BaseNetworkResult.Success(it))
            }
        } else {
            emit(BaseNetworkResult.Loading(false))
            emit(BaseNetworkResult.Error(response.message()))
            Log.d("OOOOO", "getUsersProfileList: xatolik")
        }
    }

    override suspend fun getUsersProfileDetails(id: Int): Flow<BaseNetworkResult<ProfileResponse>> = flow {
        val response = service.getUsersProfileDetails(id)
        emit(BaseNetworkResult.Loading(true))
        if (response.code() == 200) {
            emit(BaseNetworkResult.Loading(false))
            response.body()?.let {
                Log.d("FFFFF", "getUsersProfileDetails: ${it.url}")
                emit(BaseNetworkResult.Success(it))
            }
        } else {
            emit(BaseNetworkResult.Loading(false))
            emit(BaseNetworkResult.Error("No access"))
            Log.d("OOOOO", "getUsersProfileDetails: xatolik")
        }
    }

    override suspend fun createProfile(createRequest: ProfileCreateRequest): Flow<BaseNetworkResult<ProfileResponse>> = flow {
        val response = service.createProfile(createRequest)
        emit(BaseNetworkResult.Loading(true))
        if (response.code() == 200) {
            emit(BaseNetworkResult.Loading(false))
            response.body()?.let {
                Log.d("FFFFF", "createProfile: ${it.url}")
                emit(BaseNetworkResult.Success(it))
            }
        } else {
            emit(BaseNetworkResult.Loading(false))
            emit(BaseNetworkResult.Error("No access"))
            Log.d("OOOOO", "createProfile: xatolik")
        }
    }
}