package dark.composer.carpet.data.remote.models.response.product

import dark.composer.carpet.data.remote.models.response.factory.FactoryResponse

data class ProductResponse(
    val amount: Int,
    val colour: String,
    val createDate: String,
    val design: String,
    val factory: FactoryResponse,
    val height: Double,
    val name: String,
    val pon: String,
    val type: String,
    val price: Double,
    val urlImageList: List<String>? = null,
    val uuid: String,
    val attachUUID:String,
    val weight: Double,
    val visible: Boolean
)