package dark.composer.carpet.data.remote.models.response.factory

import dark.composer.carpet.data.remote.models.response.factory.paginable.Pageable
import dark.composer.carpet.data.remote.models.response.factory.paginable.SortX

data class PaginationResponse(
    var content: List<FactoryResponse>,
    val empty: Boolean,
    val first: Boolean,
    val last: Boolean,
    val number: Int,
    val numberOfElements: Int,
    val pageable: Pageable,
    val size: Int,
    val sort: SortX,
    val totalElements: Int,
    val totalPages: Int
)