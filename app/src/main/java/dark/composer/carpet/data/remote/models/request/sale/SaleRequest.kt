package dark.composer.carpet.data.remote.models.request.sale

data class SaleRequest (
    val type:String,
    val productId:String,
    val price:Double? = 0.0,
    val height:Double? = 0.0,
    val amount:Int? = 0
)