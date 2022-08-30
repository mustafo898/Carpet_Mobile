package dark.composer.carpet.data.remote.models.response.sale

import dark.composer.carpet.data.remote.models.response.factory.FactoryResponse
import dark.composer.carpet.data.remote.models.response.product.ProductResponse
import dark.composer.carpet.data.remote.models.response.profile.ProfileResponse
import java.time.LocalDateTime

data class SaleListByDate(
    val id: Int,
    val profile: ProfileResponse,
    val amount: Int,
    val createdDate: String,
    val price: Double,
    val factoryDTO: FactoryResponse,
    val productDTO: ProductResponse,
    val height: Double,
    val status: String
)