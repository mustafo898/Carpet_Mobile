package dark.composer.carpet.data.remote.models.request.basket

data class BasketCreateRequest(
    val amount: Int?,
    val info: String?,
    val productId: String,
    val type: String
)