package dark.composer.carpet.data.retrofit.models.request.basket

data class BasketCreateRequest(
    val amount: Int,
    val info: String,
    val productId: String,
    val type: String
)