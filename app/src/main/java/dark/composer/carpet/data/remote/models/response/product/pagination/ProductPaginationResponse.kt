package dark.composer.carpet.data.remote.models.response.product.pagination

data class ProductPaginationResponse(
    val createdDate: String,
    val factoryName: String,
    val height: Double,
    val imageUrlList: List<String>,
    val name: String,
    val price: Double,
    val uuid: String,
    val weight: Double,
    val factoryAttachUrl:String,
)