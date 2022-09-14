package dark.composer.carpet.data.remote.models.response.basket

import dark.composer.carpet.data.remote.models.response.product.ProductResponse

data class BasketCreateResponse(
    val amount: Int,
    val createdDate: String,
    val getProfile: GiveProfile,
    val giveProfile: GiveProfile,
    val id: Int,
    val info: String,
    val product: ProductResponse,
    val returnedDate: Any,
    val status: String,
    val type: String?="UNCOUNTABLE",
    val visible: Boolean
)