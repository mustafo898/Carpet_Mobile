package dark.composer.carpet.data.retrofit.models.response.product

data class ProductResponse(
    val amount: Int,
    val colour: String,
    val createDate: String,
    val design: String,
    val factory: Factory,
    val height: Double,
    val name: String,
    val pon: String,
    val type: String,
    val urlImageList: List<String>? = null,
    val uuid: String,
    val attachUUID:String,
    val weight: Double,
    val visible: Boolean
)