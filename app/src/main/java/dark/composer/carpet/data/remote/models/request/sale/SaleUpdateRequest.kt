package dark.composer.carpet.data.remote.models.request.sale

data class SaleUpdateRequest(
    val price: Double? = null,
    val amount: Int? = null,
    val height: Double? = null
)
