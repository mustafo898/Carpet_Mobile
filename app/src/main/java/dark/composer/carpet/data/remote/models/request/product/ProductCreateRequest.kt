package dark.composer.carpet.data.remote.models.request.product

data class ProductCreateRequest(
    val amount: Int,
    val colour: String,
    val design: String,
    val factoryId: Int,
    val height: Double,
    val name: String,
    val pon: String,
    val price: Double,
    val type: String,
    val weight: Double
)