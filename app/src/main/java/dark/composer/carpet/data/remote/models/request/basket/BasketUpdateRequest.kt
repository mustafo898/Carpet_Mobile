package dark.composer.carpet.data.remote.models.request.basket

data class BasketUpdateRequest(
    val basketId: Int,
    val status: String? = "GIVEN",
    val price: Double,
    val amount: Int? = null,
    val height: Double? = null
)