package dark.composer.carpet.domain.repository.sale

import dark.composer.carpet.data.remote.models.response.profile.ProfileResponse

interface SaleRepository {
    suspend fun getProfile(): ProfileResponse

}