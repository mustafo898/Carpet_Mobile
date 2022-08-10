package dark.composer.carpet.data.remote.models.response.product

data class Factory(
    val createdDate: String,
    val id: Int,
    val key: String,
    val name: String,
    val photoUrl: String,
    val status: String,
    val visible: Boolean
)