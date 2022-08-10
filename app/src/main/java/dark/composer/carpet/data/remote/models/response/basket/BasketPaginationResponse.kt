package dark.composer.carpet.data.remote.models.response.basket

import dark.composer.carpet.data.remote.models.response.product.pagination.ProductPaginationResponse

data class BasketPaginationResponse(
    val id: Int,
    val product: ProductPaginationResponse,
    val type: String,
    val createdDate: String,
    val status: String,
    val visible: Boolean
)