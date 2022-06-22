package dark.composer.carpet.data.repositories

import dark.composer.carpet.data.retrofit.ApiService
import dark.composer.carpet.utils.SharedPref
import javax.inject.Inject

class DefaultRepository @Inject constructor(
    private val service: ApiService,
    private val sharedPref: SharedPref
) {

}