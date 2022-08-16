package dark.composer.carpet.data.remote.models.response.profile.users

import dark.composer.carpet.data.remote.models.response.factory.paginable.Pageable
import dark.composer.carpet.data.remote.models.response.factory.paginable.SortX
import dark.composer.carpet.data.remote.models.response.profile.ProfileResponse

data class UsersPagination(
    val content: List<ProfileResponse>,
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