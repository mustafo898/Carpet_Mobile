package dark.composer.carpet.repositories

import dark.composer.carpet.retrofit.ApiService
import dark.composer.carpet.utils.SharedPref
import javax.inject.Inject

class AdminRepository @Inject constructor(
    private val service: ApiService,
    private val sharedPref: SharedPref
)