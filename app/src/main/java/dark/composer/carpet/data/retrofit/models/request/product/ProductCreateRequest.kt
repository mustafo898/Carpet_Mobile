package dark.composer.carpet.data.retrofit.models.request.product

data class ProductCreateRequest(
    val amount: Int,
    val colour: String,
    val design: String,
    val factoryId: Int,
    val height: Int,
    val name: String,
    val pon: String,
    val price: Double,
    val type: String,
    val weight: Int
)