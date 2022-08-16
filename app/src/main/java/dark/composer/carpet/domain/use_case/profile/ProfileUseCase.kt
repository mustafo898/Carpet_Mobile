package dark.composer.carpet.domain.use_case.profile

import dark.composer.carpet.data.remote.models.request.profile.ProfileRequest
import dark.composer.carpet.data.remote.models.request.profile.create_customer.ProfileCreateRequest
import dark.composer.carpet.data.remote.models.response.profile.ProfileFileResponse
import dark.composer.carpet.data.remote.models.response.profile.ProfileResponse
import dark.composer.carpet.data.remote.models.response.profile.users.UsersPagination
import dark.composer.carpet.domain.repository.profile.ProfileRepository
import dark.composer.carpet.utils.BaseNetworkResult
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import javax.inject.Inject

class ProfileUseCase @Inject constructor(
    private val repo: ProfileRepository
) {
    suspend fun getProfile(): Flow<BaseNetworkResult<ProfileResponse>>  {
        return repo.getProfile()
    }

    suspend fun createProfile(create:ProfileCreateRequest): Flow<BaseNetworkResult<ProfileResponse>> {
        return repo.createProfile(create)
    }

    suspend fun fileUpload(file:MultipartBody.Part): Flow<BaseNetworkResult<ProfileFileResponse>> {
      return repo.fileUpload(file)
    }

    suspend fun getUsersProfileList():Flow<BaseNetworkResult<List<ProfileResponse>>> {
        return repo.getUsersProfileList()
    }

    suspend fun getUsersProfilePagination(size:Int,page:Int):Flow<BaseNetworkResult<UsersPagination>> {
        return repo.getUsersProfilePagination(size,page)
    }

    suspend fun getUsersProfileDetails(id:Int):Flow<BaseNetworkResult<ProfileResponse>> {
        return repo.getUsersProfileDetails(id)
    }

    suspend fun updateUsersProfile(id:Int,update: ProfileCreateRequest):Flow<BaseNetworkResult<ProfileResponse>> {
        return repo.updateUsersProfile(id, update)
    }

    suspend fun deleteUsersProfile(id:Int):Flow<BaseNetworkResult<ProfileResponse>> {
        return repo.deleteUsersProfile(id)
    }

    suspend fun deleteProfile():Flow<BaseNetworkResult<ProfileResponse>> {
        return repo.deleteProfile()
    }

    suspend fun updateProfile(update: ProfileRequest):Flow<BaseNetworkResult<ProfileResponse>> {
        return repo.updateProfile(update)
    }
}