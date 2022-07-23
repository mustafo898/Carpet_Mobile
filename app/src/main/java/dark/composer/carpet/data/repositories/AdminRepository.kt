package dark.composer.carpet.data.repositories

import android.media.MediaCodecInfo
import android.provider.ContactsContract
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dark.composer.carpet.data.retrofit.ApiService
import dark.composer.carpet.data.retrofit.models.BaseNetworkResult
import dark.composer.carpet.data.retrofit.models.request.factory.FactoryAddRequest
import dark.composer.carpet.data.retrofit.models.response.factory.FactoryResponse
import dark.composer.carpet.data.retrofit.models.response.factory.PaginationResponse
import dark.composer.carpet.data.retrofit.models.response.profile.ProfileResponse
import dark.composer.carpet.utils.SharedPref
import javax.inject.Inject

class AdminRepository @Inject constructor(
    private val service: ApiService,
    private val sharedPref: SharedPref,
){

}