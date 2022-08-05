package dark.composer.carpet.data.retrofit.models.request.basket

data class BasketUpdateRequest(
    val basketId: Int,
    val status: String,
    val price: Double,
    val amount: Int,
    val height: Double
)