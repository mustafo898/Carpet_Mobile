package dark.composer.carpet.data.retrofit.models.response.factory.paginable

data class Content(
    val createdDate: String,
    val id: Int,
    val key: String,
    val name: String,
    val photoUrl:String? = null,
    val status: String,
    val visible: Boolean
)