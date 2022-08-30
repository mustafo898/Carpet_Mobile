package dark.composer.carpet.data.remote.models.response.sale

import android.opengl.Visibility

data class SalePaginationResponse(
    val amount: Int,
    val createdDate: String,
    val height: Double,
    val price: Double,
    val productAttachUrl:String?,
    val productName: String,
    val saleId: Int,
    val type: String,
    val weight: Double,
    val visible:Boolean
)