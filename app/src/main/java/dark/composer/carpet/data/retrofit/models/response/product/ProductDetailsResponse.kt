package dark.composer.carpet.data.retrofit.models.response.product

data class ProductDetailsResponse(
    val createDate: String,
    val design: String,
    val factory: FactoryX,
    val height: Double,
    val name: String,
    val pon: String,
    val status: String,
    val type: String,
    val urlImageList: List<String>,
    val uuid: String,
    val visible: Boolean,
    val weight: Double
)