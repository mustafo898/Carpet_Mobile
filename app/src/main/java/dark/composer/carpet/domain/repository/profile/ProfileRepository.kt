package dark.composer.carpet.domain.repository.profile

import dark.composer.carpet.data.remote.models.request.profile.ProfileRequest
import dark.composer.carpet.data.remote.models.request.profile.create_customer.ProfileCreateRequest
import dark.composer.carpet.data.remote.models.response.profile.ProfileFileResponse
import dark.composer.carpet.data.remote.models.response.profile.ProfileResponse
import dark.composer.carpet.utils.BaseNetworkResult
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface ProfileRepository {
    suspend fun getProfile(): Flow<BaseNetworkResult<ProfileResponse>>
    suspend fun fileUpload(file:MultipartBody.Part): Flow<BaseNetworkResult<ProfileFileResponse>>
    suspend fun deleteProfile(): Flow<BaseNetworkResult<ProfileResponse>>
    suspend fun updateProfile(update: ProfileRequest): Flow<BaseNetworkResult<ProfileResponse>>
    suspend fun deleteUsersProfile(id:Int): Flow<BaseNetworkResult<ProfileResponse>>
    suspend fun updateUsersProfile(id: Int,update: ProfileCreateRequest): Flow<BaseNetworkResult<ProfileResponse>>
    suspend fun getUsersProfileList(): Flow<BaseNetworkResult<List<ProfileResponse>>>
    suspend fun getUsersProfilePagination(size:Int,page:Int): Flow<BaseNetworkResult<List<ProfileResponse>>>
    suspend fun getUsersProfileDetails(id:Int): Flow<BaseNetworkResult<ProfileResponse>>
    suspend fun createProfile(createRequest: ProfileCreateRequest): Flow<BaseNetworkResult<ProfileResponse>>
}