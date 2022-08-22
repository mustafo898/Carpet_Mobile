package dark.composer.carpet.data.remote.models.request.basket

data class BasketCreateRequest(
    val amount: Int? = null,
    val info: String? = null,
    val productId: String,
    val type: String
)